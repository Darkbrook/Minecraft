package com.darkbrook.island.mmo.combat;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.MathHandler;

public class ArmorValue {
	
	public ItemStack stack;
	public int minDefence;
	public int maxDefence;
	public boolean canIncant;
	public ChatColor subColor;
	
	public ArmorValue(ItemStack stack, int minDefence, int maxDefence, boolean canIncant, ChatColor subColor) {
		this.stack = ItemHandler.addFlag(stack.clone(), ItemFlag.HIDE_ATTRIBUTES);
		this.minDefence = minDefence;
		this.maxDefence = maxDefence;
		this.canIncant = canIncant;
		this.subColor = subColor;
	}
	
	public ItemStack getDisplay() {
		return stack.clone();
	}
	
	public ItemStack getActual() {
		ItemStack copy = getDisplay();
		copy = ItemHandler.appendLore(copy, subColor + "DEF: +" + MathHandler.getRandomNumber(minDefence, maxDefence));
		return canIncant ?  ItemHandler.appendLore(copy, ChatColor.GRAY + "INC: Empty") : copy;
	}

}
