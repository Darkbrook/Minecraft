package com.darkbrook.library.gameplay.itemstack;

import org.bukkit.inventory.ItemStack;


public class DarkbrookItemStack extends ItemStackNbtWrapper
{

	public DarkbrookItemStack(ItemStack stack) 
	{
		super(stack);
	}

	public DarkbrookItemStack(String typeName, int amount, int durability)
	{
		super(typeName, amount, durability);
	}
	
	@Override
	public DarkbrookItemStack openMeta()
	{
		return (DarkbrookItemStack) super.openMeta();
	}
	
	@Override
	public DarkbrookItemStack openNbt()
	{
		return (DarkbrookItemStack) super.openNbt();
	}
	
	public ItemStack subtract(int amount)
	{
		if(equals(amount))
		{
			stack = null;
		}
		else
		{
			stack.setAmount(stack.getAmount() - amount);
		}
		
		return stack;
	}
	
	public boolean isSubtractable(int amount)
	{
		return stack.getAmount() >= amount;
	}
	
	public boolean equals(int amount)
	{
		return stack.getAmount() == amount;
	}
	
}
