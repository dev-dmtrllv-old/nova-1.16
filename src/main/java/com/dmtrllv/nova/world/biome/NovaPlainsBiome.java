package com.dmtrllv.nova.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.MobSpawnInfo.Builder;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class NovaPlainsBiome extends NovaBiome
{
	private boolean withSunflowers;
	private boolean withOutpst;
	private ConfiguredFeature<?, ?> plainsTreeFeature;

	public NovaPlainsBiome(BiomeType biomeType, float depth, float scale, boolean withSunflowers, boolean withOutpost, ConfiguredFeature<?, ?> plainsTreeFeature)
	{
		super(biomeType, depth, scale);
		this.withSunflowers = withSunflowers;
		this.withOutpst = withOutpost;
		this.plainsTreeFeature = plainsTreeFeature;
	}

	@Override
	protected void initMobs(Builder builder)
	{
		DefaultBiomeFeatures.plainsSpawns(builder);
		if (!withSunflowers)
			builder.setPlayerCanSpawn();
	}

	@Override
	protected void initFeatures(net.minecraft.world.biome.BiomeGenerationSettings.Builder genSettingsBuilder)
	{
		genSettingsBuilder.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		
		if (withOutpst == true || withSunflowers == false)
			genSettingsBuilder.addStructureStart(StructureFeatures.VILLAGE_PLAINS).addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
		
		DefaultBiomeFeatures.addDefaultOverworldLandStructures(genSettingsBuilder);
		genSettingsBuilder.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
		DefaultBiomeFeatures.addDefaultCarvers(genSettingsBuilder);
		DefaultBiomeFeatures.addDefaultLakes(genSettingsBuilder);
		DefaultBiomeFeatures.addDefaultMonsterRoom(genSettingsBuilder);
		DefaultBiomeFeatures.addPlainGrass(genSettingsBuilder);
		
		DefaultBiomeFeatures.addDefaultUndergroundVariety(genSettingsBuilder);
		DefaultBiomeFeatures.addDefaultOres(genSettingsBuilder);
		DefaultBiomeFeatures.addDefaultSoftDisks(genSettingsBuilder);

		genSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, plainsTreeFeature);
		genSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_PLAIN_DECORATED);
		genSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);

		if (withSunflowers)
		{
			genSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);
			genSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE);
			genSettingsBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
		}
		else
		{
			DefaultBiomeFeatures.addDefaultExtraVegetation(genSettingsBuilder);
		}

		DefaultBiomeFeatures.addDefaultMushrooms(genSettingsBuilder);
		DefaultBiomeFeatures.addDefaultSprings(genSettingsBuilder);
		DefaultBiomeFeatures.addSurfaceFreezing(genSettingsBuilder);
	}

	@Override
	protected void initBiome(net.minecraft.world.biome.Biome.Builder builder)
	{
		builder
			.precipitation(Biome.RainType.RAIN)
			.biomeCategory(Biome.Category.PLAINS)
			.temperature(0.8F)
			.downfall(0.4F)
			.specialEffects((new BiomeAmbience.Builder())
			.waterColor(4159204)
			.waterFogColor(329011)
			.fogColor(12638463)
			.skyColor(calculateSkyColor(0.8F))
			.ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build());
	}
}
