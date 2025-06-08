package com.darkbrook.island.brewery.registry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.common.registry.IRegistryValue;

public interface IFermentable extends IRegistryValue
{
	public ItemStack generateProduct(ItemStack stack);
	
	public ItemStack generateLiquid();
	
	public ItemStack generateBeverage();
	
	public Material getSource();
	
	public boolean isFermentableLiquid(ItemStack stack);
}
