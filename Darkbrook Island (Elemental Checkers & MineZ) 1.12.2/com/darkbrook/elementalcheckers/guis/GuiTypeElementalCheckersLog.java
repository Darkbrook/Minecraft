package com.darkbrook.elementalcheckers.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.gui.DefaultGuiInterface;

public class GuiTypeElementalCheckersLog extends DefaultGuiInterface {

	@Override
	public void onInventoryClick(InventoryClickEvent event, Player player, ClickType clickType, ItemStack itemClicked, ItemStack itemCursor, int slot, boolean isTopInventory) {
		event.setCancelled(true);
	}

}
