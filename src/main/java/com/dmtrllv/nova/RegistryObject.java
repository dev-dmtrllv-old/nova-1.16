package com.dmtrllv.nova;

import java.util.function.Supplier;

import javax.annotation.Nullable;

public class RegistryObject<T>
{
	@Nullable
	private T value = null;

	private boolean isLoaded = false;

	public boolean isRegistered()
	{
		return isLoaded;
	}

	public T register()
	{
		if(value == null)
			value = supplier.get();
		isLoaded = true;
		return value;
	}

	public final String id;

	public final Supplier<T> supplier;

	public T get()
	{
		if (value == null)
			value = supplier.get();
		return value;
	}

	public RegistryObject(String id, Supplier<T> supplier)
	{
		this.id = id;
		this.supplier = supplier;
	}
}
