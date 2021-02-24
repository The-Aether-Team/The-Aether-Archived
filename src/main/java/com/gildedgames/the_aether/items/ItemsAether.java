package com.gildedgames.the_aether.items;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;
import com.gildedgames.the_aether.items.accessories.ItemAccessoryDyed;
import com.gildedgames.the_aether.items.armor.ItemAetherArmor;
import com.gildedgames.the_aether.items.armor.ItemZaniteArmor;
import com.gildedgames.the_aether.items.dungeon.ItemDungeonKey;
import com.gildedgames.the_aether.items.dungeon.ItemVictoryMedal;
import com.gildedgames.the_aether.items.food.ItemAetherFood;
import com.gildedgames.the_aether.items.food.ItemAmbrosiumShard;
import com.gildedgames.the_aether.items.food.ItemGummySwet;
import com.gildedgames.the_aether.items.food.ItemHealingStone;
import com.gildedgames.the_aether.items.food.ItemLifeShard;
import com.gildedgames.the_aether.items.food.ItemWhiteApple;
import com.gildedgames.the_aether.items.staffs.ItemCloudStaff;
import com.gildedgames.the_aether.items.staffs.ItemNatureStaff;
import com.gildedgames.the_aether.items.tools.ItemAetherParachute;
import com.gildedgames.the_aether.items.tools.ItemGravititeTool;
import com.gildedgames.the_aether.items.tools.ItemHolystoneTool;
import com.gildedgames.the_aether.items.tools.ItemSkyrootBucket;
import com.gildedgames.the_aether.items.tools.ItemSkyrootTool;
import com.gildedgames.the_aether.items.tools.ItemValkyrieTool;
import com.gildedgames.the_aether.items.tools.ItemZaniteTool;
import com.gildedgames.the_aether.items.util.EnumAetherToolType;
import com.gildedgames.the_aether.items.util.ItemAether;
import com.gildedgames.the_aether.items.util.ItemDeveloperStick;
import com.gildedgames.the_aether.items.util.ItemSwettyBall;
import com.gildedgames.the_aether.items.weapons.ItemCandyCaneSword;
import com.gildedgames.the_aether.items.weapons.ItemElementalSword;
import com.gildedgames.the_aether.items.weapons.ItemGravititeSword;
import com.gildedgames.the_aether.items.weapons.ItemHolystoneSword;
import com.gildedgames.the_aether.items.weapons.ItemLightningKnife;
import com.gildedgames.the_aether.items.weapons.ItemNotchHammer;
import com.gildedgames.the_aether.items.weapons.ItemPigSlayer;
import com.gildedgames.the_aether.items.weapons.ItemSkyrootSword;
import com.gildedgames.the_aether.items.weapons.ItemValkyrieLance;
import com.gildedgames.the_aether.items.weapons.ItemVampireBlade;
import com.gildedgames.the_aether.items.weapons.ItemZaniteSword;
import com.gildedgames.the_aether.items.weapons.projectile.ItemDart;
import com.gildedgames.the_aether.items.weapons.projectile.ItemDartShooter;
import com.gildedgames.the_aether.items.weapons.projectile.ItemPhoenixBow;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsAether {

	public static EnumRarity aether_loot = EnumHelper.addRarity("aether_legacy_loot", EnumChatFormatting.GREEN, "Aether Loot");

	public static Item zanite_gemstone, ambrosium_shard, golden_amber, aechor_petal, swet_ball;

	public static Item skyroot_pickaxe, skyroot_axe, skyroot_shovel, skyroot_sword;

	public static Item holystone_pickaxe, holystone_axe, holystone_shovel, holystone_sword;

	public static Item zanite_pickaxe, zanite_axe, zanite_shovel, zanite_sword;

	public static Item gravitite_pickaxe, gravitite_axe, gravitite_shovel, gravitite_sword;

	public static Item valkyrie_pickaxe, valkyrie_axe, valkyrie_shovel, valkyrie_sword;

	public static Item zanite_helmet, zanite_chestplate, zanite_leggings, zanite_boots;

	public static Item gravitite_helmet, gravitite_chestplate, gravitite_leggings, gravitite_boots;

	public static Item neptune_helmet, neptune_chestplate, neptune_leggings, neptune_boots;

	public static Item phoenix_helmet, phoenix_chestplate, phoenix_leggings, phoenix_boots;

	public static Item obsidian_helmet, obsidian_chestplate, obsidian_leggings, obsidian_boots;

	public static Item valkyrie_helmet, valkyrie_chestplate, valkyrie_leggings, valkyrie_boots;

	public static Item blueberry, gummy_swet, healing_stone, white_apple, gingerbread_man, candy_cane, enchanted_blueberry;

	public static Item skyroot_stick, victory_medal;

	public static Item dungeon_key, skyroot_bucket, cloud_parachute, golden_parachute;

	public static Item nature_staff, cloud_staff, moa_egg;

	public static Item dart_shooter, phoenix_bow, dart;

	public static Item flaming_sword, lightning_sword, holy_sword;

	public static Item vampire_blade, pig_slayer, candy_cane_sword, notch_hammer, valkyrie_lance;

	public static Item leather_gloves, iron_gloves, golden_gloves, chain_gloves, diamond_gloves;

	public static Item zanite_gloves, gravitite_gloves, neptune_gloves, phoenix_gloves, obsidian_gloves, valkyrie_gloves;

	public static Item iron_ring, golden_ring, zanite_ring, ice_ring, iron_pendant, golden_pendant, zanite_pendant, ice_pendant;

	public static Item white_cape, red_cape, blue_cape, yellow_cape, swet_cape, invisibility_cape, agility_cape, valkyrie_cape;

	public static Item golden_feather, regeneration_stone, iron_bubble, life_shard;

	public static Item sentry_boots, lightning_knife;

	public static Item aether_tune, ascending_dawn, welcoming_skies, legacy;

	public static Item repulsion_shield;

	public static Item lore_book;

	public static Item developer_stick;

	public static Item aether_spawn_egg;

	public static Item skyroot_bed_item;

	public static Item aether_portal_frame;

	public static void initialization() {
		zanite_gemstone = register("zanite_gemstone", new ItemAether(AetherCreativeTabs.material).setTextureName(Aether.find("misc/zanite_gemstone")));
		ambrosium_shard = register("ambrosium_shard", new ItemAmbrosiumShard().setTextureName(Aether.find("misc/ambrosium_shard")));
		golden_amber = register("golden_amber", new ItemAether(AetherCreativeTabs.material).setTextureName(Aether.find("misc/golden_amber")));
		aechor_petal = register("aechor_petal", new ItemAether(AetherCreativeTabs.material).setTextureName(Aether.find("misc/aechor_petal")));
		swet_ball = register("swet_ball", new ItemSwettyBall(AetherCreativeTabs.material).setTextureName(Aether.find("misc/swet_ball")));

		skyroot_pickaxe = register("skyroot_pickaxe", new ItemSkyrootTool(2.0F, EnumAetherToolType.PICKAXE).setTextureName(Aether.find("tools/skyroot_pickaxe")));
		skyroot_axe = register("skyroot_axe", new ItemSkyrootTool(3.0F, EnumAetherToolType.AXE).setTextureName(Aether.find("tools/skyroot_axe")));
		skyroot_shovel = register("skyroot_shovel", new ItemSkyrootTool(1.0F, EnumAetherToolType.SHOVEL).setTextureName(Aether.find("tools/skyroot_shovel")));

		holystone_pickaxe = register("holystone_pickaxe", new ItemHolystoneTool(2.0F, EnumAetherToolType.PICKAXE).setTextureName(Aether.find("tools/holystone_pickaxe")));
		holystone_axe = register("holystone_axe", new ItemHolystoneTool(3.0F, EnumAetherToolType.AXE).setTextureName(Aether.find("tools/holystone_axe")));
		holystone_shovel = register("holystone_shovel", new ItemHolystoneTool(1.0F, EnumAetherToolType.SHOVEL).setTextureName(Aether.find("tools/holystone_shovel")));

		zanite_pickaxe = register("zanite_pickaxe", new ItemZaniteTool(2.0F, EnumAetherToolType.PICKAXE).setTextureName(Aether.find("tools/zanite_pickaxe")));
		zanite_axe = register("zanite_axe", new ItemZaniteTool(3.0F, EnumAetherToolType.AXE).setTextureName(Aether.find("tools/zanite_axe")));
		zanite_shovel = register("zanite_shovel", new ItemZaniteTool(1.0F, EnumAetherToolType.SHOVEL).setTextureName(Aether.find("tools/zanite_shovel")));

		gravitite_pickaxe = register("gravitite_pickaxe", new ItemGravititeTool(2.0F, EnumAetherToolType.PICKAXE).setTextureName(Aether.find("tools/gravitite_pickaxe")));
		gravitite_axe = register("gravitite_axe", new ItemGravititeTool(3.0F, EnumAetherToolType.AXE).setTextureName(Aether.find("tools/gravitite_axe")));
		gravitite_shovel = register("gravitite_shovel", new ItemGravititeTool(1.0F, EnumAetherToolType.SHOVEL).setTextureName(Aether.find("tools/gravitite_shovel")));

		valkyrie_pickaxe = register("valkyrie_pickaxe", new ItemValkyrieTool(2.0F, EnumAetherToolType.PICKAXE).setTextureName(Aether.find("tools/valkyrie_pickaxe")));
		valkyrie_axe = register("valkyrie_axe", new ItemValkyrieTool(3.0F, EnumAetherToolType.AXE).setTextureName(Aether.find("tools/valkyrie_axe")));
		valkyrie_shovel = register("valkyrie_shovel", new ItemValkyrieTool(1.0F, EnumAetherToolType.SHOVEL).setTextureName(Aether.find("tools/valkyrie_shovel")));

		zanite_helmet = register("zanite_helmet", new ItemZaniteArmor(0, ArmorMaterial.IRON, "zanite", zanite_gemstone, 0x711ae8).setTextureName(Aether.find("armor/zanite_helmet")));
		zanite_chestplate = register("zanite_chestplate", new ItemZaniteArmor(1, ArmorMaterial.IRON, "zanite", zanite_gemstone, 0x711ae8).setTextureName(Aether.find("armor/zanite_chestplate")));
		zanite_leggings = register("zanite_leggings", new ItemZaniteArmor(2, ArmorMaterial.IRON, "zanite", zanite_gemstone, 0x711ae8).setTextureName(Aether.find("armor/zanite_leggings")));
		zanite_boots = register("zanite_boots", new ItemZaniteArmor(3, ArmorMaterial.IRON, "zanite", zanite_gemstone, 0x711ae8).setTextureName(Aether.find("armor/zanite_boots")));

		gravitite_helmet = register("gravitite_helmet", new ItemAetherArmor(0, ArmorMaterial.DIAMOND, "gravitite", Item.getItemFromBlock(BlocksAether.enchanted_gravitite), 0xe752db).setTextureName(Aether.find("armor/gravitite_helmet")));
		gravitite_chestplate = register("gravitite_chestplate", new ItemAetherArmor(1, ArmorMaterial.DIAMOND, "gravitite", Item.getItemFromBlock(BlocksAether.enchanted_gravitite), 0xe752db).setTextureName(Aether.find("armor/gravitite_chestplate")));
		gravitite_leggings = register("gravitite_leggings", new ItemAetherArmor(2, ArmorMaterial.DIAMOND, "gravitite", Item.getItemFromBlock(BlocksAether.enchanted_gravitite), 0xe752db).setTextureName(Aether.find("armor/gravitite_leggings")));
		gravitite_boots = register("gravitite_boots", new ItemAetherArmor(3, ArmorMaterial.DIAMOND, "gravitite", Item.getItemFromBlock(BlocksAether.enchanted_gravitite), 0xe752db).setTextureName(Aether.find("armor/gravitite_boots")));

		neptune_helmet = register("neptune_helmet", new ItemAetherArmor(0, ArmorMaterial.DIAMOND, "neptune", null, 0x2654FF).setTextureName(Aether.find("armor/neptune_helmet")));
		neptune_chestplate = register("neptune_chestplate", new ItemAetherArmor(1, ArmorMaterial.DIAMOND, "neptune", null, 0x2654FF).setTextureName(Aether.find("armor/neptune_chestplate")));
		neptune_leggings = register("neptune_leggings", new ItemAetherArmor(2, ArmorMaterial.DIAMOND, "neptune", null, 0x2654FF).setTextureName(Aether.find("armor/neptune_leggings")));
		neptune_boots = register("neptune_boots", new ItemAetherArmor(3, ArmorMaterial.DIAMOND, "neptune", null, 0x2654FF).setTextureName(Aether.find("armor/neptune_boots")));

		phoenix_helmet = register("phoenix_helmet", new ItemAetherArmor(0, ArmorMaterial.DIAMOND, "phoenix", null).setTextureName(Aether.find("armor/phoenix_helmet")));
		phoenix_chestplate = register("phoenix_chestplate", new ItemAetherArmor(1, ArmorMaterial.DIAMOND, "phoenix", null).setTextureName(Aether.find("armor/phoenix_chestplate")));
		phoenix_leggings = register("phoenix_leggings", new ItemAetherArmor(2, ArmorMaterial.DIAMOND, "phoenix", null).setTextureName(Aether.find("armor/phoenix_leggings")));
		phoenix_boots = register("phoenix_boots", new ItemAetherArmor(3, ArmorMaterial.DIAMOND, "phoenix", null).setTextureName(Aether.find("armor/phoenix_boots")));

		obsidian_helmet = register("obsidian_helmet", new ItemAetherArmor(0, ArmorMaterial.DIAMOND, "obsidian", null, 0x1b1447).setTextureName(Aether.find("armor/obsidian_helmet")));
		obsidian_chestplate = register("obsidian_chestplate", new ItemAetherArmor(1, ArmorMaterial.DIAMOND, "obsidian", null, 0x1b1447).setTextureName(Aether.find("armor/obsidian_chestplate")));
		obsidian_leggings = register("obsidian_leggings", new ItemAetherArmor(2, ArmorMaterial.DIAMOND, "obsidian", null, 0x1b1447).setTextureName(Aether.find("armor/obsidian_leggings")));
		obsidian_boots = register("obsidian_boots", new ItemAetherArmor(3, ArmorMaterial.DIAMOND, "obsidian", null, 0x1b1447).setTextureName(Aether.find("armor/obsidian_boots")));

		valkyrie_helmet = register("valkyrie_helmet", new ItemAetherArmor(0, ArmorMaterial.DIAMOND, "valkyrie", null).setTextureName(Aether.find("armor/valkyrie_helmet")));
		valkyrie_chestplate = register("valkyrie_chestplate", new ItemAetherArmor(1, ArmorMaterial.DIAMOND, "valkyrie", null).setTextureName(Aether.find("armor/valkyrie_chestplate")));
		valkyrie_leggings = register("valkyrie_leggings", new ItemAetherArmor(2, ArmorMaterial.DIAMOND, "valkyrie", null).setTextureName(Aether.find("armor/valkyrie_leggings")));
		valkyrie_boots = register("valkyrie_boots", new ItemAetherArmor(3, ArmorMaterial.DIAMOND, "valkyrie", null).setTextureName(Aether.find("armor/valkyrie_boots")));

		blueberry = register("blueberry", new ItemAetherFood(2).setTextureName(Aether.find("food/blueberry")));
		enchanted_blueberry = register("enchanted_blueberry", new ItemAetherFood(8).setTextureName(Aether.find("food/enchanted_blueberry")));
		white_apple = register("white_apple", new ItemWhiteApple().setTextureName(Aether.find("food/white_apple")));
		gummy_swet = register("gummy_swet", new ItemGummySwet());
		healing_stone = register("healing_stone", new ItemHealingStone().setTextureName(Aether.find("food/healing_stone")));
		candy_cane = register("candy_cane", new ItemAetherFood(2).setTextureName(Aether.find("food/candycane")));
		gingerbread_man = register("gingerbread_man", new ItemAetherFood(2).setTextureName(Aether.find("food/gingerbread_man")));

		skyroot_stick = register("skyroot_stick", new Item().setCreativeTab(AetherCreativeTabs.material).setTextureName(Aether.find("skyroot_stick")));
		victory_medal = register("victory_medal", new ItemVictoryMedal().setTextureName(Aether.find("victory_medal")));

		dungeon_key = register("dungeon_key", new ItemDungeonKey());
		skyroot_bucket = register("skyroot_bucket", new ItemSkyrootBucket());
		cloud_parachute = register("cold_parachute", new ItemAetherParachute().setTextureName(Aether.find("misc/parachutes/cold_parachute")));
		golden_parachute = register("golden_parachute", new ItemAetherParachute().setTextureName(Aether.find("misc/parachutes/golden_parachute")));

		moa_egg = register("moa_egg", new ItemMoaEgg());

		dart_shooter = register("dart_shooter", new ItemDartShooter());
		phoenix_bow = register("phoenix_bow", new ItemPhoenixBow());
		dart = register("dart", new ItemDart());

		skyroot_sword = register("skyroot_sword", new ItemSkyrootSword().setTextureName(Aether.find("weapons/skyroot_sword")));
		holystone_sword = register("holystone_sword", new ItemHolystoneSword().setTextureName(Aether.find("weapons/holystone_sword")));
		zanite_sword = register("zanite_sword", new ItemZaniteSword().setTextureName(Aether.find("weapons/zanite_sword")));
		gravitite_sword = register("gravitite_sword", new ItemGravititeSword().setTextureName(Aether.find("weapons/gravitite_sword")));

		flaming_sword = register("flaming_sword", new ItemElementalSword().setTextureName(Aether.find("weapons/flaming_sword")));
		lightning_sword = register("lightning_sword", new ItemElementalSword().setTextureName(Aether.find("weapons/lightning_sword")));
		holy_sword = register("holy_sword", new ItemElementalSword().setTextureName(Aether.find("weapons/holy_sword")));

		vampire_blade = register("vampire_blade", new ItemVampireBlade().setTextureName(Aether.find("weapons/vampire_blade")));
		pig_slayer = register("pig_slayer", new ItemPigSlayer().setTextureName(Aether.find("weapons/pig_slayer")));
		candy_cane_sword = register("candy_cane_sword", new ItemCandyCaneSword().setTextureName(Aether.find("weapons/candycane_sword")));
		notch_hammer = register("notch_hammer", new ItemNotchHammer().setTextureName(Aether.find("weapons/notch_hammer")));

		leather_gloves = register("leather_gloves", new ItemAccessoryDyed(AccessoryType.GLOVES).setTextureName(Aether.find("accessories/leather_gloves")));
		iron_gloves = register("iron_gloves", new ItemAccessory(AccessoryType.GLOVES).setTextureName(Aether.find("accessories/solid_gloves")));
		golden_gloves = register("golden_gloves", new ItemAccessory(AccessoryType.GLOVES).setColor(0xFBF424).setTextureName(Aether.find("accessories/solid_gloves")));
		chain_gloves = register("chain_gloves", new ItemAccessory(AccessoryType.GLOVES).setTexture("chain").setTextureName(Aether.find("accessories/chain_gloves")));
		diamond_gloves = register("diamond_gloves", new ItemAccessory(AccessoryType.GLOVES).setColor(0x33ebcb).setTextureName(Aether.find("accessories/solid_gloves")));

		zanite_gloves = register("zanite_gloves", new ItemAccessory(AccessoryType.GLOVES).setColor(0x711ae8).setTextureName(Aether.find("accessories/solid_gloves")));
		gravitite_gloves = register("gravitite_gloves", new ItemAccessory(AccessoryType.GLOVES).setColor(0xe752db).setTextureName(Aether.find("accessories/solid_gloves")));
		neptune_gloves = register("neptune_gloves", new ItemAccessory(AccessoryType.GLOVES).setDungeonLoot().setColor(0x2654FF).setTextureName(Aether.find("accessories/chain_gloves")));
		phoenix_gloves = register("phoenix_gloves", new ItemAccessory(AccessoryType.GLOVES).setTexture("phoenix").setDungeonLoot().setMaxDamage(152).setTextureName(Aether.find("accessories/phoenix_gloves")));
		obsidian_gloves = register("obsidian_gloves", new ItemAccessory(AccessoryType.GLOVES).setDungeonLoot().setColor(0x1b1447).setTextureName(Aether.find("accessories/solid_gloves")));
		valkyrie_gloves = register("valkyrie_gloves", new ItemAccessory(AccessoryType.GLOVES).setDungeonLoot().setTexture("valkyrie").setTextureName(Aether.find("accessories/valkyrie_gloves")));

		iron_ring = register("iron_ring", new ItemAccessory(AccessoryType.RING).setTextureName(Aether.find("accessories/ring_base")));
		golden_ring = register("golden_ring", new ItemAccessory(AccessoryType.RING).setColor(0xeaee57).setTextureName(Aether.find("accessories/ring_base")));
		zanite_ring = register("zanite_ring", new ItemAccessory(AccessoryType.RING).setColor(0x711ae8).setMaxDamage(49).setTextureName(Aether.find("accessories/ring_base")));
		ice_ring = register("ice_ring", new ItemAccessory(AccessoryType.RING).setColor(0x95e6e7).setMaxDamage(125).setTextureName(Aether.find("accessories/ring_base")));

		iron_pendant = register("iron_pendant", new ItemAccessory(AccessoryType.PENDANT).setTextureName(Aether.find("accessories/pendant_base")));
		golden_pendant = register("golden_pendant", new ItemAccessory(AccessoryType.PENDANT).setColor(0xeaee57).setTextureName(Aether.find("accessories/pendant_base")));
		zanite_pendant = register("zanite_pendant", new ItemAccessory(AccessoryType.PENDANT).setColor(0x711ae8).setMaxDamage(98).setTextureName(Aether.find("accessories/pendant_base")));
		ice_pendant = register("ice_pendant", new ItemAccessory(AccessoryType.PENDANT).setColor(0x95e6e7).setMaxDamage(250).setTextureName(Aether.find("accessories/pendant_base")));

		red_cape = register("red_cape", new ItemAccessory(AccessoryType.CAPE).setColor(0xe81111).setTextureName(Aether.find("accessories/cape_color_base")));
		blue_cape = register("blue_cape", new ItemAccessory(AccessoryType.CAPE).setColor(0x137fb7).setTextureName(Aether.find("accessories/cape_color_base")));
		yellow_cape = register("yellow_cape", new ItemAccessory(AccessoryType.CAPE).setColor(0xcdcb0e).setTextureName(Aether.find("accessories/cape_color_base")));
		white_cape = register("white_cape", new ItemAccessory(AccessoryType.CAPE).setTextureName(Aether.find("accessories/cape_color_base")));
		swet_cape = register("swet_cape", new ItemAccessory(AccessoryType.CAPE).setTexture("swet_cape").setDungeonLoot().setTextureName(Aether.find("accessories/swet_cape")));
		invisibility_cape = register("invisibility_cape", new ItemAccessory(AccessoryType.CAPE).setDungeonLoot().setTextureName(Aether.find("accessories/invisibility_cape")));
		agility_cape = register("agility_cape", new ItemAccessory(AccessoryType.CAPE).setTexture("agility_cape").setDungeonLoot().setTextureName(Aether.find("accessories/agility_cape")));
		valkyrie_cape = register("valkyrie_cape", new ItemAccessory(AccessoryType.CAPE).setTexture("valkyrie_cape").setDungeonLoot().setTextureName(Aether.find("accessories/valkyrie_cape")));

		golden_feather = register("golden_feather", new ItemAccessory(AccessoryType.MISC).setDungeonLoot().setTextureName(Aether.find("accessories/golden_feather")));
		regeneration_stone = register("regeneration_stone", new ItemAccessory(AccessoryType.MISC).setDungeonLoot().setTextureName(Aether.find("accessories/regeneration_stone")));
		iron_bubble = register("iron_bubble", new ItemAccessory(AccessoryType.MISC).setDungeonLoot().setTextureName(Aether.find("accessories/iron_bubble")));

		life_shard = register("life_shard", new ItemLifeShard().setTextureName(Aether.find("misc/life_shard")));
		cloud_staff = register("cloud_staff", new ItemCloudStaff().setTextureName(Aether.find("staff/cloud_staff")));
		nature_staff = register("nature_staff", new ItemNatureStaff().setTextureName(Aether.find("staff/nature_staff")));
		lightning_knife = register("lightning_knife", new ItemLightningKnife().setTextureName(Aether.find("weapons/lightning_knife")));

		valkyrie_lance = register("valkyrie_lance", new ItemValkyrieLance().setTextureName(Aether.find("weapons/valkyrie_lance")));
		sentry_boots = register("sentry_boots", new ItemAetherArmor(3, ArmorMaterial.DIAMOND, "sentry", null).setTextureName(Aether.find("armor/sentry_boots")));

		aether_tune = register("aether_tune", new ItemAetherDisc("aether_tune", "Noisestorm").setTextureName(Aether.find("music/aether_tune")));
		ascending_dawn = register("ascending_dawn", new ItemAetherDisc("ascending_dawn", "Emile van Krieken").setTextureName(Aether.find("music/ascending_dawn")));
		welcoming_skies = register("welcoming_skies", new ItemAetherDisc("welcoming_skies", "Voyed").setTextureName(Aether.find("music/welcoming_skies")));
		legacy = register("legacy", new ItemAetherDisc("legacy", "Jon Lachney").setTextureName(Aether.find("music/legacy")));

		repulsion_shield = register("repulsion_shield", new ItemAccessory(AccessoryType.SHIELD).setTexture("repulsion").setInactiveTexture("repulsion_movement").setDungeonLoot().setMaxDamage(512).setTextureName(Aether.find("accessories/repulsion_shield")));
		lore_book = register("lore_book", new ItemLoreBook().setTextureName(Aether.find("misc/lore_book")));

		developer_stick = register("developer_stick", new ItemDeveloperStick().setTextureName(Aether.find("skyroot_stick")));

		aether_spawn_egg = register("aether_spawn_egg", new ItemAetherSpawnEgg().setTextureName("spawn_egg"));

		skyroot_bed_item = register("skyroot_bed_item", new ItemSkyrootBed().setTextureName(Aether.find("skyroot_bed_item")));

		aether_portal_frame = register("aether_portal_frame", new ItemAetherPortalFrame().setTextureName(Aether.find("aether_portal_frame")));
	}

	public static Item register(String name, Item item) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name, Aether.MOD_ID);

		return item;
	}

}