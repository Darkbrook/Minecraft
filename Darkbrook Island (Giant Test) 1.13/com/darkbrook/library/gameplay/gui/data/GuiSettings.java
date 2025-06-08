package com.darkbrook.library.gameplay.gui.data;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.darkbrook.library.data.object.ObjectDataParsed;
import com.darkbrook.library.data.object.ObjectDataQueue;

public class GuiSettings extends ObjectDataParsed<Inventory>
{
	
	private boolean isDraggingEnabled;
	private boolean isClickingEnabled;
	
	public GuiSettings(String identity) 
	{
		super(identity);
	}
	
	public boolean isDraggingEnabled()
	{
		return isDraggingEnabled;
	}
	
	public boolean isClickingEnabled()
	{
		return isClickingEnabled;
	}

	@Override
	protected Inventory onParsedValueLoad() 
	{
		ObjectDataQueue data = getData("drag/pick/size/name", false, false, 1, "Custom Inventory");
		
		isDraggingEnabled = data.b();
		isClickingEnabled = data.b();
		
		return Bukkit.createInventory(null, data.i() * 9, data.s());
	}
	
}
