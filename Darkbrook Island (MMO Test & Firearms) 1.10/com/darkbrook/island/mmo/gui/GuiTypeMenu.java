package com.darkbrook.island.mmo.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.library.gui.GuiType;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.combat.InstanceBattle;
import com.darkbrook.island.mmo.combat.InstanceBattleBase;

import net.md_5.bungee.api.ChatColor;

public class GuiTypeMenu extends GuiType {

	@Override
	protected boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		if(item == null) return true;
		if(click != ClickType.RIGHT && click != ClickType.LEFT) return true;
		
		if(item.isSimilar(GameRegistry.instancebattle)) {
			for(int i = 0; i < 27; i++) GameRegistry.MENU_INSTANCE_BATTLES.setItem(i, null);
			List<InstanceBattleBase> ibbs = InstanceBattle.INSTANCE_BATTLES;
			for(int i = 0; i < ibbs.size(); i++) GameRegistry.MENU_INSTANCE_BATTLES.setItem(i, ItemHandler.setDisplayName(new ItemStack(Material.PAPER), ChatColor.WHITE + "" + ChatColor.BOLD + "Instance Battle " + (i + 1)));
			GameRegistry.MENU_INSTANCE_BATTLES.setItem(26, GameRegistry.back);
			GameRegistry.MENU_INSTANCE_BATTLES.openInventory(player);
		}
		if(item.isSimilar(GameRegistry.items)) GameRegistry.MENU_ITEMS.openInventory(player);
		if(item.isSimilar(GameRegistry.armor)) GameRegistry.MENU_ARMOR.openInventory(player);
		if(item.isSimilar(GameRegistry.legendaries)) GameRegistry.MENU_LEGENDARIES.openInventory(player);
		if(item.isSimilar(GameRegistry.bunkerworld)) GameRegistry.MENU_BUNKERWORLD.openInventory(player);
		return true;
	}

	@Override
	protected boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		return true;
	}

}
