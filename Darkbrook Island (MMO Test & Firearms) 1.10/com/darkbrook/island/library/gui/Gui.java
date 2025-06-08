package com.darkbrook.island.library.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;

public class Gui {

	private static final ArrayList<GuiType> GUITYPES = new ArrayList<GuiType>();
	
	private String title;
	private int size;
	private ItemStack[] contents;
	private GuiType type;
	private Inventory inventory;
	
	public Gui(String title, int rows, GuiType type) {
		this.title = title;
		this.size = rows * 9;
		this.type = type;
		this.type.gui = this;
		if(!GUITYPES.contains(type)) {
			References.plugin.getServer().getPluginManager().registerEvents(type, References.plugin);
			GUITYPES.add(type);
		}
	}
	
	public void openInventory(Player player) {
		player.closeInventory();
		Inventory inventory = this.inventory == null ? Bukkit.createInventory(player, size, title) : this.inventory;
		if(contents != null) inventory.setContents(contents);
		player.openInventory(inventory);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_TWINKLE_FAR, 0.5F, 2.0F);
		this.inventory = inventory;
	}
	
	public void setContents(ItemStack[] contents) {
		this.contents = contents; 
		if(inventory != null) inventory.setContents(contents);
	}
	
	public ItemStack[] getContents() {
		return contents == null ? new ItemStack[size] : contents;
	}
	
	public void setItem(int slot, ItemStack stack) {
		ItemStack[] contents = getContents();
		contents[slot] = stack;
		setContents(contents);
	}
	
	public ItemStack getItem(int slot) {
		return inventory.getItem(slot);
	}

	public String getName() {
		return title;
	}
	
	public int getSize() {
		return size;
	}

	public static int getSlot(int x, int y) {
		return (9 * y) + x;
	}
	
	public static int getX(int slot) {
		return slot - (getY(slot) * 9);
	}
	
	public static int getY(int slot) {
		return (int) ((double) slot / 9D);
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

}
