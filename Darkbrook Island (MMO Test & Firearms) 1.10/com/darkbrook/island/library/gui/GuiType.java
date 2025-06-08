package com.darkbrook.island.library.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GuiType implements Listener {

	protected Gui gui;
	protected InventoryClickEvent event;
	
	protected boolean isGui(Inventory inventory) {
		return inventory.getName().equals(gui.getName());
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		this.event = event;
		if(event.getClickedInventory() == null) return;
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		ItemStack item = event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR ? null : event.getCurrentItem().clone();
		ItemStack cursor = event.getCursor() == null || event.getCursor().getType() == Material.AIR ? null : event.getCursor().clone();
		ClickType click = event.getClick();
		if(isGui(event.getClickedInventory()) ? clickGuiEvent(event.getClickedInventory(), player, slot, item, cursor, click) : (isGui(event.getInventory()) ? clickInventoryEvent(event.getClickedInventory(), player, slot, item, cursor, click) : false)) event.setCancelled(true); 
	}
	
	protected void setItem(ItemStack stack) {
		event.setCurrentItem(stack);
	}
	
	@SuppressWarnings("deprecation")
	protected void setCursor(ItemStack stack) {
		event.setCursor(stack);
	}
	
	protected abstract boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click);
	protected abstract boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click);
	
}
