// package com.dmtrllv.nova.world.biome;

// import net.minecraft.util.math.MathHelper;
// import net.minecraft.world.biome.Biome;
// import net.minecraft.world.biome.BiomeAmbience;
// import net.minecraft.world.biome.BiomeGenerationSettings;
// import net.minecraft.world.biome.DefaultBiomeFeatures;
// import net.minecraft.world.biome.MobSpawnInfo;
// import net.minecraft.world.biome.MoodSoundAmbience;
// import net.minecraft.world.gen.GenerationStage;
// import net.minecraft.world.gen.feature.ConfiguredFeature;
// import net.minecraft.world.gen.feature.structure.StructureFeatures;
// import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

// public final class NovaBiomeMaker
// {
// 	private static int calculateSkyColor(float val)
// 	{
// 		float lvt_1_1_ = val / 3.0F;
// 		lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
// 		return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
// 	}

// 	public static Biome forestBiome(float depth, float scale, ConfiguredFeature<?, ?> treesFeature)
// 	{
// 		MobSpawnInfo.Builder msb = new MobSpawnInfo.Builder();
// 		DefaultBiomeFeatures.farmAnimals(msb);
// 		DefaultBiomeFeatures.commonSpawns(msb);

// 		BiomeGenerationSettings.Builder genSettings = (new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
// 		DefaultBiomeFeatures.addDefaultOverworldLandStructures(genSettings);
// 		genSettings.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
// 		DefaultBiomeFeatures.addDefaultCarvers(genSettings);
// 		DefaultBiomeFeatures.addDefaultLakes(genSettings);
// 		DefaultBiomeFeatures.addDefaultMonsterRoom(genSettings);
// 		DefaultBiomeFeatures.addForestFlowers(genSettings);
// 		DefaultBiomeFeatures.addDefaultUndergroundVariety(genSettings);
// 		DefaultBiomeFeatures.addDefaultOres(genSettings);
// 		DefaultBiomeFeatures.addDefaultSoftDisks(genSettings);
		
// 		genSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, treesFeature);

// 		DefaultBiomeFeatures.addDefaultFlowers(genSettings);
// 		DefaultBiomeFeatures.addForestGrass(genSettings);
// 		DefaultBiomeFeatures.addDefaultMushrooms(genSettings);
// 		DefaultBiomeFeatures.addDefaultExtraVegetation(genSettings);
// 		DefaultBiomeFeatures.addDefaultSprings(genSettings);
// 		DefaultBiomeFeatures.addSurfaceFreezing(genSettings);

// 		return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.FOREST).depth(depth).scale(scale).temperature(0.6F).downfall(0.6F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.6F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(msb.build()).generationSettings(genSettings.build()).build();
// 	}
// }
