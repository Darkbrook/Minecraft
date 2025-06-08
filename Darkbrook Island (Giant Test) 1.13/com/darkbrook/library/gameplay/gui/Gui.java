package com.darkbrook.library.gameplay.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import com.darkbrook.island.gameplay.visual.VisualRepository;
import com.darkbrook.library.gameplay.gui.data.GuiDataMapping;
import com.darkbrook.library.gameplay.gui.data.GuiItem;
import com.darkbrook.library.gameplay.gui.data.GuiSettings;
import com.darkbrook.library.gameplay.gui.inventory.InventoryWrapper;

public abstract class Gui extends InventoryWrapper
{

	protected GuiDataMapping mapping;
	protected GuiSettings data;
		
	public Gui(String identity)
	{
		super(VisualRepository.soundOpenClose, null);
		mapping = new GuiDataMapping(identity);
	}
	
	public void load(String keys, Object... values)
	{
		data = mapping.getSettingsArray().get(0);
		data.addMapping(keys, values);
		
		setInventory(data.getParsedValue());
		reload();
	}
	
	public void playSound(HumanEntity entity, String sound)
	{
		mapping.getSound(sound).getParsedValue().play((Player) entity, true);
	}
	
	public void playSound(HumanEntity entity)
	{
		VisualRepository.soundClick.play((Player) entity, true);
	}
	
	public void setItem(GuiItem item, int index)
	{
		setItem(item.getParsedValue(), index);
	}
	
	public void setItem(String item, int index)
	{
		setItem(mapping.getItem(item), index);
	}
	
	public void addItem(GuiItem item)
	{
		addItem(item.getParsedValue());
	}
	
	public void addItem(String item)
	{
		addItem(mapping.getItem(item));
	}
	
}
