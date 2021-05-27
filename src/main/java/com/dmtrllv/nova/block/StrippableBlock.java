package com.dmtrllv.nova.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class StrippableBlock extends RotatedPillarBlock
{

	private final RegistryObject<Block> strippedBlock;

	public StrippableBlock(Properties properties, RegistryObject<Block> strippedBlock)
	{
		super(properties);
		this.strippedBlock = strippedBlock;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayResult)
	{

		if (player.getItemInHand(hand).getItem() instanceof AxeItem)
		{
			BlockState bs = world.getBlockState(pos);
			world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide())
				world.setBlock(pos, strippedBlock.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, bs.getValue(RotatedPillarBlock.AXIS)), 11);
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}
}
