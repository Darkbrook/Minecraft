package com.darkbrook.elementalcheckers.scrolls;

import java.awt.Point;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.elementalcheckers.ElementalCheckers;
import com.darkbrook.elementalcheckers.ElementalCheckersPiece;
import com.darkbrook.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.gui.GuiHandler;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.misc.MathHandler;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

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
		int generatedPointInt = generatePointOne(player);		
		Point point0 = GuiHandler.getCoordinates(generatedPointInt);
		Point point1 = GuiHandler.getCoordinates(generatePointTwo(generatedPointInt, player));
		return point0.x == point1.x || point0.y == point1.y || MathHandler.getDistance(point0.x, point0.y, point1.x, point1.y) <= 3 ? getPoints(player) : MathHandler.getBresenhamLine(point0.x, point0.y, point1.x, point1.y);
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
			int slot = GuiHandler.getSlot((int) points.get(i).getX(), (int) points.get(i).getY());
			if(ElementalCheckers.BOARD_SLOTS.contains(slot)) setVoid(player, slot, i * 8);
		}
	}

}
