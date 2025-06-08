package com.darkbrook.prospector.inventory;

import com.darkbrook.prospector.item.crafting.SluicingRecipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSluiceBoxInput extends Slot
{
	public SlotSluiceBoxInput(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return SluicingRecipes.getInstance().getRecipe(stack) != null;
	}
}
