package com.dmtrllv.nova.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.MobSpawnInfo.Builder;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeManager;

public class NovaForestBiome extends NovaBiome
{
	protected ConfiguredFeature<?, ?> treesFeature;

	public NovaForestBiome(BiomeManager.BiomeType biomeType, float depth, float scale, ConfiguredFeature<?,?> treesFeature)
	{
		super(biomeType, depth, scale);
		this.treesFeature = treesFeature;
	}

	@Override
	protected void initMobs(Builder builder)
	{
		DefaultBiomeFeatures.farmAnimals(builder);
		DefaultBiomeFeatures.commonSpawns(builder);
	}

	@Override
	protected void initFeatures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder)
	{
		builder.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		DefaultBiomeFeatures.addDefaultOverworldLandStructures(builder);
		builder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
		DefaultBiomeFeatures.addDefaultCarvers(builder);
		DefaultBiomeFeatures.addDefaultLakes(builder);
		DefaultBiomeFeatures.addDefaultMonsterRoom(builder);
		DefaultBiomeFeatures.addForestFlowers(builder);
		DefaultBiomeFeatures.addDefaultUndergroundVariety(builder);
		DefaultBiomeFeatures.addDefaultOres(builder);
		DefaultBiomeFeatures.addDefaultSoftDisks(builder);
		
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, treesFeature);

		DefaultBiomeFeatures.addDefaultFlowers(builder);
		DefaultBiomeFeatures.addForestGrass(builder);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addDefaultExtraVegetation(builder);
		DefaultBiomeFeatures.addDefaultSprings(builder);
		DefaultBiomeFeatures.addSurfaceFreezing(builder);
	}

	@Override
	protected void initBiome(net.minecraft.world.biome.Biome.Builder builder)
	{
		builder.precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.FOREST).temperature(0.6F).downfall(0.6F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.6F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build());
	}
}
