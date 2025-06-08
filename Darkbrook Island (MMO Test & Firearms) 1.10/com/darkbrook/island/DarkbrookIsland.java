package com.darkbrook.island;

import org.bukkit.plugin.java.JavaPlugin;

import com.darkbrook.island.bunkerworld.BunkerWorldHook;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckers;
import com.darkbrook.island.internal.anticheat.AntiCheat;
import com.darkbrook.island.internal.commands.Bind;
import com.darkbrook.island.internal.commands.CommandRegistry;
import com.darkbrook.island.internal.commands.Respawn;
import com.darkbrook.island.library.entity.Npc;
import com.darkbrook.island.library.entity.NpcLoader;
import com.darkbrook.island.library.template.Selection;
import com.darkbrook.island.mmo.GameRegistry;

public class DarkbrookIsland extends JavaPlugin {
	
	public void onEnable() {
						
		References.load(this);
		GameRegistry.load(this);
		BunkerWorldHook.load(this);
		NpcLoader.load(this);
		Respawn.load(this);
		Selection.load(this);
		Bind.load(this);
		//AntiCheat.load(this);
		//WallHook.load(this);
		ElementalCheckers.unload(this);
		ElementalCheckers.load(this);

		registerCommand("buildvoxel");
		registerCommand("ec");
		registerCommand("blueprint");
		registerCommand("copy");
		registerCommand("paste");
		registerCommand("stop");
		registerCommand("instancebattle");
		registerCommand("giveskull");
		registerCommand("maze");
		registerCommand("spawnnpc");
		registerCommand("removenpc");
		registerCommand("menu");
		registerCommand("bind");
		registerCommand("kit");
		registerCommand("setspawn");
		registerCommand("spawn");
		registerCommand("circles");
		registerCommand("see");
		registerCommand("world");
		registerCommand("pulse");
		registerCommand("set");
		
	}
	
	public void registerCommand(String command) {
		this.getCommand(command).setExecutor(new CommandRegistry());
	}

	public void onDisable() {
		//Experience.unload();
		//MineHook.unload();
		Npc.unloadNps();
		Respawn.unload();
	}

}
