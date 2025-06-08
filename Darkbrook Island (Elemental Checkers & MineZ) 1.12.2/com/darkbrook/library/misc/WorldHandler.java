package com.darkbrook.library.misc;

import org.bukkit.Difficulty;
import org.bukkit.World;

public class WorldHandler {

	public static void setupWorld(World world) {
		
		world.setDifficulty(Difficulty.PEACEFUL);

		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("mobGriefing", "false");
		world.setGameRuleValue("showDeathMessages", "false");
		world.setGameRuleValue("naturalRegeneration", "false");
		world.setGameRuleValue("maxEntityCramming", "0");
		world.setGameRuleValue("doFireTick", "false");
		world.setGameRuleValue("randomTickSpeed", "0");
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("doWeatherCycle", "false");
		world.setGameRuleValue("sendCommandFeedback", "false");
		
		world.setDifficulty(Difficulty.HARD);
		
	}
	
}
