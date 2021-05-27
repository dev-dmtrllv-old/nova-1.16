package com.dmtrllv.nova.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class NovaSignTileEntity extends SignTileEntity
{

	@Override
	public TileEntityType<NovaSignTileEntity> getType()
	{
		return NovaTileEntityType.SIGN.get();
	}
}
