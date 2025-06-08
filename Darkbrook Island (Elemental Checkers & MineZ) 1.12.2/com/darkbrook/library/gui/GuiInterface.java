package com.darkbrook.library.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiInterface {
	
	public void onInventoryClick(InventoryClickEvent event, Player player, ClickType clickType, ItemStack itemClicked, ItemStack itemCursor, int slot, boolean isTopInventory);
	
	public void setGui(Gui gui);
	public void setTitle(String title);
	
	public Gui getGui();
	public String getTitle();

}
