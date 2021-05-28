package com.dmtrllv.nova.world.biome;

import com.dmtrllv.nova.Nova;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NovaBiomes
{
	public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, Nova.MOD_ID);

	public static void addBiomeEntries()
	{
		for (RegistryObject<Biome> biome : REGISTRY.getEntries())
			BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeEntry(RegistryKey.create(Registry.BIOME_REGISTRY, biome.getId()), 10));
	}

	public static void fillBiomeDictionary()
	{
		for (RegistryObject<Biome> biome : REGISTRY.getEntries())
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, biome.getId()), new BiomeDictionary.Type[]{BiomeDictionary.Type.OVERWORLD});
	}

	public static final RegistryObject<Biome> WHITE_OAK_FOREST = REGISTRY.register("white_oak_forest", () -> NovaBiomeMaker.forestBiome(0.2F, 0.4F, false));
}
