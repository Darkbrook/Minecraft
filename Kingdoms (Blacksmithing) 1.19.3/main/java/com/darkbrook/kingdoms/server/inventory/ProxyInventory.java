package com.darkbrook.kingdoms.server.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ProxyInventory implements Inventory
{
	private Inventory inventory;
	
	public ProxyInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}

	public Inventory getInventory()
	{
		return inventory;
	}

	public void setInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}

	@Override
	public ItemStack removeStack(int slot, int amount)
	{
		return inventory.removeStack(slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot)
	{
		return inventory.removeStack(slot);
	}
	
	@Override
	public ItemStack getStack(int slot)
	{
		return inventory.getStack(slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack)
	{
		inventory.setStack(slot, stack);		
	}
	
	@Override
	public void clear()
	{		
		inventory.clear();
	}
	
	@Override
	public void markDirty()
	{
		inventory.markDirty();		
	}
	
	@Override
	public int size()
	{
		return inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		return inventory.isEmpty();
	}
	
	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return inventory.canPlayerUse(player);
	}
}
