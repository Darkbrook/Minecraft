package com.darkbrook.island.mmo.misc;

import org.bukkit.inventory.ItemStack;

public enum ToolMaterial {

	WOOD(0), STONE(1), IRON(2);
	
	int value;
	
	private ToolMaterial(int value) {
		this.value = value;
	}
	
	public boolean isValid(ItemStack stack) {
		int internalValue = -1;
		if(stack.getType().toString().contains("WOOD")) internalValue = 0;
		if(stack.getType().toString().contains("STONE")) internalValue = 1;
		if(stack.getType().toString().contains("IRON")) internalValue = 2;
		if(internalValue >= value) return true;
		return false;
	}
	
}
