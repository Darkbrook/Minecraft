package com.darkbrook.anticheat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class WarnCatchBan {

	private final Map<Player, Integer> CLEARS = new HashMap<Player, Integer>();
	private final Map<Player, Integer> WARNINGS = new HashMap<Player, Integer>();
	private final Map<Player, Integer> CATCHES = new HashMap<Player, Integer>();
	private int clearLimit;
	private int warnLimit;
	private int catchLimit;

	public WarnCatchBan(int clearLimit, int warnLimit, int catchLimit) {
		this.clearLimit = clearLimit;
		this.catchLimit = catchLimit;
		this.warnLimit = warnLimit;
	}
	
	public boolean register(Player player, String banMessage, String hackType, boolean isWarning) {
	
		if(!isWarning || !isWarning(player)) {
			
			if(isClearing(player)) {
				if(CLEARS.containsKey(player)) CLEARS.remove(player);
				if(WARNINGS.containsKey(player)) WARNINGS.remove(player);
				if(CATCHES.containsKey(player)) CATCHES.remove(player);
			}
			
			return false;
			
		}
		
		CLEARS.remove(player);
		AntiCheat.alertOperator(player, hackType);
		WARNINGS.remove(player);

		if(isCatch(player)) {
			AntiCheat.banIp(player, banMessage.replace("{catches}", "" + CATCHES.get(player)).replace("{catch_limit}", "" + catchLimit));	
			CATCHES.remove(player);
		}
		
		return true;

	}
	
	private boolean isClearing(Player player) {
		CLEARS.put(player, CLEARS.containsKey(player) ? CLEARS.get(player) + 1 : 1);
		return CLEARS.get(player) > clearLimit;
	}
	
	private boolean isWarning(Player player) {
		WARNINGS.put(player, WARNINGS.containsKey(player) ? WARNINGS.get(player) + 1 : 1);
		return WARNINGS.get(player) > warnLimit;
	}
	
	private boolean isCatch(Player player) {
		CATCHES.put(player, CATCHES.containsKey(player) ? CATCHES.get(player) + 1 : 1);
		return CATCHES.get(player) > catchLimit;
	}
	
}
