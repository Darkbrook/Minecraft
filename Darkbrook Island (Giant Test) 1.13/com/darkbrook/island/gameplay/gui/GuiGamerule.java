package com.darkbrook.island.gameplay.gui;

import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.darkbrook.library.gameplay.gui.GuiListener;
import com.darkbrook.library.gameplay.gui.data.GuiItem;
import com.darkbrook.library.gameplay.gui.data.GuiProperty;
import com.darkbrook.library.gameplay.gui.inventory.CustomInventory;

public class GuiGamerule extends GuiListener
{

	private World world;
	
	public GuiGamerule(World world) 
	{
		super("gamerule");
		this.world = world;
		
		load(null);
		register();
	}

	@Override
	protected void onItemClick(InventoryClickEvent event) 
	{	
		String gamerule = CustomInventory.getValueFromLore(event, 0);		
		String value = String.valueOf(event.getCurrentItem().getType() != mapping.getItem("enabled").getParsedValue().getType());

		GuiProperty property = mapping.getProp(gamerule);
		String tempValue = property.getDataValue(value);
		
		if(tempValue != null)
		{
			value = tempValue;
		}
		
		world.setGameRuleValue(gamerule, value);
		playSound(event.getWhoClicked(), "pick");
		delayedReloadMapped();
	}

	@Override
	protected void onContentLoad() 
	{
		
		for(GuiProperty property : mapping.getPropArray())
		{
			String value = world.getGameRuleValue(property.getVariableName());
			GuiItem item = mapping.getItem(value.equals("false") || value.equals("0") ? "disabled" : "enabled");
			addItem(property.apply(item));
		}
		
	}

}
