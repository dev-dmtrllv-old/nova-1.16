package com.dmtrllv.nova.world.gen.feature;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.block.NovaBlocks;
import com.google.common.collect.ImmutableList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.gen.feature.TwoLayerFeature;

public final class NovaFeatures
{
	public static final class ConfiguredFeatures
	{
		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> WHITE_OAK = create("white_oak", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(NovaBlocks.WHITE_OAK_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(NovaBlocks.WHITE_OAK_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));
		public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> WHITE_OAK_BEES = create("white_oak_bees", Feature.TREE.configured(WHITE_OAK.config().withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005))));
		
		public static final ConfiguredFeature<?, ?> TREES_WHITE_OAK = create("trees_white_oak", WHITE_OAK_BEES.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
	
		private static <FC extends IFeatureConfig, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF create(String id, CF configuredFeature)
		{
			ResourceLocation resID = new ResourceLocation(Nova.MOD_ID, id);
			if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(resID))
				throw new IllegalStateException("Configured Feature ID: \"" + resID.toString() + "\" already exists in the Configured Features registry!");
	
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resID, configuredFeature);
			return configuredFeature;
		}
	}
	
}
