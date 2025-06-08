package com.darkbrook.island.mmo.item;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.library.item.ItemActivator;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.mmo.GameRegistry;

public class DamageDie extends ItemActivator {

	private static final HashMap<Player, Boolean> CAN_ROLL = new HashMap<Player, Boolean>();
	private static final HashMap<Player, Integer> LAST_ROLL = new HashMap<Player, Integer>();
	
	public static void setRollTicket(Player player) {
		CAN_ROLL.put(player, true);
	}
	
	public static int getRoll(Player player) {
		return LAST_ROLL.containsKey(player) ? LAST_ROLL.get(player) : 0;
	}
	
	public static void clearRoll(Player player) {
		LAST_ROLL.remove(player);
	}
	
	@Override
	protected ItemStack getItem() {
		return GameRegistry.die;
	}

	@Override
	protected boolean onItemInteract(Player player) {

		if(player.getInventory().getItemInMainHand().isSimilar(GameRegistry.die)) {
			
			if(CAN_ROLL.containsKey(player) && CAN_ROLL.get(player)) {
				LAST_ROLL.put(player, MathHandler.RANDOM.nextInt(20) + 1);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SKELETON_DEATH, 1.0F, 0.0F);
				CAN_ROLL.put(player, false);
			}
			
			return true;
		}
		
		return false;
	}

}
