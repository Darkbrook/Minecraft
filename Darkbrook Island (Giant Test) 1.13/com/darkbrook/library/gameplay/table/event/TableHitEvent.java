package com.darkbrook.library.gameplay.table.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.gameplay.table.table.Table;

public class TableHitEvent extends TableEvent
{
	
	private ItemStack handStack;

	public TableHitEvent(Table table, Player player)
	{
		super(table);
		this.handStack = player.getInventory().getItemInMainHand();
	}

	public ItemStack getHandStack()
	{
		return handStack;
	}

}
