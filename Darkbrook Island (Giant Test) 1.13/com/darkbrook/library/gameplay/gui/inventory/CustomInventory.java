package com.darkbrook.library.gameplay.gui.inventory;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.darkbrook.library.gameplay.itemstack.DarkbrookItemStack;


public class CustomInventory 
{
	
	public static String getValueFromName(InventoryClickEvent event)
	{
		return ChatColor.stripColor(new DarkbrookItemStack(event.getCurrentItem()).openMeta().getName());
	}
	
	public static String getValueFromLore(InventoryClickEvent event, int index)
	{
		return ChatColor.stripColor(new DarkbrookItemStack(event.getCurrentItem()).openMeta().getLore().get(index)).split(": ")[1];
	}
	
	public static boolean compare(Inventory inventory, Inventory custom)
	{
		return inventory != null && custom != null && inventory.toString().equals(custom.toString().replace("CraftInventoryCustom", "CraftInventory"));
	}

}
