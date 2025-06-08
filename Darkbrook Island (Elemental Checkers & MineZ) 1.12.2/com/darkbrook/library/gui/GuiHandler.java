package com.darkbrook.library.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.darkbrook.library.plugin.PluginGrabber;

public class GuiHandler implements Listener {
	
	private static final Map<Inventory, GuiInterface> INTERFACE_MAPPING = new HashMap<Inventory, GuiInterface>();
	
	public static void register() {
		Plugin plugin = PluginGrabber.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(new GuiHandler(), plugin);
	}
	
	public static void addInterface(GuiInterface interfaceGui) {
		//Inventory inventory = interfaceGui.getGui().getInventory();
		//String inventoryTitle = inventory.getClass().getSimpleName() + ")-(" + inventory.getTitle();
		//Bukkit.broadcastMessage(ChatColor.YELLOW + "[" + INTERFACE_MAPPING.size() + "] Attemping to map an inventory [(" +  inventoryTitle + ChatColor.YELLOW + ")]");
		INTERFACE_MAPPING.put(interfaceGui.getGui().getInventory(), interfaceGui);
		//Bukkit.broadcastMessage(ChatColor.GREEN + "[" + INTERFACE_MAPPING.size() + "] Inventory added [(" + inventoryTitle + ChatColor.GREEN + ")]");
	}
	
	public static int getSlot(int x, int y) {
		return (9 * y) + x;
	}
	
	public static Point getCoordinates(int slot) {
		return new Point(getX(slot), getY(slot));
	}
	
	public static List<Integer> getColumns(int rows, int columns, int min, int max) {
		List<Integer> list = new ArrayList<Integer>();
		for(int i = min; i <= max; i++) for(int r = 0; r < rows; r++) list.add((9 * r) + i);
		return list;
	}
	
	public static ItemStack[] setColumnsContents(ItemStack[] contents, int rows, int columns, int min, int max, ItemStack stack) {
		ItemStack[] contentsOut = contents == null ? new ItemStack[rows * columns] : contents;
		for(Integer i : getColumns(rows, columns, min, max)) contentsOut[i] = stack;
		return contentsOut;
	}
	
	private static int getX(int slot) {
		return (int) (slot - (getY(slot) * 9));
	}
	
	private static int getY(int slot) {
		return (int) ((double) slot / 9D);
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		
		Inventory inventoryClicked = event.getClickedInventory();
		if(inventoryClicked == null) return;
		
		Player player = (Player) event.getWhoClicked();
		GuiInterface interfaceGui = INTERFACE_MAPPING.containsKey(inventoryClicked) ? INTERFACE_MAPPING.get(inventoryClicked) : null;
		boolean isTopInventory = true;
		
		if(interfaceGui == null) {
			Inventory inventoryTop = player.getOpenInventory().getTopInventory();
			interfaceGui = INTERFACE_MAPPING.containsKey(inventoryTop) ? INTERFACE_MAPPING.get(inventoryTop) : null;
			isTopInventory = false;
			if(interfaceGui == null) return;			
		}
		
		ItemStack itemClicked = event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR ? null : event.getCurrentItem().clone();
		ItemStack itemCursor = event.getCursor() == null || event.getCursor().getType() == Material.AIR ? null : event.getCursor().clone();
		ClickType clickType = event.getClick();
		int slot = event.getSlot();
			
		interfaceGui.onInventoryClick(event, player, clickType, itemClicked, itemCursor, slot, isTopInventory);	
					
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		if(inventory == null || !INTERFACE_MAPPING.containsKey(inventory)) return;
		//String inventoryTitle = inventory.getClass().getSimpleName() + ")-(" + inventory.getTitle();
		//Bukkit.broadcastMessage(ChatColor.YELLOW + "[" + INTERFACE_MAPPING.size() + "] Attemping to unmap an inventory [(" + inventoryTitle + ChatColor.YELLOW + ")]");
		INTERFACE_MAPPING.get(inventory).getGui().getOpenSound().play((Player) event.getPlayer(), true);
		INTERFACE_MAPPING.remove(inventory);
		//Bukkit.broadcastMessage(ChatColor.GREEN + "[" + INTERFACE_MAPPING.size() + "] Inventory unmapped [(" + inventoryTitle + ChatColor.GREEN + ")]");
	}

}
