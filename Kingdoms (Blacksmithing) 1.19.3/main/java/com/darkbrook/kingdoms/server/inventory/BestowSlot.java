package com.darkbrook.kingdoms.server.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BestowSlot extends Slot
{
	public BestowSlot(Inventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}
		
	@Override
    public ItemStack takeStack(int amount)
	{
		return super.getStack().copyWithCount(amount);
    }

	@Override
    public ItemStack getStack() 
    {
        return super.getStack().copy();
    }
	
	@Override
    public void setStack(ItemStack stack) 
	{
		super.setStack(getStack());
	}
	
	@Override
	public ItemStack insertStack(ItemStack stack, int count)
	{
		return ItemStack.EMPTY;
	}
}
