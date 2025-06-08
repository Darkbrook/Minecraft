package com.darkbrook.library.gameplay.table.event.cancelable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.event.CancelableEvent;

public class TableCreateEvent extends CancelableEvent
{
	
	private Player player;
	private Block block;
	private ItemStack stack;
	private float offsetY;
	
	public TableCreateEvent(Player player, Block block, ItemStack stack)
	{
		this.player = player;
		this.block = block;
		this.stack = stack;
	}

	public Player getPlayer()
	{
		return player;
	}
	
	public ItemStack getItemStack()
	{
		return stack;
	}
	
	public void setItemStack(ItemStack stack)
	{
		this.stack = stack;
	}
	
	public Block getBlock()
	{
		return block;
	}

	public float getOffsetY()
	{
		return offsetY;
	}

	public void setOffsetY(float offsetY)
	{
		this.offsetY = offsetY;
	}

}
