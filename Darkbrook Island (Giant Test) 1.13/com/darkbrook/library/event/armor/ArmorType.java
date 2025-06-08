package com.darkbrook.library.event.armor;

import org.bukkit.inventory.ItemStack;

public enum ArmorType
{
	
	HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	
	public static ArmorType getArmorType(ItemStack stack)
	{
		
		try
		{
			return valueOf(stack.getType().name().split("_")[1].toUpperCase());
		}
		catch (Exception e)
		{
			return null;
		}
		
	}
	
}
