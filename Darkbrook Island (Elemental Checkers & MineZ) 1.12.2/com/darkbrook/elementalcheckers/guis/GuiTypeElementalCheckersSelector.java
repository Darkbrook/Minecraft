package com.darkbrook.elementalcheckers.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.elementalcheckers.ElementalCheckers;
import com.darkbrook.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.elementalcheckers.scrolls.Scroll;
import com.darkbrook.library.gui.DefaultGuiInterface;
import com.darkbrook.library.item.ItemHandler;

public class GuiTypeElementalCheckersSelector extends DefaultGuiInterface {

	private ElementalCheckers ec;
	
	public GuiTypeElementalCheckersSelector(ElementalCheckers ec) {
		this.ec = ec;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event, Player player, ClickType clickType, ItemStack itemClicked, ItemStack itemCursor, int slot, boolean isTopInventory) {
		
		event.setCancelled(true);
		if(!isTopInventory || itemClicked == null || (clickType != ClickType.RIGHT && clickType != ClickType.LEFT) ) return;
		
		ElementalCheckersPlayer ecp = ec.getClient(player);
		if(!ecp.isClickable() || ecp.isPaused()) return;
		
		if(slot == 8) {
			
			if(clickType == ClickType.RIGHT && itemClicked.getType() == Material.BOOK) {
				ecp.slowClicks(1);
				return;
			}
			
			if(clickType == ClickType.LEFT) {
				ecp.setSelectionItem(8, Scroll.cycleScrolls(ec.getClient(player)));
				player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1.0F, 0.0F);
			} else if(clickType == ClickType.RIGHT){
				
				Scroll scroll = Scroll.getScroll(itemClicked);
			
				if(Scroll.hasScroll(player, itemClicked) || scroll.getCost() == 0) {
					
					if(ecp.scroll != scroll) {
						player.sendMessage(ChatColor.BLUE + ItemHandler.getDisplayName(itemClicked) + " selected.");
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 2.0F);
						ecp.scroll = scroll;
						ecp.setSelectionItem(slot, ItemHandler.appendEnchantment(ecp.getSelectionItem(slot).clone(), Enchantment.DURABILITY, 0, false));
					} else {
						player.sendMessage(ChatColor.RED + "That scroll is already selected.");
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 0.0F);
					}
					
				} else {
					
					if(ecp.removeCoins(scroll.getCost())) {
						player.sendMessage(ChatColor.BLUE + ItemHandler.getDisplayName(itemClicked) + " unlocked.");
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
		
		if(!ecp.hasSelected && itemClicked != null && !itemClicked.isSimilar(ElementalCheckers.TAKEN) && slot > 1 && slot < 7) {
			ecp.setSelectionItem(0, ItemHandler.setDisplayName(itemClicked.clone(), ChatColor.GRAY + "" + ChatColor.BOLD + "Selected Team: " + ItemHandler.getDisplayName(itemClicked.clone())));
			player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 2.0F);
			ec.player1.setSelectionItem(slot, ElementalCheckers.TAKEN);
			ec.player2.setSelectionItem(slot, ElementalCheckers.TAKEN);
			ecp.piece = itemClicked.clone();
			ecp.hasSelected = true;
		}
		
		if(ec.player1.hasSelected && ec.player2.hasSelected) ec.game();
		ecp.slowClicks(1);
		
	}

}
