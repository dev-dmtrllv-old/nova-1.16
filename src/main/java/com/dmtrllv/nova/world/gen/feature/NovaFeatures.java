package com.dmtrllv.nova.world.gen.feature;

import com.dmtrllv.nova.Nova;

import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class NovaFeatures
{
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, Nova.MOD_ID);

	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> PEBBLE_PATCH = REGISTRY.register("pebble_patch", () -> new PebblePatchFeature(BlockClusterFeatureConfig.CODEC));
}
