package com.dmtrllv.nova.item;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class NovaArmorItem extends ArmorItem {

	private String texture;

	public NovaArmorItem(String texture, IArmorMaterial material, EquipmentSlotType equipSlotType, Properties properties)
	{
		super(material, equipSlotType, properties);
		this.texture = texture;
	}
	
	@Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        return texture;
    }
}
