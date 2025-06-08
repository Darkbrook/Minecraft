package com.darkbrook.island.internal.addons.elementalcheckers.guis;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckers;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.island.internal.addons.elementalcheckers.scrolls.Scroll;
import com.darkbrook.island.library.gui.GuiType;
import com.darkbrook.island.library.misc.CompressedSound;

import net.md_5.bungee.api.ChatColor;

public class GuiTypeElementalCheckers extends GuiType {

	private ElementalCheckers ec;
	
	public GuiTypeElementalCheckers(ElementalCheckers ec) {
		this.ec = ec;
	}
	
	@Override
	protected boolean clickGuiEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		
		if(!ec.isValid) return false;
		if(ec.playerCurrent.player != player || item == null) return true;
		if(click != ClickType.RIGHT && click != ClickType.LEFT) return true;
		ElementalCheckersPlayer ecp = ec.getClient(player);
		if(!ecp.isClickable() || ecp.isPaused()) return true;
		
		if(ElementalCheckers.BOARD_SLOTS.contains(slot)) {
		
			if(item.getType() == Material.STAINED_CLAY && item.getDurability() == ec.playerCurrent.piece.getDurability()) {
						
				ItemStack stack = new ItemStack(item.clone());
				stack.setType(Material.WOOL);
				clicked.setItem(slot, stack);
			
				ec.selectedSlot = slot;
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 2.0F);
				ec.playerCurrent.getMovementOptions(false);
				
			} else if(item.isSimilar(ElementalCheckers.CANMOVETO)) {
				ec.onTurn("", "", "", new CompressedSound(Sound.BLOCK_ANVIL_HIT, 1.0F, 2.0F));
				ec.playerCurrent.movePiece(slot);
				ec.passTurn();
			} else if(item.getType() == Material.STAINED_GLASS && item.getDurability() == ec.playerCurrent.piece.getDurability()) {
				ec.selectedSlot = slot;
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 2.0F);
				ec.playerCurrent.getMovementOptions(true);
			}
			
		} else if(item.isSimilar(ElementalCheckers.PASS_TURN)) {
						
			if(ec.playerCurrent.removeCoins(1)) {
				ec.onTurn(ChatColor.RED + "You passed your turn.", ChatColor.RED + "Opponent passed their turn.", player.getName() + " passed their turn.", new CompressedSound(Sound.BLOCK_BREWING_STAND_BREW, 1.0F, 2.0F));
				ec.passTurn();
			} else {
				ec.playerCurrent.player.sendMessage(ChatColor.RED + "You do not have enough game coins to do this.");
				ec.playerCurrent.player.playSound(ec.playerCurrent.player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 0.0F);
			}

		} else if(item.getType() == Material.EMPTY_MAP) {
			Scroll scroll = Scroll.getScroll(item);
			scroll.use(ec.playerCurrent);
		}
		
		ecp.slowClicks(1);
		
 		return true;
	}

	@Override
	protected boolean clickInventoryEvent(Inventory clicked, Player player, int slot, ItemStack item, ItemStack cursor, ClickType click) {
		if(!ec.isValid) return false;
		return true;
	}

}
