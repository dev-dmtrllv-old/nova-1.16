package com.dmtrllv.nova.renderer;

import com.dmtrllv.nova.Nova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.Atlases;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

public final class NovaSignTextureStich
{
	public static Logger logger = LogManager.getLogger();

	public static final ResourceLocation WHITE_OAK = new ResourceLocation(Nova.MOD_ID, "entity/signs/white_oak");

	public static void onStitchEvent(final TextureStitchEvent.Pre event)
	{
		ResourceLocation stitching = event.getMap().location();

		if (stitching.equals(Atlases.SIGN_SHEET))
			event.addSprite(WHITE_OAK);
	}
}
