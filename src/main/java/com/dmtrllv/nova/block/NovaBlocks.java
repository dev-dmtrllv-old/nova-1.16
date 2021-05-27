package com.dmtrllv.nova.block;

import java.util.Arrays;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.block.trees.WhiteOakTree;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class NovaBlocks
{
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Nova.MOD_ID);

	public static void setCutOutRenderers()
	{
		Arrays.asList(WHITE_OAK_DOOR, WHITE_OAK_TRAPDOOR, WHITE_OAK_SAPLING).forEach((RegistryObject<Block> b) -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
	}

	public static void setSolidRenderers()
	{
		Arrays.asList(PEBBLE).forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.solid()));
	}

	private static Boolean never(BlockState bs, IBlockReader br, BlockPos pos, EntityType<?> et)
	{
		return (boolean) false;
	}

	// private static StrippableBlock log(RegistryObject<Block> strippedBlock,
	// MaterialColor innerColor, MaterialColor outerColor) {
	// return new StrippableBlock(AbstractBlock.Properties.of(Material.WOOD,
	// (blockState) -> {
	// return blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
	// innerColor : outerColor;
	// }).strength(2.0F).sound(SoundType.WOOD), strippedBlock);
	// }

	private static StrippableBlock log(RegistryObject<Block> strippedBlock, MaterialColor color)
	{
		return new StrippableBlock(AbstractBlock.Properties.of(Material.WOOD, color).strength(2.0F).sound(SoundType.WOOD), strippedBlock);
	}

	// private static RotatedPillarBlock log(MaterialColor innerColor, MaterialColor
	// outerColor) {
	// return new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
	// (blockState) -> {
	// return blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ?
	// innerColor : outerColor;
	// }).strength(2.0F).sound(SoundType.WOOD));
	// }

	private static RotatedPillarBlock log(MaterialColor color)
	{
		return new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, color).strength(2.0F).sound(SoundType.WOOD));
	}

	private static LeavesBlock leaves()
	{
		return new LeavesBlock(AbstractBlock.Properties.copy(Blocks.OAK_LEAVES));
	}

	public static final RegistryObject<Block> STRIPPED_WHITE_OAK_LOG = REGISTRY.register("stripped_white_oak_log", () -> log(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> WHITE_OAK_LOG = REGISTRY.register("white_oak_log", () -> log(STRIPPED_WHITE_OAK_LOG, MaterialColor.QUARTZ));
	public static final RegistryObject<Block> STRIPPED_WHITE_OAK_WOOD = REGISTRY.register("stripped_white_oak_wood", () -> log(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> WHITE_OAK_WOOD = REGISTRY.register("white_oak_wood", () -> log(STRIPPED_WHITE_OAK_WOOD, MaterialColor.QUARTZ));
	public static final RegistryObject<Block> WHITE_OAK_PLANKS = REGISTRY.register("white_oak_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.QUARTZ).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_STAIRS = REGISTRY.register("white_oak_stairs", () -> new StairsBlock(() -> WHITE_OAK_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(WHITE_OAK_PLANKS.get())));
	public static final RegistryObject<Block> WHITE_OAK_SLAB = REGISTRY.register("white_oak_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(WHITE_OAK_PLANKS.get())));
	public static final RegistryObject<Block> WHITE_OAK_DOOR = REGISTRY.register("white_oak_door", () -> new DoorBlock(Block.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> WHITE_OAK_TRAPDOOR = REGISTRY.register("white_oak_trapdoor", () -> new TrapDoorBlock(Block.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(NovaBlocks::never)));
	public static final RegistryObject<Block> WHITE_OAK_BUTTON = REGISTRY.register("white_oak_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_PRESSURE_PLATE = REGISTRY.register("white_oak_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).noCollission().strength(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_FENCE = REGISTRY.register("white_oak_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_FENCE_GATE = REGISTRY.register("white_oak_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, WHITE_OAK_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WHITE_OAK_SIGN = REGISTRY.register("white_oak_sign", () -> new NovaStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), NovaWoodType.WHITE_OAK));
	public static final RegistryObject<Block> WHITE_OAK_WALL_SIGN = REGISTRY.register("white_oak_wall_sign", () -> new NovaWallSignBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> WHITE_OAK_SIGN.get()), NovaWoodType.WHITE_OAK));
	public static final RegistryObject<Block> WHITE_OAK_SAPLING = REGISTRY.register("white_oak_sapling", () -> new SaplingBlock(new WhiteOakTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block> WHITE_OAK_LEAVES = REGISTRY.register("white_oak_leaves", () -> leaves());
	public static final RegistryObject<Block> PEBBLE = REGISTRY.register("pebble", () -> new PebbleBlock(AbstractBlock.Properties.of(Material.EGG).noCollission().strength(0.5F).sound(SoundType.METAL).noOcclusion()));
}
