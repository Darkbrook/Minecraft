package com.darkbrook.library.gui;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.compressed.CompressedSound;

public class Gui {
	
	private static final CompressedSound DEFAULT_OPEN_SOUND = new CompressedSound(Sound.ENTITY_FIREWORK_SHOOT, 0.5F, 2.0F);

	private CompressedSound soundOpen;
	private Inventory inventory;
	private ItemStack[] contents;
	private String title;
	private int size;
	
	public Gui(GuiInterface interfaceGui, String title, int rows) {
				
		this.soundOpen = DEFAULT_OPEN_SOUND;
		this.title = title;
		this.size = rows * 9;
		this.inventory = Bukkit.createInventory(null, size, title);
				
		interfaceGui.setGui(this);
		interfaceGui.setTitle(title);
		
		GuiHandler.addInterface(interfaceGui);		
		
	}
	
	public void setOpenSound(CompressedSound soundOpen) {
		this.soundOpen = soundOpen;
	}
	
	public void open(Player player) {
		player.closeInventory();
		player.openInventory(inventory);
		player.updateInventory();
		if(soundOpen != null) soundOpen.play(player, true);		
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
	
	public void setItem(int x, int y, ItemStack stack) {
		setItem(GuiHandler.getSlot(x, y), stack);
	}
	
	public CompressedSound getOpenSound() {
		return soundOpen;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ItemStack getItem(int slot) {
		return inventory.getItem(slot);
	}

	public String getTitle() {
		return title;
	}
	
	public int getSize() {
		return size;
	}

}
