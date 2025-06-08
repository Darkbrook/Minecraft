package com.darkbrook.elementalcheckers.replay;

import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.compressed.CompressedSound;

public class ElementalCheckersFrame {
	
	public ItemStack[] contents;
	public CompressedSound sound;
	public String message;
	
	public ElementalCheckersFrame(ItemStack[] contents, CompressedSound sound, String message) {
		this.contents = contents;
		this.sound = sound;
		this.message = message;
	}

}
