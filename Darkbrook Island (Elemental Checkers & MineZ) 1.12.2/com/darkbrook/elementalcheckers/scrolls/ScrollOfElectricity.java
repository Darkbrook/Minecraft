package com.darkbrook.elementalcheckers.scrolls;

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
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class ScrollOfElectricity extends Scroll {

	public static final ItemStack ELECTRICITY = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 3), ChatColor.DARK_AQUA + "" + ChatColor.AQUA + "Electrical Charge");
	
	public ScrollOfElectricity(int cost, int actionPoints, int cooldown) {
		super(ChatColor.DARK_AQUA, ChatColor.AQUA, "Scroll Of Electricity", "Can be used to zap a group of warriors.", cost, actionPoints, cooldown);
	}

	@Override
	public boolean condition(ElementalCheckersPlayer player) {
		return true;
	}

	@Override
	protected void onUse(ElementalCheckersPlayer player) {
						
		player.pauseTurn(128);
		ElementalCheckersPlayer opposite = player.ec.getOpposite(player.player);
		int delay = 0;
		
		for(int y = 0; y < 9; y++) {
			
			for(int x = 0; x < 9; x++) {
				
				delay++;
				int slot = GuiHandler.getSlot(x, y);
				ItemStack stack = player.getGameBoardItem(slot);

				if(stack == null) {
					continue;
				}
				
				if(stack.getType() == Material.STAINED_GLASS_PANE) {
				
					UpdateHandler.delay(new UpdateListener() {

						@Override
						public void onUpdate() {
							
							player.setGameItem(slot, ELECTRICITY.clone());
							player.setOppositeGameItem(slot, ELECTRICITY.clone());
							player.ec.sendToAll("", "", "", new CompressedSound(Sound.ENTITY_FIREWORK_TWINKLE, 0.5F, 2.0F));	
							
							UpdateHandler.delay(new UpdateListener() {

								@Override
								public void onUpdate() {
									player.setGameItem(slot, ElementalCheckers.GROUND.clone());
									player.setOppositeGameItem(slot, ElementalCheckers.GROUND.clone());
								}
								
							}, 20);
							
						}
						
					}, delay * 2);
					
				} else if(stack.getType() == Material.STAINED_CLAY && stack.getDurability() == opposite.piece.getDurability()) {
										
					UpdateHandler.delay(new UpdateListener() {

						@Override
						public void onUpdate() {
							ElementalCheckersPiece.getPieceFromMapping(opposite, ElementalCheckersPlayer.getOppositeSlot(slot)).zap(opposite, ElementalCheckersPlayer.getOppositeSlot(slot), player.zapDamage);
						}
						
					}, delay * 2);
					
				}
				
			}
			
		}
		
		player.zapDamage += 1;
		
	}

}
