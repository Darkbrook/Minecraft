package com.darkbrook.library.gameplay.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.darkbrook.library.gameplay.gui.inventory.CustomInventory;
import com.darkbrook.library.util.runnable.RunnableState;
import com.darkbrook.library.util.runnable.SingleRunnable;

public abstract class GuiListener extends Gui implements IGuiListener
{
	
	private SingleRunnable reload;
	private SingleRunnable reloadMapped;

	public GuiListener(String identity) 
	{
		super(identity);
		
		reload = new SingleRunnable(() -> reload(), RunnableState.ASYNC, 1);
		reloadMapped = new SingleRunnable(() -> reloadMapped(), RunnableState.ASYNC, 1);
	}

	@Override
	public void register() 
	{
		IGuiListener.register(this);
	}

	@Override
	public void unregister() 
	{
		IGuiListener.unregister(this);
	}
	
	public void delayedReload()
	{
		reload.execute();
	}
	
	public void delayedReloadMapped()
	{
		reloadMapped.execute();
	}
	
	public void reloadMapped()
	{
		IGuiListener.reload(this);
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) 
	{

		if(!data.isDraggingEnabled())
		{
			event.setCancelled(true);
		}
		
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) 
	{

		if(!data.isClickingEnabled())
		{
			event.setCancelled(true);
		}
		
		if(CustomInventory.compare(event.getClickedInventory(), inventory) && event.getCurrentItem().getType() != Material.AIR)
		{
			onItemClick(event);			
		}
		
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) 
	{
		
		if(CustomInventory.compare(event.getInventory(), super.inventory))
		{
			sound((Player) event.getPlayer());
			unregister();
		}
		
	}
	
	protected abstract void onItemClick(InventoryClickEvent event);

}
