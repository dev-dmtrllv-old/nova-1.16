package com.dmtrllv.nova.util;

import java.util.HashMap;
import java.util.function.Supplier;

import com.dmtrllv.nova.Nova;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class NovaRegistry<T>
{
	private final HashMap<String, NovaRegistryObject<T>> objects = new HashMap<String, NovaRegistryObject<T>>();

	private boolean isRegistered = false;

	private Registry<T> registry;

	public NovaRegistry(Registry<T> registry)
	{
		this.registry = registry;
	}

	public void register()
	{
		if (!isRegistered)
		{
			objects.forEach((key, o) ->
			{
				ResourceLocation resID = new ResourceLocation(Nova.MOD_ID, key);
				if (registry.keySet().contains(resID))
					throw new IllegalStateException("Configured Feature ID: \"" + resID.toString() + "\" already exists in the Configured Features registry!");

				net.minecraft.util.registry.Registry.register(registry, resID, o.get());
			});
			isRegistered = true;
		}
	}

	@SuppressWarnings("unchecked")
	public <S extends T> NovaRegistryObject<S> register(String id, Supplier<S> sup)
	{
		NovaRegistryObject<S> o = new NovaRegistryObject<S>(id, sup);
		objects.put(id, (NovaRegistryObject<T>)o);
		return o;
	}
}
