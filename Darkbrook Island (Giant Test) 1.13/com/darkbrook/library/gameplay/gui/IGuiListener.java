package com.darkbrook.library.gameplay.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.darkbrook.island.DarkbrookIsland;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.helper.HashMapHelper;

public interface IGuiListener extends Listener, IRegistryValue
{

	public static final Map<Class<? extends IGuiListener>, List<IGuiListener>> listenerMapping = new HashMap<Class<? extends IGuiListener>, List<IGuiListener>>();
	
	public void register();
	public void unregister();
	public void reload();
	
	public void onInventoryDrag(InventoryDragEvent event);
	public void onInventoryClick(InventoryClickEvent event);
	public void onInventoryClose(InventoryCloseEvent event);
	
	public static void register(IGuiListener listener)
	{
		DarkbrookIsland.getPlugin().register(listener);
		HashMapHelper.add(listenerMapping, listener.getClass(), listener);
	}
	
	public static void unregister(IGuiListener listener)
	{
		listenerMapping.get(listener.getClass()).remove(listener);
		DarkbrookIsland.getPlugin().unregister(listener);
	}
	
	public static void reload(IGuiListener listener)
	{
		for(IGuiListener gui : listenerMapping.get(listener.getClass())) gui.reload();
	}
	
}
