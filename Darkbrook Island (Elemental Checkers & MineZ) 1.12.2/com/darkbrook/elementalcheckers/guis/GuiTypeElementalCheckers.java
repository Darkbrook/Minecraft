package com.darkbrook.elementalcheckers.guis;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.elementalcheckers.ElementalCheckers;
import com.darkbrook.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.elementalcheckers.scrolls.Scroll;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.gui.DefaultGuiInterface;

import net.md_5.bungee.api.ChatColor;

public class GuiTypeElementalCheckers extends DefaultGuiInterface {

	private ElementalCheckers ec;
	
	public GuiTypeElementalCheckers(ElementalCheckers ec) {
		this.ec = ec;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event, Player player, ClickType clickType, ItemStack itemClicked, ItemStack itemCursor, int slot, boolean isTopInventory) {

		event.setCancelled(true);
		if(!isTopInventory || itemClicked == null || ec.playerCurrent.player != player || (clickType != ClickType.RIGHT && clickType != ClickType.LEFT)) return;

		ElementalCheckersPlayer ecp = ec.getClient(player);
		if(!ecp.isClickable() || ecp.isPaused()) return;
		
		if(ElementalCheckers.BOARD_SLOTS.contains(slot)) {
		
			if(itemClicked.getType() == Material.STAINED_CLAY && itemClicked.getDurability() == ec.playerCurrent.piece.getDurability()) {
						
				ItemStack stack = new ItemStack(itemClicked.clone());
				stack.setType(Material.WOOL);
				this.getGui().setItem(slot, stack);
			
				ec.selectedSlot = slot;
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 2.0F);
				ec.playerCurrent.getMovementOptions(false);
				
			} else if(itemClicked.isSimilar(ElementalCheckers.CANMOVETO)) {
				ec.onTurn("", "", "", new CompressedSound(Sound.BLOCK_ANVIL_HIT, 1.0F, 2.0F));
				ec.playerCurrent.movePiece(slot);
				ec.passTurn();
			} else if(itemClicked.getType() == Material.STAINED_GLASS && itemClicked.getDurability() == ec.playerCurrent.piece.getDurability()) {
				ec.selectedSlot = slot;
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 2.0F);
				ec.playerCurrent.getMovementOptions(true);
			}
			
		} else if(itemClicked.isSimilar(ElementalCheckers.PASS_TURN)) {
						
			if(ec.playerCurrent.removeCoins(1)) {
				ec.onTurn(ChatColor.RED + "You passed your turn.", ChatColor.RED + "Opponent passed their turn.", player.getName() + " passed their turn.", new CompressedSound(Sound.BLOCK_BREWING_STAND_BREW, 1.0F, 2.0F));
				ec.passTurn();
			} else {
				ec.playerCurrent.player.sendMessage(ChatColor.RED + "You do not have enough game coins to do this.");
				ec.playerCurrent.player.playSound(ec.playerCurrent.player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 0.0F);
			}

		} else if(itemClicked.getType() == Material.EMPTY_MAP) {
			Scroll scroll = Scroll.getScroll(itemClicked);
			scroll.use(ec.playerCurrent);
		}
		
		ecp.slowClicks(1);
		
		
	}

}
