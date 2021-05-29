package com.dmtrllv.nova.world.biome;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.world.gen.feature.NovaConfiguredFeatures;
import com.google.common.base.Supplier;

import org.apache.logging.log4j.LogManager;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NovaBiomes
{
	public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, Nova.MOD_ID);

	public static void addBiomeEntries()
	{
		for (RegistryObject<Biome> biome : REGISTRY.getEntries())
		{
			for (NovaBiome nb : NovaBiome.BIOMES)
				if (nb.getBiome() == biome.get())
				{
					BiomeManager.addBiome(nb.getBiomeType(), new BiomeEntry(RegistryKey.create(Registry.BIOME_REGISTRY, biome.getId()), 10));
					break;
				}
		}
	}

	public static void fillBiomeDictionary()
	{
		for (RegistryObject<Biome> biome : REGISTRY.getEntries())
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, biome.getId()), new BiomeDictionary.Type[] { BiomeDictionary.Type.OVERWORLD });
	}

	// public static void overwriteVanillaBiomes(Collection<Biome> biomes)
	// {
	// 	LogManager.getLogger().info("OVERWRITE VANILLA BIOMES");
	// 	for (Biome biome : biomes)
	// 	{
			
	// 	}
	// }

	private static RegistryObject<Biome> register(String id, Supplier<NovaBiome> biomeSupplier)
	{
		return REGISTRY.register(id, () -> biomeSupplier.get().getBiome());
	}

	public static void onBiomeLoaded(BiomeLoadingEvent biome)
	{
		// LogManager.getLogger().info("on biome load " + biome.getName());
		if (biome.getCategory() == Biome.Category.PLAINS || biome.getCategory() == Biome.Category.RIVER)
		{
			// add pebbles
			LogManager.getLogger().info("pebbles added to " + biome.getName());
			biome.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> NovaConfiguredFeatures.PATCH_PEBBLES.get());
			
		}
	}

	public static final RegistryObject<Biome> WHITE_OAK_FOREST = register("white_oak_forest", () -> new NovaForestBiome(BiomeManager.BiomeType.WARM, 0.15F, 0.15F, NovaConfiguredFeatures.TREES_WHITE_OAK_FOREST.get()));
	public static final RegistryObject<Biome> WHITE_OAK_PLAINS = register("white_oak_plains", () -> new NovaPlainsBiome(BiomeManager.BiomeType.WARM, 0.125F, 0.05F, false, false, NovaConfiguredFeatures.TREES_WHITE_OAK_PLAINS.get()));
	public static final RegistryObject<Biome> WHITE_OAK_SUNFLOWER_PLAINS = register("white_oak_sunflower_plains", () -> new NovaPlainsBiome(BiomeManager.BiomeType.WARM, 0.125F, 0.05F, true, false, NovaConfiguredFeatures.TREES_WHITE_OAK_PLAINS.get()));

}
