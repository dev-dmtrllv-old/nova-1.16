package com.dmtrllv.nova.tileentity;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.block.NovaBlocks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NovaTileEntityType
{
	public static final DeferredRegister<TileEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Nova.MOD_ID);

	public static final RegistryObject<TileEntityType<NovaSignTileEntity>> SIGN = REGISTRY.register("sign", () -> TileEntityType.Builder.of(NovaSignTileEntity::new, NovaBlocks.WHITE_OAK_SIGN.get(), NovaBlocks.WHITE_OAK_WALL_SIGN.get()).build(null));

}
