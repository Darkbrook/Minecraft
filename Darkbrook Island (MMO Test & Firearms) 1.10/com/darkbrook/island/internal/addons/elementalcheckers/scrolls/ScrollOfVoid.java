package com.darkbrook.island.internal.addons.elementalcheckers.scrolls;

import java.awt.Point;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckers;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckersPiece;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.island.library.gui.Gui;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.CompressedSound;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public class ScrollOfVoid extends Scroll {

	public static final ItemStack VOID = ElementalCheckers.addDecay(ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Void"), 16);
	
	public ScrollOfVoid(int cost, int actionPoints, int cooldown) {
		super(ChatColor.DARK_GRAY, ChatColor.GRAY, "Scroll Of Void", "Opens a ravine randomly on the map.", cost, actionPoints, cooldown);
	}
	
	private void sendKillMessage(ElementalCheckersPlayer player, int slot) {
					
		ElementalCheckersPlayer playerCurrent = player.ec.playerCurrent;
		ElementalCheckersPlayer playerOpposite = player.ec.getOpposite(playerCurrent.player);
		
		if(player == playerCurrent) {
			ElementalCheckersPiece.getPieceFromMapping(playerCurrent, slot).voidDeath(playerCurrent, slot, slot, false);
		} else if(player == playerOpposite) {
			int slotOpposite = ElementalCheckersPlayer.getOppositeSlot(slot);
			ElementalCheckersPiece.getPieceFromMapping(playerOpposite, slotOpposite).voidDeath(playerOpposite,slotOpposite, slotOpposite, false);
		}
		
	}
	
	private void setVoid(ElementalCheckersPlayer player, int slot, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				if(player.getGameItem(slot).getType() == Material.STAINED_CLAY) {	
					ElementalCheckersPlayer opposite = player.ec.getOpposite(player.player);
					if(player.getGameItem(slot).getDurability() == player.piece.getDurability()) {
						sendKillMessage(player, slot);
					} else if(player.getGameItem(slot).getDurability() == opposite.piece.getDurability()) {
						sendKillMessage(opposite, slot);
					}
				}	
					
				player.setGameItem(slot, VOID.clone());
				player.setOppositeGameItem(slot, VOID.clone());
				player.ec.sendToAll("", "", "", new CompressedSound(Sound.BLOCK_STONE_BREAK, 1.0F, 0.0F));	
				
			}
			
		}, delay);
	
	}
	
	private int generatePointOne(ElementalCheckersPlayer player) {
		int slot = player.getRandomEmptyBoardSlot();
		return player.getGameItem(slot).isSimilar(ElementalCheckers.GROUND) ? slot : generatePointOne(player);
	}
	
	private int generatePointTwo(int p1, ElementalCheckersPlayer player) {
		int slot = player.getRandomEmptyBoardSlot();
		return slot != p1 && player.getGameItem(slot).isSimilar(ElementalCheckers.GROUND) ? slot : generatePointOne(player);
	}

	private List<Point> getPoints(ElementalCheckersPlayer player) {
		int p1 = generatePointOne(player);
		int p2 = generatePointTwo(p1, player);
		int x1 = Gui.getX(p1);
		int y1 = Gui.getY(p1);
		int x2 = Gui.getX(p2);
		int y2 = Gui.getY(p2);
		if(x1 != x2 && y1 != y2 && MathHandler.distance2D(x1, y1, x2, y2) > 3) return MathHandler.getBresenhamLine(x1, y1, x2, y2);
		return getPoints(player);
	}
	
	@Override
	public boolean condition(ElementalCheckersPlayer player) {
		return true;
	}

	@Override
	protected void onUse(ElementalCheckersPlayer player) {
		player.pauseTurn(64);
		List<Point> points = getPoints(player);
		for(int i = 0; i < points.size(); i++) {
			int slot = Gui.getSlot((int) points.get(i).getX(), (int) points.get(i).getY());
			if(ElementalCheckers.BOARD_SLOTS.contains(slot)) setVoid(player, slot, i * 8);
		}
	}

}
