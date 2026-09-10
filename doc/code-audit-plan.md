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

## Packet Audit Results

| Packet | Direction | Issue found | Fix applied |
|---|---|---|---|
| `PacketOpenContainer` | C→S | Client could open **any** GUI id at its own position with no whitelist; combined with the unchecked TE casts in `AetherGuiHandler`, a crafted id at a mismatched tile entity crashed the server (DoS) | Whitelist to `accessories` + `-1` (the only legitimately-sent ids) |
| `PacketExtendedAttack` | C→S | Client could attack **any entity by ID** with no reach/LOS check → free reach/kill-aura | Server-side: require a Valkyrie tool in hand, distance ≤ 9 blocks, `canEntityBeSeen`, entity not self/dead |
| `PacketPerkChanged` | C→S | Client could toggle **any player's** halo/glow/moa-skin by arbitrary entity id | Must target self (`entityID == player.getEntityId()`) |
| `PacketCapeChanged` | C→S | Same — could toggle any player's cape | Must target self |
| `PacketSendSneaking` | C→S | Client could set **any player's** mount-sneaking state | Must target self |
| `PacketInitiateValkyrieFight` | C→S | No server-side validation: arbitrary slot index, deleted the **entire** medal stack, could ready any queen | Validate slot bounds, item == victory_medal, stack ≥ 10, queen within 64 blocks, not already ready; consume exactly 10 via `decrStackSize` |
| `PacketCheckKey` | C→S | Set a **global static** (`AetherLore.hasKey`) — any client flipped lore-slot validity for the entire server | Handler is now a no-op; `SlotLore.isItemValid` computes deterministically on both sides via `StatCollector` |
| `PacketDisplayDialogue` | S→C | `toBytes` wrote `dialogue` before `dialogueName`, but `fromBytes` reads `dialogueName` first → the gui title/body were swapped | Fix write order to match read order |
| `PacketSetTime` (earlier session) | C→S | Any client could set time on **all** dimensions; no boss/permission gate | Aether-only, Sun Spirit gate, op/`sunAltarMultiplayer` check |
| All S→C packets (`Accessory`, `Achievement`, `SendPoison*`, `UpdateLifeShardCount`, `SendSeenDialogue`, `PortalItem`, `SendTime`, `SendShouldCycle`, `SendEternalDay`, `SwetJump`, `DisplayDialogue`) | S→C | Client trusts server by design; no cross-player client exploit | No change needed |

Notable: `AetherLore.hasKey` is retained (deprecated) for API compatibility.
`PacketCheckKey` remains registered in `AetherNetwork` so the discriminant ids
are unchanged; its handler is now inert.

## Plan Steps

1. ✅ **Complete the network packet audit (DONE)** — every packet in
   `network/packets/` read and every call site traced. See
   [Packet Audit Results](#packet-audit-results) below. All fixes applied and
   verified with `gradlew build`.

2. ✅ **Thread-safety: investigated, NOT needed** — traced the FML 1.7.10
   dispatch path (`NetworkDispatcher` → `FMLProxyPacket` →
   `NetworkManager.channelRead0` → `receivedPacketsQueue` → main-thread drain
   → `EmbeddedChannel.writeInbound` → `SimpleChannelHandlerWrapper`).
   `FMLProxyPacket` extends `Packet` and does not override `hasPriority()`, so
   it is queued and processed on the **main thread** (`MinecraftServer.run` /
   `Minecraft.runMainGameLoop`). `onMessage` already runs on the main thread;
   a scheduled-task rewrite would add risk for no benefit. (`MinecraftServer`
   in 1.7.10 has no `addScheduledTask` anyway — only client `Minecraft`
   does.)

3. ✅ **Harden `AetherGuiHandler` (DONE)** — the dangerous unchecked TE casts are
   now guarded with `instanceof` checks on **both** server and client, so a
   mismatched/blocks-replaced tile entity can no longer throw a
   `ClassCastException`. Server-side TE GUIs (enchanter/freezer/incubator/
   treasure chest) additionally require the player to be within 9 blocks
   (`MAX_OPEN_DISTANCE_SQ`), defense-in-depth layered on top of vanilla's
   6-block activation reach and the container's own 8-block
   `isUseableByPlayer` check. `PacketOpenContainer` was already whitelisted to
   `accessories`/`-1` in step 1 (no TE casts, no coords needed). Threat-model
   note: traced FML 1.7.10 and confirmed `FMLMessage.OpenGui` is server→client
   only (`OpenGuiHandler` uses `FMLClientHandler`), so a client cannot directly
   invoke `getServerGuiElement` with arbitrary coords — the `instanceof`
   guards are therefore defense-in-depth, not a remote-exploit fix, but they
   prevent real client/server crashes on TE desync.

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
