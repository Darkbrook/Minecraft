package com.darkbrook.elementalcheckers.scrolls;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.elementalcheckers.ElementalCheckers;
import com.darkbrook.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class ScrollOfRubble extends Scroll {

	public static final ItemStack RUBBLE = ElementalCheckers.addDecay(ItemHandler.setDisplayName(new ItemStack(Material.BRICK), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Rubble"), 64);
	
	public ScrollOfRubble(int cost, int actionPoints, int cooldown) {
		super(ChatColor.DARK_GRAY, ChatColor.GRAY, "Scroll Of Rubble", "Places 3 pieces of rubble on the board.", cost, actionPoints, cooldown);
	}
	
	private void placeRubble(ElementalCheckersPlayer player, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				int slot = player.getRandomEmptyBoardSlot();
				player.setGameItem(slot, RUBBLE.clone());
				player.setOppositeGameItem(slot, RUBBLE.clone());
				player.ec.sendToAll("", "", "", new CompressedSound(Sound.ENTITY_SKELETON_HURT, 1.0F, 0.0F));				
			}
			
		}, delay);
	
	}
	
	@Override
	public boolean condition(ElementalCheckersPlayer player) {
		return true;
	}

	@Override
	protected void onUse(ElementalCheckersPlayer player) {
		
		player.pauseTurn(48);
		
		for(int i = 0; i < 6; i++) {
			placeRubble(player, i * 8);
		}
		
	}

}
