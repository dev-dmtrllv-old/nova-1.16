package com.dmtrllv.nova.renderer;

import java.util.Random;

import javax.annotation.Nullable;

import com.dmtrllv.nova.Nova;
import com.dmtrllv.nova.world.events.BloodMoonEvent;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.FogRenderer.FogType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.world.DimensionRenderInfo;

public class NovaSkyRenderHandler implements ISkyRenderHandler
{
	private static final NovaSkyRenderHandler INSTANCE = new NovaSkyRenderHandler();

	@Nullable
	private static Minecraft minecraft = null;

	public static void onFogDensityEvent(EntityViewRenderEvent.FogDensity event)
	{
		if(minecraft != null && minecraft.player.level.dimension() == World.OVERWORLD && event.getType() == FogType.FOG_TERRAIN && BloodMoonEvent.isBloodMoonActive())
		{
			event.setDensity(0.35F);
		}
	}

	public static void onFogColorEvent(EntityViewRenderEvent.FogColors event)
	{
		if(minecraft != null && minecraft.player.level.dimension() == World.OVERWORLD)
			event.setRed(BloodMoonEvent.getRedColor(event.getRed()));
	}

	private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");

	private static final ResourceLocation BLOOD_MOON_LOCATION = new ResourceLocation(Nova.MOD_ID, "textures/environment/moon_phases.png");

	public static void initialize(RenderWorldLastEvent event)
	{
		NovaSkyRenderHandler.minecraft = Minecraft.getInstance();
		minecraft.level.effects().setSkyRenderHandler(INSTANCE);
	}

	@Nullable
	private VertexBuffer starBuffer;

	@Nullable
	private VertexBuffer skyBuffer;

	@Nullable
	private VertexBuffer darkBuffer;

	@Nullable
	private VertexBuffer darkBloodMoonBuffer;

	private final ClientWorld level;

	private final VertexFormat skyFormat = DefaultVertexFormats.POSITION;

	private TextureManager textureManager;

	private NovaSkyRenderHandler()
	{
		minecraft = Minecraft.getInstance();
		this.level = minecraft.level;
		this.textureManager = minecraft.getTextureManager();

		this.createStars();
		this.createLightSky();
		this.createDarkSky();
		this.createBloodMoonSky();
	}

	private void createBloodMoonSky()
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		if (this.darkBloodMoonBuffer != null)
		{
			this.darkBloodMoonBuffer.close();
		}

		this.darkBloodMoonBuffer = new VertexBuffer(this.skyFormat);
		this.drawSkyHemisphere(bufferbuilder, -16.0F, true);
		bufferbuilder.end();
		this.darkBloodMoonBuffer.upload(bufferbuilder);
	}

	private void drawSkyHemisphere(BufferBuilder builder, float p_174968_2_, boolean p_174968_3_)
	{
		builder.begin(7, DefaultVertexFormats.POSITION);

		for (int k = -384; k <= 384; k += 64)
		{
			for (int l = -384; l <= 384; l += 64)
			{
				float f = (float) k;
				float f1 = (float) (k + 64);
				if (p_174968_3_)
				{
					f1 = (float) k;
					f = (float) (k + 64);
				}

				builder.vertex((double) f, (double) p_174968_2_, (double) l).endVertex();
				builder.vertex((double) f1, (double) p_174968_2_, (double) l).endVertex();
				builder.vertex((double) f1, (double) p_174968_2_, (double) (l + 64)).endVertex();
				builder.vertex((double) f, (double) p_174968_2_, (double) (l + 64)).endVertex();
			}
		}
	}

	private void createDarkSky()
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		if (this.darkBuffer != null)
		{
			this.darkBuffer.close();
		}

		this.darkBuffer = new VertexBuffer(this.skyFormat);
		this.drawSkyHemisphere(bufferbuilder, -16.0F, true);
		bufferbuilder.end();
		this.darkBuffer.upload(bufferbuilder);
	}

	private void createStars()
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		if (this.starBuffer != null)
		{
			this.starBuffer.close();
		}

		this.starBuffer = new VertexBuffer(this.skyFormat);
		this.drawStars(bufferbuilder);
		bufferbuilder.end();
		this.starBuffer.upload(bufferbuilder);
	}

	@SuppressWarnings("unused")
	private void drawStars(BufferBuilder p_180444_1_)
	{
		Random random = new Random(10842L);
		p_180444_1_.begin(7, DefaultVertexFormats.POSITION);

		for (int i = 0; i < 1500; ++i)
		{
			double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d4 < 1.0D && d4 > 0.01D)
			{
				d4 = 1.0D / Math.sqrt(d4);
				d0 = d0 * d4;
				d1 = d1 * d4;
				d2 = d2 * d4;
				double d5 = d0 * 100.0D;
				double d6 = d1 * 100.0D;
				double d7 = d2 * 100.0D;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = random.nextDouble() * Math.PI * 2.0D;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for (int j = 0; j < 4; ++j)
				{
					double d17 = 0.0D;
					double d18 = (double) ((j & 2) - 1) * d3;
					double d19 = (double) ((j + 1 & 2) - 1) * d3;
					double d20 = 0.0D;
					double d21 = d18 * d16 - d19 * d15;
					double d22 = d19 * d16 + d18 * d15;
					double d23 = d21 * d12 + 0.0D * d13;
					double d24 = 0.0D * d12 - d21 * d13;
					double d25 = d24 * d9 - d22 * d10;
					double d26 = d22 * d9 + d24 * d10;
					p_180444_1_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}

	}

	private void createLightSky()
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		if (this.skyBuffer != null)
		{
			this.skyBuffer.close();
		}

		this.skyBuffer = new VertexBuffer(this.skyFormat);
		this.drawSkyHemisphere(bufferbuilder, 16.0F, false);
		bufferbuilder.end();
		this.skyBuffer.upload(bufferbuilder);
	}

	@SuppressWarnings("deprecation")
	private void renderEndSky(MatrixStack p_228444_1_)
	{
		RenderSystem.disableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		this.textureManager.bind(END_SKY_LOCATION);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();

		for (int i = 0; i < 6; ++i)
		{
			p_228444_1_.pushPose();
			if (i == 1)
			{
				p_228444_1_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			}

			if (i == 2)
			{
				p_228444_1_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
			}

			if (i == 3)
			{
				p_228444_1_.mulPose(Vector3f.XP.rotationDegrees(180.0F));
			}

			if (i == 4)
			{
				p_228444_1_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			}

			if (i == 5)
			{
				p_228444_1_.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
			}

			Matrix4f matrix4f = p_228444_1_.last().pose();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
			bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
			bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
			bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
			tessellator.end();
			p_228444_1_.popPose();
		}

		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}

	@Override @SuppressWarnings({ "deprecation", "unused" })
	public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc)
	{
		if (minecraft.level.effects().skyType() == DimensionRenderInfo.FogType.END)
		{
			this.renderEndSky(matrixStack);
		}
		else if (minecraft.level.effects().skyType() == DimensionRenderInfo.FogType.NORMAL)
			{
				RenderSystem.disableTexture();
				Vector3d vector3d = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getBlockPosition(), partialTicks);
				float f = (float) vector3d.x;
				float f1 = (float) vector3d.y;
				float f2 = (float) vector3d.z;
				FogRenderer.levelFogColor();
				BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
				RenderSystem.depthMask(false);
				RenderSystem.enableFog();
				RenderSystem.color3f(f, f1, f2);
				this.skyBuffer.bind();
				this.skyFormat.setupBufferState(0L);
				this.skyBuffer.draw(matrixStack.last().pose(), 7);
				VertexBuffer.unbind();
				this.skyFormat.clearBufferState();
				RenderSystem.disableFog();
				RenderSystem.disableAlphaTest();
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				float[] afloat = this.level.effects().getSunriseColor(this.level.getTimeOfDay(partialTicks), partialTicks);
				if (afloat != null)
				{
					RenderSystem.disableTexture();
					RenderSystem.shadeModel(7425);
					matrixStack.pushPose();
					matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
					float f3 = MathHelper.sin(this.level.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
					matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3));
					matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
					float f4 = afloat[0];
					float f5 = afloat[1];
					float f6 = afloat[2];
					Matrix4f matrix4f = matrixStack.last().pose();
					bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
					bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
					int i = 16;
					for (int j = 0; j <= 16; ++j)
					{
						float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
						float f8 = MathHelper.sin(f7);
						float f9 = MathHelper.cos(f7);
						bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
					}
					bufferbuilder.end();
					WorldVertexBufferUploader.end(bufferbuilder);
					matrixStack.popPose();
					RenderSystem.shadeModel(7424);
				}
				RenderSystem.enableTexture();
				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				matrixStack.pushPose();
				float f11 = 1.0F - this.level.getRainLevel(partialTicks);
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
				matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(partialTicks) * 360.0F));
				Matrix4f matrix4f1 = matrixStack.last().pose();
				float f12 = 30.0F;
				this.textureManager.bind(SUN_LOCATION);
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
				bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
				bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
				bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
				bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
				bufferbuilder.end();
				WorldVertexBufferUploader.end(bufferbuilder);
				f12 = 20.0F;

				if (BloodMoonEvent.isBloodMoonActive())
					this.textureManager.bind(BLOOD_MOON_LOCATION);
				else
					this.textureManager.bind(MOON_LOCATION);

				int k = this.level.getMoonPhase();
				int l = k % 4;
				int i1 = k / 4 % 2;
				float f13 = (float) (l + 0) / 4.0F;
				float f14 = (float) (i1 + 0) / 2.0F;
				float f15 = (float) (l + 1) / 4.0F;
				float f16 = (float) (i1 + 1) / 2.0F;
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
				bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
				bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
				bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
				bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
				bufferbuilder.end();
				WorldVertexBufferUploader.end(bufferbuilder);
				RenderSystem.disableTexture();
				float f10 = this.level.getStarBrightness(partialTicks) * f11;
				if (f10 > 0.0F)
				{
					RenderSystem.color4f(f10, f10, f10, f10);
					this.starBuffer.bind();
					this.skyFormat.setupBufferState(0L);
					this.starBuffer.draw(matrixStack.last().pose(), 7);
					VertexBuffer.unbind();
					this.skyFormat.clearBufferState();
				}
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.disableBlend();
				RenderSystem.enableAlphaTest();
				RenderSystem.enableFog();
				matrixStack.popPose();
				RenderSystem.disableTexture();
				RenderSystem.color3f(0.0F, 0.0F, 0.0F);
				double d0 = minecraft.player.getEyePosition(partialTicks).y - this.level.getLevelData().getHorizonHeight();
				if (d0 < 0.0D)
				{
					matrixStack.pushPose();
					matrixStack.translate(0.0D, 12.0D, 0.0D);
					this.darkBuffer.bind();
					this.skyFormat.setupBufferState(0L);
					this.darkBuffer.draw(matrixStack.last().pose(), 7);
					VertexBuffer.unbind();
					this.skyFormat.clearBufferState();
					matrixStack.popPose();
				}
				if (this.level.effects().hasGround())
				{
					RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
				} else
				{
					RenderSystem.color3f(f, f1, f2);
				}
				RenderSystem.enableTexture();
				RenderSystem.depthMask(true);
				RenderSystem.disableFog();

				RenderHelper.setupNetherLevel(matrixStack.last().pose());
			}
	}

}
