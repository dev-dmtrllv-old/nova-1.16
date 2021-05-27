package com.dmtrllv.nova.block;

import com.dmtrllv.nova.item.NovaItems;
import com.dmtrllv.nova.state.properties.NovaBlockStateProperties;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PebbleBlock extends Block
{
	private static final VoxelShape ONE_PEBBLE_AABB = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
	private static final VoxelShape MULTIPLE_PEBBLES_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
	public static final IntegerProperty PEBBLES = NovaBlockStateProperties.PEBBLES;

	public PebbleBlock(AbstractBlock.Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PEBBLES, Integer.valueOf(1)));
	}

	private boolean canSurviveOnBlock(BlockState s)
	{
		return s.is(Blocks.GRASS_BLOCK) || s.is(Blocks.DIRT) || s.is(Blocks.COARSE_DIRT) || s.is(Blocks.PODZOL) || s.is(Blocks.FARMLAND) || s.is(Blocks.GRASS_PATH);
	}

	@Override
	public boolean canSurvive(BlockState blockState, IWorldReader reader, BlockPos pos)
	{
		BlockPos blockpos = pos.below();
		return canSurviveOnBlock(reader.getBlockState(blockpos));
	}

	@Override @SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState s, Direction dir, BlockState s2, IWorld world, BlockPos pos, BlockPos pos2)
	{
		return !s.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(s, dir, s2, world, pos, pos2);
	}

	private void decreasePebbles(World world, BlockPos blockPos, BlockState blockState)
	{
		world.playSound((PlayerEntity) null, blockPos, SoundEvents.STONE_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
		int i = blockState.getValue(PEBBLES);
		if (i <= 1)
		{
			world.destroyBlock(blockPos, false);
		} 
		else
		{
			world.setBlock(blockPos, blockState.setValue(PEBBLES, Integer.valueOf(i - 1)), 2);
			world.levelEvent(2001, blockPos, Block.getId(blockState));
		}

	}

	public void playerDestroy(World p_180657_1_, PlayerEntity p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_)
	{
		super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, p_180657_5_, p_180657_6_);
		this.decreasePebbles(p_180657_1_, p_180657_3_, p_180657_4_);
	}

	@SuppressWarnings("deprecation")
	public boolean canBeReplaced(BlockState p_196253_1_, BlockItemUseContext p_196253_2_)
	{
		return p_196253_2_.getItemInHand().getItem() == this.asItem() && p_196253_1_.getValue(PEBBLES) < 4 ? true : super.canBeReplaced(p_196253_1_, p_196253_2_);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_)
	{
		BlockState blockstate = p_196258_1_.getLevel().getBlockState(p_196258_1_.getClickedPos());
		return blockstate.is(this) ? blockstate.setValue(PEBBLES, Integer.valueOf(Math.min(4, blockstate.getValue(PEBBLES) + 1))) : super.getStateForPlacement(p_196258_1_);
	}

	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_)
	{
		return p_220053_1_.getValue(PEBBLES) > 1 ? MULTIPLE_PEBBLES_AABB : ONE_PEBBLE_AABB;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PEBBLES);
	}

	@Override @SuppressWarnings("deprecation")
	public void neighborChanged(BlockState blockState, World world, BlockPos posA, Block block, BlockPos posB, boolean bool)
	{
		super.neighborChanged(blockState, world, posA, block, posB, bool);

		if (!canSurviveOnBlock(world.getBlockState(posA.below())))
		{
			int l = blockState.getValue(PEBBLES).intValue() - 1;
			for (int i = 0; i < l; i++)
				dropResources(blockState, world, posA, null, null, new ItemStack(NovaItems.PEBBLE.get()));
		}
	}
}
