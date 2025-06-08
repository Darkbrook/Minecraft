package com.darkbrook.island.gameplay.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

import com.darkbrook.library.gameplay.gui.GuiListener;
import com.darkbrook.library.gameplay.gui.data.GuiProperty;

public class GuiChatColor extends GuiListener
{

	public GuiChatColor() 
	{
		super("chatcolor");
		
		load(null);
		register();
	}

	@Override
	protected void onItemClick(InventoryClickEvent event) 
	{
		playSound(event.getWhoClicked(), "pick");
	}

	@Override
	protected void onContentLoad() 
	{		
		
		int index = 0;
		
		for(GuiProperty property : mapping.getPropArray())
		{
			/*if(index == 0)
			{
				addItem(property.apply(mapping.getItem("dye")));
			}
			else
			{
				setItem("locked", index);
			}*/
			addItem(property.apply(mapping.getItem("dye")));

			index++;
		}
		
	}

}
