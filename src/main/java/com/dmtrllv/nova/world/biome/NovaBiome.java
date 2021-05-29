package com.dmtrllv.nova.world.biome;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.BiomeGenerationSettings.Builder;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;

public abstract class NovaBiome
{
	public static final List<NovaBiome> BIOMES = new ArrayList<>();

	protected static int calculateSkyColor(float val)
	{
		float lvt_1_1_ = val / 3.0F;
		lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
		return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}

	@Nullable
	private Biome biome = null;

	private float depth = 1.0f;

	private float scale = 1.0f;

	private BiomeType biomeType;

	public Biome getBiome()
	{
		if (biome == null)
		{
			Biome.Builder biomeBuilder = new Biome.Builder();
			MobSpawnInfo.Builder mobSpawnInfoBuilder = new MobSpawnInfo.Builder();
			BiomeGenerationSettings.Builder genSettingsBuilder = new BiomeGenerationSettings.Builder();

			this.initMobs(mobSpawnInfoBuilder);
			this.initFeatures(genSettingsBuilder);
			this.initBiome(biomeBuilder);

			biome = biomeBuilder.depth(depth).scale(scale).mobSpawnSettings(mobSpawnInfoBuilder.build()).generationSettings(genSettingsBuilder.build()).build();
		}
		return biome;
	}

	public BiomeManager.BiomeType getBiomeType() { return biomeType; }

	public NovaBiome(BiomeManager.BiomeType biomeType, float depth, float scale)
	{
		this.biomeType = biomeType;
		this.depth = depth;
		this.scale = scale;
		BIOMES.add(this);
	}

	protected abstract void initMobs(MobSpawnInfo.Builder builder);

	protected abstract void initFeatures(Builder genSettingsBuilder);

	protected abstract void initBiome(Biome.Builder builder);
}
