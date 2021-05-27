package com.dmtrllv.nova;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.dmtrllv.nova.block.NovaBlocks;
import com.dmtrllv.nova.block.NovaWoodType;
import com.dmtrllv.nova.item.NovaItems;
import com.dmtrllv.nova.renderer.NovaAtlases;
import com.dmtrllv.nova.tileentity.NovaTileEntityType;
import com.dmtrllv.nova.world.gen.feature.NovaFeatures;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Nova.MOD_ID)
public class Nova
{
	public static final String MOD_ID = "nova";

	public static final Logger LOGGER = LogManager.getLogger();

	public Nova()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		NovaBlocks.REGISTRY.register(bus);
		NovaItems.REGISTRY.register(bus);
		NovaTileEntityType.REGISTRY.register(bus);

		bus.addListener(this::onCommonSetup);
		bus.addListener(this::onClientSetup);
		bus.addListener(this::onModelRegistryEvent);
	}

	private void initColors(ItemColors itemColors, BlockColors blockColors)
	{
		itemColors.register((a, b) ->
		{
			BlockState blockstate = ((BlockItem) a.getItem()).getBlock().defaultBlockState();
			return blockColors.getColor(blockstate, null, null, b);
		}, NovaBlocks.WHITE_OAK_LEAVES.get());
	}

	private void initColors(BlockColors BlockColors)
	{
		BlockColors.register((a, b, c, d) ->
		{
			return b != null && c != null ? BiomeColors.getAverageFoliageColor(b, c) : FoliageColors.getDefaultColor();
		}, NovaBlocks.WHITE_OAK_LEAVES.get());
	}

	@SuppressWarnings("deprecation")
	private void onCommonSetup(final FMLCommonSetupEvent event)
	{
		DeferredWorkQueue.runLater(() ->
		{
			Minecraft m = Minecraft.getInstance();
			this.initColors(m.getBlockColors());
			this.initColors(m.getItemColors(), m.getBlockColors());
		});
		NovaFeatures.register();
	}

	@SuppressWarnings("deprecation")
	private void onClientSetup(final FMLClientSetupEvent event)
	{
		DeferredWorkQueue.runLater(() ->
		{
			LogManager.getLogger().info("register nova");
			NovaWoodType.registerWoodTypes();
			NovaBlocks.setCutOutRenderers();
			NovaBlocks.setSolidRenderers();
		});

		ClientRegistry.bindTileEntityRenderer(NovaTileEntityType.SIGN.get(), SignTileEntityRenderer::new);

	}

	private void onModelRegistryEvent(final ModelRegistryEvent event)
	{
		FakeBakery.getBuiltinTextures().add(NovaAtlases.signTexture(NovaWoodType.WHITE_OAK));
	}
}
