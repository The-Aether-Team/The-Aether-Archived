package com.legacy.aether.registry;

import com.legacy.aether.Aether;
import com.legacy.aether.block.BlocksAether;
import com.legacy.aether.block.natural.BlockAetherDirt;
import com.legacy.aether.block.natural.BlockAetherGrass;
import com.legacy.aether.block.natural.BlockHolystone;
import com.legacy.aether.entity.boss.EntitySlider;
import com.legacy.aether.entity.passive.EntityAerwhale;
import com.legacy.aether.item.group.AetherItemGroup;
import com.legacy.aether.world.AetherModDimension;
import com.legacy.aether.world.biome.HighlandsBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = "aether_legacy", bus = Bus.MOD)
public class AetherRegistryHandler
{

	@SubscribeEvent
	public static void onRegisterBlocks(Register<Block> event)
	{
		register(event.getRegistry(), "aether_grass", new BlockAetherGrass());
		register(event.getRegistry(), "aether_dirt", new BlockAetherDirt());
		register(event.getRegistry(), "holystone", new BlockHolystone());
	}

	@SubscribeEvent
	public static void onRegisterItems(Register<Item> event)
	{
		register(event.getRegistry(), "aether_grass", new ItemBlock(BlocksAether.AETHER_GRASS, (new Properties()).group(AetherItemGroup.BLOCKS)));
		register(event.getRegistry(), "aether_dirt", new ItemBlock(BlocksAether.AETHER_DIRT, (new Properties()).group(AetherItemGroup.BLOCKS)));
		register(event.getRegistry(), "holystone", new ItemBlock(BlocksAether.HOLYSTONE, (new Properties()).group(AetherItemGroup.BLOCKS)));
	}

	@SubscribeEvent
	public static void onRegisterEntityTypes(Register<EntityType<?>> event)
	{
		register(event.getRegistry(), "aerwhale", EntityType.Builder.create(EntityAerwhale.class, EntityAerwhale::new).disableSerialization().build(""));
		register(event.getRegistry(), "slider", EntityType.Builder.create(EntitySlider.class, EntitySlider::new).disableSerialization().build(""));
	}

	@SubscribeEvent
	public static void onRegisterSoundEvents(Register<SoundEvent> event)
	{
		register(event.getRegistry(), "slider.collide", new SoundEvent(Aether.locate("slider.collide")));
	}

	@SubscribeEvent
	public static void onRegisterBiomes(Register<Biome> event)
	{
		register(event.getRegistry(), "highlands", new HighlandsBiome());
	}

	@SubscribeEvent
	public static void onRegisterDimensions(Register<ModDimension> event)
	{
		register(event.getRegistry(), "aether", new AetherModDimension());
	}

	private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String name, T object)
	{
		object.setRegistryName(Aether.locate(name));
		registry.register(object);
	}

}