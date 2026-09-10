# Aether Legacy — Codebase Audit Plan

A full audit of the Aether Legacy mod (Minecraft 1.7.10 / Forge) covering
vulnerabilities, performance issues, and bug fixes.

## Findings so far (from initial reconnaissance)

### Confirmed vulnerabilities (network layer — 1.7.10 mods trust packets far too much)

- `PacketSetTime` — any client can set the world time of **every dimension** on
  the server (no permission check, no proximity to a Sun Altar; the
  `dimensionId` field is read but ignored).
- `PacketOpenContainer` — any client can open any GUI by arbitrary ID at their
  own coords; combined with `AetherGuiHandler`'s **unchecked casts** of
  `world.getTileEntity(x, y, z)` to `TileEntityEnchanter`/`Freezer`/`Incubator`,
  a crafted packet at a mismatched TE position triggers a server-side
  `ClassCastException` (crash/DoS).
- `PacketExtendedAttack` — attack any entity by ID with **no reach/LOS check**
  (free reach/kill-aura exploit).
- `PacketPerkChanged` / `PacketCapeChanged` — any client can flip **any other
  player's** halo/glow/cape/moa-skin by entity ID, with no server-side
  `AetherRankings` donator/rank validation.
- `PacketSendSneaking` — any client can set **any player's** `mountSneaking`
  state.
- `PacketCheckKey` — writes to a static global (`AetherLore.hasKey`), letting
  any client flip global state for the whole server.
- `AetherPacket.onMessage` — handlers touch world/player state directly; in
  1.7.10 simpleimpl these arrive on Netty threads, not the main thread → race
  conditions/crashes (should use `addScheduledTask`).

### Confirmed bugs

- `TileEntityTreasureChest.unlock()` — `random.nextInt(1)` always returns 0
  (loot roll bug).
- `TileEntityTreasureChest.getDescriptionPacket()` — serializes **full inventory
  contents** to every client in the world (loot x-ray/leak vector vanilla
  chests avoid); `closeInventory` manual `--numPlayersUsing` can desync from
  `super.openInventory()`.
- `ItemLifeShard.onItemRightClick` — decrements `heldItem.stackSize` without
  nulling at 0, and the shard-count/max-health sync path looks fragile.

## Plan Steps

1. **Complete the network packet audit** — read every packet in
   `network/packets/` (esp. `PacketInitiateValkyrieFight`,
   `PacketDialogueClicked`, `PacketSwetJump`, `PacketPortalItem`,
   `PacketAccessory`) and document each server-bound handler that lacks:
   sender identity validation, range/distance checks, permission checks, and
   input bounds validation. Produce a fix list per packet.

2. **Fix thread-safety of the packet base class** — modify
   `AetherPacket.onMessage` to schedule handling onto the main thread via
   `FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask()`
   / `Minecraft.getMinecraft().addScheduledTask()`, eliminating cross-thread
   world access.

3. **Harden `AetherGuiHandler`** — add `instanceof` checks before casting tile
   entities (prevents the `ClassCastException` server crash), validate GUI IDs
   against a whitelist, and make `PacketOpenContainer` carry/use real
   interaction coordinates with a distance check.

4. **Audit all containers and slots for dupe exploits** — review
   `ContainerAccessories`, `ContainerEnchanter`, `ContainerFreezer`,
   `ContainerIncubator`, `ContainerLore`, and every class in `inventory/slots/`
   for missing `canInteractWith`, `isItemValid`,
   `slotClick`/shift-click (`transferStackInSlot`) validation, and
   stack-overflow on merge (`Math.min` on maxStackSize).

5. **Audit player data (`PlayerAether`) persistence & sync** — verify shard
   counts, accessory inventory, and perk state are saved to NBT, synced only
   server→client, and that `PlayerAether.get(player)` storage (likely a map
   keyed by player) doesn't leak `EntityPlayer` references after logout
   (memory leak check).

6. **Fix item logic bugs** — `ItemLifeShard` (stack cleanup at 0, remove
   redundant `updateShardCount(0)` call), `ItemDeveloperStick` (unguarded
   `--heldItem.stackSize`), and review `DoubleDropHelper`,
   `ItemSkyrootBucket`, `ItemGravititeTool` and `ItemSkyrootSword`
   (double-drop tools are classic dupe-bug territory — e.g., double drops
   firing in `onBlockDestroyed` when the block wasn't actually mined, or
   applying to creative-mode players).

7. **Tile entity audit** — fix `TileEntityTreasureChest` loot roll
   (`nextInt(1)` bug), stop syncing full inventory NBT to clients (send only
   `locked`/`kind` and let vanilla chest-sync handle contents), fix
   `closeInventory`/`openInventory` symmetry; review
   `TileEntityEnchanter`/`Freezer`/`Incubator` for `updateEntity` infinite
   loops, burn-time not saved to NBT, and processing logic that runs
   client-side.

8. **Static/global state audit** — eliminate or scope `AetherLore.hasKey`
   (make it per-player), check `AetherEventHandler`, `AetherWorld`, and
   `RandomTracker` for other client-controllable or cross-player global
   state.

9. **Performance pass** — profile-critical paths: `ChunkProviderAether` +
   `MapGen*` world gen (allocation churn, per-chunk `new Random()`, redundant
   block scans), entity AI tick costs, `sendToAllInOurWorld` O(players)
   loops, particle spawning in `AetherOverlay`, and client render loops; flag
   any per-tick allocations that can be hoisted.

10. **Client-side crash cleanup** — `GuiDialogue` bare
    `printStackTrace()`, `AetherMainMenu` reflective `Desktop` launch
    (verify try/catch coverage), `EntityAetherItem`/renderer null-safety on
    missing player data.

11. **Regression-verify** — after fixes, run `gradlew build` (compile check)
    and, where feasible, quick in-game smoke tests of each patched packet path
    on a local server with a modified client simulation.

## Priority

Recommended order: network packet fixes first (highest severity — remote
exploits and server crashes), then containers/dupe fixes, then tile entity and
item bugs, then performance and cleanup.
