package com.darkbrook.library.gameplay.table.event;

import com.darkbrook.library.event.DarkbrookEvent;
import com.darkbrook.library.gameplay.table.table.Table;

public class TableEvent extends DarkbrookEvent
{
	
	private Table table;

	public TableEvent(Table table)
	{
		this.table = table;
	}

	public Table getTable()
	{
		return table;
	}
	
}
