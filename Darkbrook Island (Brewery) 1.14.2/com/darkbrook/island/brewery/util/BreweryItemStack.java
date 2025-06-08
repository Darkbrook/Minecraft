package com.darkbrook.island.brewery.util;

import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.common.gameplay.nms.NmsItemStack;

public class BreweryItemStack extends NmsItemStack
{

	public BreweryItemStack(ItemStack stack) 
	{
		super(stack);
	}
	
	public ItemStack getFermentedItemStack(int fermentationSeconds)
	{
		int status = tag.hasKey("FermentationStatus") ? tag.getInt("FermentationStatus") - 1 : fermentationSeconds;
		
		if(status > 0)
		{
			tag.setInt("FermentationStatus", status);
			return getBukkitItemStack();
		}
		
		return null;
	}
	
	public ItemStack getLiquidItemStack(String beverageLiquidKey)
	{
		tag.setBoolean(beverageLiquidKey, true);
		return getBukkitItemStack();
	}
	
	public ItemStack getBeverageItemStack(String beverageName, float beverageAbv)
	{
		tag.setString("BeverageName", beverageName);
		tag.setFloat("AlcoholByVolume", beverageAbv);
		return getBukkitItemStack();
	}
	
	public String getBeverageName()
	{
		return tag.getString("BeverageName");
	}
	
	public float getAlcoholByVolume()
	{
		return tag.getFloat("AlcoholByVolume");
	}
	
	public boolean hasAlcohol()
	{
		return tag.hasKey("AlcoholByVolume");
	}
	
	public boolean isLiquid(String beverageLiquidKey)
	{
		return tag.hasKey(beverageLiquidKey);
	}

}
