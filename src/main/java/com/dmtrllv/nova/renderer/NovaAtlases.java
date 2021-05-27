package com.dmtrllv.nova.renderer;

import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;

public class NovaAtlases
{
	public static RenderMaterial signTexture(WoodType woodType)
	{
		ResourceLocation location = new ResourceLocation(woodType.name());
		return new RenderMaterial(Atlases.SIGN_SHEET, new ResourceLocation(location.getNamespace(), "entity/signs/" + location.getPath()));
	}
}
