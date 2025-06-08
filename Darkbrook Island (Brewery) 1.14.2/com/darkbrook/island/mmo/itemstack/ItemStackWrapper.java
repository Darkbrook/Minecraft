package com.darkbrook.island.mmo.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackWrapper 
{
	
	protected ItemStack stack;
	
	public ItemStackWrapper(Material material, int amount)
	{
		stack = new ItemStack(material, amount);
	}
	
	public ItemStackWrapper(Material material)
	{
		this(material, 1);
	}

	public short getDurabilityMax()
	{
		return stack.getType().getMaxDurability();
	}
	
	@SuppressWarnings("deprecation")
	public short getDurability()
	{
		return stack.getDurability();
	}
	
	@SuppressWarnings("deprecation")
	public void setDurability(short durability)
	{
		stack.setDurability(durability);
	}
	
	public ItemStack getStack()
	{
		return stack;
	}

}
