package com.dmtrllv.nova.item;

import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

public final class NovaItemGroup extends ItemGroup
{
	public static final ItemGroup NOVA_MOD_ITEMS = new NovaItemGroup("nova_mod_items", () -> new ItemStack(NovaItems.PEBBLE.get()));

	private final Supplier<ItemStack> iconSupplier;

	protected NovaItemGroup(final String name, final Supplier<ItemStack> iconSupplier)
	{
		super(name);
		this.iconSupplier = iconSupplier;
	}

	protected NovaItemGroup(final String name, final RegistryObject<Item> fromItem)
	{
		super(name);
		this.iconSupplier = () -> new ItemStack(fromItem.get());
	}

	@Override
	public ItemStack makeIcon()
	{
		return iconSupplier.get();
	}
}
