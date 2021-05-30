package com.dmtrllv.nova.item;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

public enum NovaItemTier implements IItemTier
{
	PEBBLE(1, 32, 3.0F, 0.0F, 10, () ->
	{
		return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
	});

	private final int level;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final LazyValue<Ingredient> repairIngredient;

	private NovaItemTier(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredients)
	{
		this.level = level;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = new LazyValue<>(repairIngredients);
	}

	public int getUses()
	{
		return this.uses;
	}

	public float getSpeed()
	{
		return this.speed;
	}

	public float getAttackDamageBonus()
	{
		return this.damage;
	}

	public int getLevel()
	{
		return this.level;
	}

	public int getEnchantmentValue()
	{
		return this.enchantmentValue;
	}

	public Ingredient getRepairIngredient()
	{
		return this.repairIngredient.get();
	}
}
