package com.darkbrook.island.mmo.gui;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;
import com.darkbrook.island.library.gui.GuiType;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.combat.InstanceBattle;
import com.darkbrook.island.mmo.entity.EntityData;

public class GuiTypeInstanceBattleMenu extends GuiType {

	@Override
	protected boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		
		if(item == null) return true;
		if(click != ClickType.RIGHT && click != ClickType.LEFT) return true;
		
		if(item.isSimilar(GameRegistry.back)) {
			GameRegistry.MENU.openInventory(player);
			return true;
		}
		
		String name = ItemHandler.getDisplayName(item);
		int ib = Integer.parseInt(name.substring(name.indexOf("Instance Battle ") + "Instance Battle ".length())) - 1;
		player.setGameMode(GameMode.SPECTATOR);
		EntityData contestant = InstanceBattle.INSTANCE_BATTLES.get(ib).contestant;

		if(contestant == null) {
			player.sendMessage(References.getInfoFormat(ChatColor.GRAY, "This instance battle has not started yet."));
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 2.0F);
		} else {
			player.teleport(contestant.entity.getLocation());
			InstanceBattle.INSTANCE_BATTLES.get(ib).contestantsPlayer.add(new EntityData(player, player.getName(), 0, 0, 0, 0, 0));
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "You have been teleported to instance " + (ib + 1) + "."));
			player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 2.0F);
		}
		
		return true;
	}

	@Override
	protected boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		return true;
	}

}
