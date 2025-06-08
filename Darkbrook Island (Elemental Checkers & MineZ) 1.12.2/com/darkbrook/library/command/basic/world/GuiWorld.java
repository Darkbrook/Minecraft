package com.darkbrook.library.command.basic.world;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.gui.Gui;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.misc.MathHandler;

import net.md_5.bungee.api.ChatColor;

public class GuiWorld extends Gui {

	public GuiWorld(List<String> worlds, World world) {
		
		super(new GuiTypeWorld(), ChatColor.GOLD + "" + ChatColor.BOLD + "Worlds", MathHandler.getDivision(worlds.size(), 9) + 1);

		String worldName = WorldLoader.getDisplayWorldName(world.getName());
		
		for(int i = 0; i < worlds.size(); i++) {
			String name = worlds.get(i);
			this.setItem(i, name.equalsIgnoreCase(worldName) ? getCurrentWorld(name) : getAvailableWorld(name));
		}
		
	}
	
	private ItemStack getCurrentWorld(String name) {
		ItemStack stack = ItemHandler.setDisplayName(new ItemStack(Material.PAPER), ChatColor.YELLOW + "" + ChatColor.BOLD + name);
		stack = ItemHandler.appendLore(stack, ChatColor.DARK_GREEN + "Status: " + ChatColor.GREEN + "Occupied");
		stack = ItemHandler.appendEnchantment(stack, Enchantment.DURABILITY, 1, false);
		return stack;
	}
	
	private ItemStack getAvailableWorld(String name) {
		ItemStack stack = ItemHandler.setDisplayName(new ItemStack(Material.EMPTY_MAP), ChatColor.YELLOW + "" + ChatColor.BOLD + name);
		stack = ItemHandler.appendLore(stack, ChatColor.DARK_GREEN + "Status: " + ChatColor.GREEN + "Available");
		return stack;
	}

}
