package com.darkbrook.library.gameplay.gui.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.gameplay.visual.DarkbrookSound;

public abstract class InventoryWrapper 
{

	private DarkbrookSound sound;
	protected Inventory inventory;
	
	public InventoryWrapper(DarkbrookSound sound, Inventory inventory)
	{
		this.sound = sound;
		this.inventory = inventory;
	}
	
	public void reload()
	{
		clear();
		onContentLoad();
		update();
	}
	
	public void open(Player player) 
	{
		player.openInventory(inventory);
		sound(player);
	}
	
	public void close(Player player) 
	{
		player.closeInventory();
		sound.play(player, true);
	}
	
	public void sound(Player player)
	{
		sound.play(player, true);
	}
	
	public void setItem(ItemStack stack, int index)
	{
		inventory.setItem(index, stack);
	}
	
	public void addItem(ItemStack stack)
	{
		inventory.addItem(stack);
	}
	
	public void clear()
	{
		inventory.clear();
	}
	
	public void update()
	{
		
		for(HumanEntity viewer : inventory.getViewers())
		{
			((Player) viewer).updateInventory();
		}
		
	}
	
	protected void setInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}
	
	protected abstract void onContentLoad();
	
}
