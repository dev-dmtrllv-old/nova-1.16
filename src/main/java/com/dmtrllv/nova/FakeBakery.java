package com.dmtrllv.nova;

import java.util.Set;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;

public class FakeBakery extends ModelBakery
{
	private FakeBakery(IResourceManager resourceManagerIn, BlockColors blockColorsIn, IProfiler profilerIn, int maxMipmapLevel)
	{
		super(resourceManagerIn, blockColorsIn, profilerIn, maxMipmapLevel);
	}

	static Set<RenderMaterial> getBuiltinTextures()
	{
		return ModelBakery.UNREFERENCED_TEXTURES;
	}
}
