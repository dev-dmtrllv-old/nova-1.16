package com.dmtrllv.nova;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;


public class Registry<T>
{
	private boolean didRegister = false;
	private final HashMap<String, RegistryObject<T>> objects = new HashMap<String, RegistryObject<T>>();

	@Nullable
	private BiConsumer<String, T> onRegistered = (id, o) -> {};

	public Registry() {}

	public Registry(BiConsumer<String, T> onRegistered) 
	{
		this.onRegistered = onRegistered;
	}

	@SuppressWarnings("unchecked")
	public <S extends T> RegistryObject<S> register(String id, Supplier<S> supplier)
	{
		RegistryObject<S> o = new RegistryObject<S>(id, supplier);
		objects.put(id, (RegistryObject<T>)o);
		return o;
	}

	public void forEach(BiConsumer<String, RegistryObject<T>> cb)
	{
		objects.forEach(cb);
	}

	public void register()
	{
		if (!didRegister)
		{
			this.objects.forEach((k, o) -> 
			{
				if(!o.isRegistered())
				{
					T obj = o.register();
					onRegistered.accept(k, obj);
				}
			});
			didRegister = true;
		}
	}

	public T get(String id)
	{
		RegistryObject<T> o = this.objects.get(id);
		if (o == null)
			return null;
		return o.get();
	}
}
