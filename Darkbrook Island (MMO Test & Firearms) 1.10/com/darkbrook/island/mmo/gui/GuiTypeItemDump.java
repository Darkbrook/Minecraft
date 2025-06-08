package com.darkbrook.island.mmo.gui;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.library.gui.GuiType;
import com.darkbrook.island.library.item.AttributeType;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.item.SlotType;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.mmo.GameRegistry;

import net.md_5.bungee.api.ChatColor;

public class GuiTypeItemDump extends GuiType {

	@Override
	protected boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		
		if(item == null) return true;
		if(click != ClickType.RIGHT && click != ClickType.LEFT) return true;
		
		if(item.isSimilar(GameRegistry.back)) {
			GameRegistry.MENU.openInventory(player);
			return true;
		} else if(item.isSimilar(GameRegistry.is_ofthesky)) {
			ItemHandler.addItem(player, ItemHandler.addAttribute(item, AttributeType.LUCK, MathHandler.RANDOM.nextInt(), SlotType.HELMET));
		} else {
			ItemHandler.addItem(player, item);
		}
		
		player.sendMessage(ChatColor.GREEN + "You have received a '" + ItemHandler.getDisplayName(item) + ChatColor.GREEN + "'"  );
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 2.0F);
		
		return true;
	}

	@Override
	protected boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		return true;
	}

}
