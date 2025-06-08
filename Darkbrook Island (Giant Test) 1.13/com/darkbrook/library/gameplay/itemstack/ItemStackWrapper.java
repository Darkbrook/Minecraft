package com.darkbrook.library.gameplay.itemstack;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
	
public abstract class ItemStackWrapper 
{
	
	protected ItemStack stack;
	private String baseName;
	private String typeName;
	
	public ItemStackWrapper(ItemStack stack)
	{
		this.stack = stack;
		
		baseName = CraftItemStack.asNMSCopy(new ItemStack(stack.getType())).getName().getText();
		typeName = stack.getType().toString().toLowerCase().replace("_", "");
	}
	
	public ItemStackWrapper(String typeName, int amount, int durability)
	{
		this(parseItemStack(typeName, amount, durability));
	}
	
	public ItemStack getItemStack()
	{
		return stack;
	}
	
	public String getBaseName()
	{
		return baseName;
	}
	
	public String getTypeName()
	{
		return typeName;
	}

	public Material getType() 
	{
		return stack.getType();
	}
	
	public int getAmount() 
	{
		return stack.getAmount();
	}
	
	public int getDurability() 
	{
		return stack.getDurability();
	}
	
	private static ItemStack parseItemStack(String typeName, int amount, int durability)
	{
		
		try
		{
			return new ItemStack(Material.getMaterial(typeName.toUpperCase().replace(' ', '_')), amount, (short) durability);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Invalid material type \"" + typeName + "\"");
		}
		
	}
	
}
