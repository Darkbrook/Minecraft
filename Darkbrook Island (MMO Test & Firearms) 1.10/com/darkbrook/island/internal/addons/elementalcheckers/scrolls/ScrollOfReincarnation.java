package com.darkbrook.island.internal.addons.elementalcheckers.scrolls;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckers;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.island.library.misc.CompressedSound;

public class ScrollOfReincarnation extends Scroll {

	public ScrollOfReincarnation(int cost, int actionPoints, int cooldown) {
		super(ChatColor.DARK_RED, ChatColor.RED, "Scroll Of Reincarnation", "Can be used to reincarnate a decaying warrior.", cost, actionPoints, cooldown);
	}
	
	@Override
	public boolean condition(ElementalCheckersPlayer player) {
		ItemStack stack = player.getGameBoardItem(player.ec.selectedSlot);
		if(player.ec.selectedSlot != -1 && stack != null && stack.getType() == Material.STAINED_GLASS && stack.getDurability() == player.piece.getDurability()) return true;
		player.player.sendMessage(ChatColor.RED + "Your selected piece is not dead.");
		player.player.playSound(player.player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 0.0F);
		return false;
	}

	@Override
	protected void onUse(ElementalCheckersPlayer player) {
		player.clearMoveOptions();
		player.resetSelectedPiece(player.ec.selectedSlot);
		player.setGameItem(player.ec.selectedSlot, ElementalCheckers.GROUND);
		player.generatePieceOnBoard(player.ec.selectedSlot);
		player.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Was Reincarnated"), 
		References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Was Reincarnated"),  
		References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, player.player.getName() + "'s Warrior Was Reincarnated"), 
		new CompressedSound(Sound.BLOCK_PORTAL_TRAVEL, 0.5F, 2.0F));
		player.ec.updateTurnDisplay();
	}

}
