package com.dmtrllv.nova;

import java.util.HashMap;
import java.util.function.Supplier;

import javax.annotation.Nullable;

public class Registry<T>
{
	public final class Object
	{
		@Nullable
		private T value = null;

		public final String id;

		private Supplier<T> supplier;

		public T get()
		{
			return value;
		}

		public Object(String id, Supplier<T> supplier)
		{
			this.id = id;
			this.supplier = supplier;
		}
	}

	private boolean didRegister = false;
	private final HashMap<String, Object> objects = new HashMap<String, Object>();

	public Object register(String id, Supplier<T> supplier)
	{
		Object o = new Object(id, supplier);
		objects.put(id, o);
		return o;
	}

	public void register()
	{
		if (!didRegister)
		{
			this.objects.forEach((k, o) ->
			{
				o.value = o.supplier.get();
			});
			didRegister = true;
		}
	}

	public T get(String id)
	{
		Object o = this.objects.get(id);
		if (o == null)
			return null;
		return o.get();
	}
}
