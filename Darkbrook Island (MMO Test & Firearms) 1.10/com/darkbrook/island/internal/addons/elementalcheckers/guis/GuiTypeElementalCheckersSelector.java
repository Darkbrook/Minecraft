package com.darkbrook.island.internal.addons.elementalcheckers.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckers;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.island.internal.addons.elementalcheckers.scrolls.Scroll;
import com.darkbrook.island.library.gui.GuiType;
import com.darkbrook.island.library.item.ItemHandler;

public class GuiTypeElementalCheckersSelector extends GuiType {

	private ElementalCheckers ec;
	
	public GuiTypeElementalCheckersSelector(ElementalCheckers ec) {
		this.ec = ec;
	}

	@Override
	protected boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		
		if(!ec.isValid) return false;
		if(item == null) return true;
		if(click != ClickType.RIGHT && click != ClickType.LEFT) return true;
		ElementalCheckersPlayer ecp = ec.getClient(player);
		if(!ecp.isClickable() || ecp.isPaused()) return true;
		
		if(slot == 8) {
			
			if(click == ClickType.RIGHT && item.getType() == Material.BOOK) {
				ecp.slowClicks(1);
				return true;
			}
			
			if(click == ClickType.LEFT) {
				ecp.setSelectionItem(8, Scroll.cycleScrolls(ec.getClient(player)));
				player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1.0F, 0.0F);
			} else if(click == ClickType.RIGHT){
				
				Scroll scroll = Scroll.getScroll(item);
			
				if(Scroll.hasScroll(player, item) || scroll.getCost() == 0) {
					
					if(ecp.scroll != scroll) {
						player.sendMessage(ChatColor.BLUE + ItemHandler.getDisplayName(item) + " selected.");
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 2.0F);
						ecp.scroll = scroll;
						ecp.setSelectionItem(slot, ItemHandler.enchant(ecp.getSelectionItem(slot).clone(), Enchantment.DURABILITY, 0, false));
					} else {
						player.sendMessage(ChatColor.RED + "That scroll is already selected.");
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 0.0F);
					}
					
				} else {
					
					if(ecp.removeCoins(scroll.getCost())) {
						player.sendMessage(ChatColor.BLUE + ItemHandler.getDisplayName(item) + " unlocked.");
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 2.0F);
						ecp.setSelectionItem(8, scroll.getDisplayUnlocked());
						scroll.unlock(player);
					} else {
						player.sendMessage(ChatColor.RED + "You do not have enough coins for this item.");
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 0.0F);
					}
					
				}
			
			}
		
		}
		
		if(!ecp.hasSelected && item != null && !item.isSimilar(ElementalCheckers.TAKEN) && slot > 1 && slot < 7) {
			ecp.setSelectionItem(0, ItemHandler.setDisplayName(item.clone(), ChatColor.GRAY + "" + ChatColor.BOLD + "Selected Team: " + ItemHandler.getDisplayName(item.clone())));
			player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 2.0F);
			ec.player1.setSelectionItem(slot, ElementalCheckers.TAKEN);
			ec.player2.setSelectionItem(slot, ElementalCheckers.TAKEN);
			ecp.piece = item.clone();
			ecp.hasSelected = true;
		}
		
		if(ec.player1.hasSelected && ec.player2.hasSelected) ec.game();
		ecp.slowClicks(1);
		
		return true;
	}

	@Override
	protected boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		if(!ec.isValid) return false;
		return true;
	}

}
