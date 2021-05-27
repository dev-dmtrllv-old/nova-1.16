package com.dmtrllv.nova.world.gen.feature;

import com.dmtrllv.nova.Registry;
import com.dmtrllv.nova.block.NovaBlocks;

import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.gen.feature.TwoLayerFeature;

public final class NovaFeatures
{
	private static final Registry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTRY = new Registry<>();
	private static final Registry<ConfiguredFeature<BaseTreeFeatureConfig, ?>> CONFIGURED_TREE_FEATURE_REGISTRY = new Registry<>();

	public static final Registry<ConfiguredFeature<BaseTreeFeatureConfig, ?>>.Object WHITE_OAK = CONFIGURED_TREE_FEATURE_REGISTRY.register("white_oak", () -> Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(NovaBlocks.WHITE_OAK_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(NovaBlocks.WHITE_OAK_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));

	public static void register()
	{
		CONFIGURED_FEATURE_REGISTRY.register();
		CONFIGURED_TREE_FEATURE_REGISTRY.register();
	}
}
