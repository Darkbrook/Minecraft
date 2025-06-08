package com.darkbrook.island.internal.addons.elementalcheckers.replay;

import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.library.misc.CompressedSound;

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
