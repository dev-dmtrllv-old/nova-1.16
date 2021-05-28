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
		return withBeeHive ? NovaFeatures.ConfiguredFeatures.WHITE_OAK_BEES : NovaFeatures.ConfiguredFeatures.WHITE_OAK;
	}
}
