package com.darkbrook.prospector.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSluiceBoxOutput extends Slot
{
	public SlotSluiceBoxOutput(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
