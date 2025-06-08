package com.darkbrook.library.gameplay.table.event.cancelable;

import com.darkbrook.library.event.CancelableEvent;
import com.darkbrook.library.gameplay.table.table.Table;

public class CancelableTableEvent extends CancelableEvent
{

	private Table table;

	public CancelableTableEvent(Table table)
	{
		this.table = table;
	}

	public Table getTable()
	{
		return table;
	}
	
}
