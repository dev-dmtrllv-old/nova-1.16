package com.dmtrllv.nova.block.trees;

import java.util.Random;
import javax.annotation.Nullable;

import com.dmtrllv.nova.world.gen.feature.NovaFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class WhiteOakTree extends Tree
{

	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean withBeeHive)
	{
		// if (rand.nextInt(10) == 0) {
		// return withBeeHive ? Features.FANCY_OAK_BEES_005 : Features.FANCY_OAK;
		// } else {
		// return withBeeHive ? Features.OAK_BEES_005 : Features.OAK;
		// }
		return (ConfiguredFeature<BaseTreeFeatureConfig, ?>) NovaFeatures.WHITE_OAK.get();
	}
}
