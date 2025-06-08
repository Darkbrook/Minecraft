package com.darkbrook.island.gameplay.gui;

import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;

import com.darkbrook.library.gameplay.gui.GuiListener;
import com.darkbrook.library.gameplay.gui.data.GuiItem;
import com.darkbrook.library.gameplay.gui.data.GuiProperty;

public class GuiDefault extends GuiListener
{

	public GuiDefault(String identity) 
	{
		super(identity);
		
		load(null);
		register();
	}

	@Override
	protected void onItemClick(InventoryClickEvent event)
	{
		event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
		playSound(event.getWhoClicked());
	}

	@Override
	protected void onContentLoad() 
	{

		 for(GuiItem item : mapping.getItemArray()) 
		 { 
			 
			 try
			 {
				 addItem(item);				 
			 }
			 catch(Exception e)
			 {
			 }
			 
		 }
		
		 List<GuiProperty> propertyArray = mapping.getPropArray();

		 if(!propertyArray.isEmpty()) for(GuiProperty property : propertyArray) for(GuiItem item : mapping.getItemArray())
		 {
			 addItem(property.apply(item));
	     }
		 
	}

}
