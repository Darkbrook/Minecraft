package com.darkbrook.library.gameplay.table.event.cancelable;

import org.bukkit.entity.Player;

import com.darkbrook.library.gameplay.table.table.Table;

public class TableBreakEvent extends CancelableTableEvent
{
	
	private Player player;
	private TableBreakReason reason;
	
	public TableBreakEvent(Player player, Table table, TableBreakReason reason)
	{
		super(table);
		this.player = player;
		this.reason = reason;
	}

	public Player getPlayer()
	{
		return player;
	}

	public TableBreakReason getReason()
	{
		return reason;
	}

}
