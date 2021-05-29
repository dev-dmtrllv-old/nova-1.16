package com.dmtrllv.nova.world.gen.feature;

import com.dmtrllv.nova.state.properties.NovaBlockStateProperties;
import com.mojang.serialization.Codec;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class PebblePatchFeature extends Feature<BlockClusterFeatureConfig>
{
	public PebblePatchFeature(Codec<BlockClusterFeatureConfig> codec)
	{
		super(codec);
	}

	public boolean place(ISeedReader reader, ChunkGenerator gen, Random rand, BlockPos pos, BlockClusterFeatureConfig config)
	{
		BlockState blockstate = config.stateProvider.getState(rand, pos);
		BlockPos blockpos;
		if (config.project)
		{
			blockpos = reader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE_WG, pos);
		}
		else
		{
			blockpos = pos;
		}

		int i = 0;

		BlockPos.Mutable p = new BlockPos.Mutable();

		for (int j = 0; j < config.tries; ++j)
		{
			p.setWithOffset(blockpos, rand.nextInt(config.xspread + 1) - rand.nextInt(config.xspread + 1), rand.nextInt(config.yspread + 1) - rand.nextInt(config.yspread + 1), rand.nextInt(config.zspread + 1) - rand.nextInt(config.zspread + 1));
			BlockPos blockpos1 = p.below();
			BlockState blockstate1 = reader.getBlockState(blockpos1);
			if ((reader.isEmptyBlock(p) || config.canReplace && reader.getBlockState(p).getMaterial().isReplaceable()) && blockstate.canSurvive(reader, p) && (config.whitelist.isEmpty() || config.whitelist.contains(blockstate1.getBlock())) && !config.blacklist.contains(blockstate1) && (!config.needWater || reader.getFluidState(blockpos1.west()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.east()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.north()).is(FluidTags.WATER) || reader.getFluidState(blockpos1.south()).is(FluidTags.WATER)))
			{
				config.blockPlacer.place(reader, p,  blockstate.setValue(NovaBlockStateProperties.PEBBLES, Integer.valueOf(rand.nextInt(3) + 1)), rand);
				++i;
			}
		}

		return i > 0;
	}
}
