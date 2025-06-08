package com.darkbrook.prospector.inventory;

import com.darkbrook.prospector.item.crafting.SluicingRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSluiceBox extends Container
{
	private final IInventory inventory;
	private int sluiceTime;
	private int sluiceTimeTotal;
	
	public ContainerSluiceBox(IInventory inventory, InventoryPlayer playerInventory) 
	{
		this.inventory = inventory;
		addSlotToContainer(new SlotSluiceBoxInput(inventory, 0, 36, 20));
		
		for (int i = 0; i < 3; i++)
			addSlotToContainer(new SlotSluiceBoxOutput(inventory, i + 1, (i * 18) + 90, 20));
		
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
					addSlotToContainer(new Slot(playerInventory, (y * 9) + x + 9, (x * 18) + 8, (y * 18) + 51));
		
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(playerInventory, i, (i * 18) + 8, 109));
	}
	
	@Override
	public void onCraftGuiOpened(ICrafting listener)
	{
		super.onCraftGuiOpened(listener);
		listener.sendAllWindowProperties(this, inventory);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (ICrafting listener : crafters)
		{
			if (sluiceTime != inventory.getField(0))
				listener.sendProgressBarUpdate(this, 0, inventory.getField(0));
			
			if (sluiceTimeTotal != inventory.getField(1))
				listener.sendProgressBarUpdate(this, 1, inventory.getField(1));
		}
		
		sluiceTime = inventory.getField(0);
		sluiceTimeTotal = inventory.getField(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value)
	{
		inventory.setField(id, value);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return inventory.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		Slot slot = getSlot(index);
		
		if (slot == null || !slot.getHasStack())
			return null;
		
		ItemStack original = slot.getStack();
		ItemStack copy = original.copy();
		
		if (index == 1 || index == 2 || index == 3)
		{
			//output -> inventory/hotbar
			if (!mergeItemStack(original, 4, 40, true))
				return null;
			
			slot.onSlotChange(original, copy);
		}
		else if (index != 0)
		{			
			if (SluicingRecipes.getInstance().getRecipe(original) != null)
			{
				//applicable items : inventory/hotbar -> input
				if (!mergeItemStack(original, 0, 1, false))
					return null;
			}
			else if (index >= 4 && index < 31)
			{
				//inventory -> hotbar
				if (!mergeItemStack(original, 31, 40, false))
					return null;
			}
			//hotbar -> inventory
			else if (index >= 31 && index < 40 && !mergeItemStack(original, 4, 31, false))
				return null;
		}
		//input -> inventory/hotbar
		else if (!mergeItemStack(original, 4, 40, false))
			return null;
		
		if (original.stackSize == 0)
			slot.putStack(null);
		else
			slot.onSlotChanged();
		
		if (original.stackSize == copy.stackSize)
			return null;
		
		slot.onPickupFromSlot(player, original);
		return copy;
	}
}
