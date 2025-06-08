package com.darkbrook.island.mmo.gui;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;
import com.darkbrook.island.library.gui.GuiType;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.item.SkyWalk;
import com.darkbrook.island.mmo.item.Tome;

public class GuiTypeTome extends GuiType {

	@Override
	protected boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
					
		if(item == null) return true;
		if(click != ClickType.RIGHT && click != ClickType.LEFT) return true;
		
		if(item.isSimilar(GameRegistry.action_clickfeet)) {
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "You have clicked your feet together..."));
			SkyWalk.check(player);
		} else if(item.isSimilar(GameRegistry.action_knockknee)) {
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "You have knocked your knees together..."));
			Tome.sendDelayedMessage(player, References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Nothing seems to happen."));
		} else if(item.isSimilar(GameRegistry.action_shiver)) {
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "You have shivered using your torso..."));
			Tome.sendDelayedMessage(player, References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Nothing seems to happen."));
		} else if(item.isSimilar(GameRegistry.action_wink)) {
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "You have winked..."));
			Tome.sendDelayedMessage(player, References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Nothing seems to happen."));
		} else if(item.isSimilar(GameRegistry.action_punchknuckles)) {
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "You have punched your fists together..."));
			Tome.sendDelayedMessage(player, References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Nothing seems to happen."));
		}
								
		player.closeInventory();
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 2.0F);
		
		return true;
		
	}

	@Override
	protected boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		return true;
	}

}
