package com.darkbrook.library.gameplay.gui.data;

import java.util.List;

import com.darkbrook.library.data.object.ObjectDataMapping;
import com.darkbrook.library.util.ResourceLocation;

public class GuiDataMapping extends ObjectDataMapping
{
	
	public GuiDataMapping(String identity) 
	{
		super("gui/sound/item/prop", GuiSettings.class, GuiSound.class, GuiItem.class, GuiProperty.class);
		load(new ResourceLocation("$data/gui/" + identity + "-gui.txt"));
	}
	
	public List<GuiSettings> getSettingsArray()
	{
		return getDataArray(GuiSettings.class);
	}
	
	public GuiSettings getSetting(String key)
	{
		return getData(GuiSettings.class, key);
	}
	
	public List<GuiSound> getSoundArray()
	{
		return getDataArray(GuiSound.class);
	}
	
	public GuiSound getSound(String key)
	{
		return getData(GuiSound.class, key);
	}
	
	public List<GuiItem> getItemArray()
	{
		return getDataArray(GuiItem.class);
	}

	public GuiItem getItem(String key)
	{
		return getData(GuiItem.class, key);
	}
	
	public List<GuiProperty> getPropArray()
	{
		return getDataArray(GuiProperty.class);
	}
	
	public GuiProperty getProp(String key)
	{
		return getData(GuiProperty.class, key);
	}

}
