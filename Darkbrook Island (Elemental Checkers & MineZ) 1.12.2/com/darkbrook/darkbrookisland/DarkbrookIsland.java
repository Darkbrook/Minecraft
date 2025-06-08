package com.darkbrook.darkbrookisland;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.darkbrook.anticheat.AntiCheat;
import com.darkbrook.elementalcheckers.CommandEc;
import com.darkbrook.elementalcheckers.ElementalCheckers;
import com.darkbrook.library.blueprint.command.CommandBlueprint;
import com.darkbrook.library.blueprint.selection.BlockSelectionHandler;
import com.darkbrook.library.bossbar.BossBar;
import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.basic.bind.Bind;
import com.darkbrook.library.command.basic.bind.CommandBind;
import com.darkbrook.library.command.basic.kit.CommandKit;
import com.darkbrook.library.command.basic.maze.CommandMaze;
import com.darkbrook.library.command.basic.npc.CommandNpc;
import com.darkbrook.library.command.basic.npc.NpcLoader;
import com.darkbrook.library.command.basic.realtime.CommandRealtime;
import com.darkbrook.library.command.basic.realtime.TimeHandler;
import com.darkbrook.library.command.basic.see.CommandSee;
import com.darkbrook.library.command.basic.shutdown.CommandStop;
import com.darkbrook.library.command.basic.world.CommandWorld;
import com.darkbrook.library.config.MinecraftConfig;
import com.darkbrook.library.file.FileChecker;
import com.darkbrook.library.gui.GuiHandler;
import com.darkbrook.library.plugin.PluginGrabber;
import com.darkbrook.minez.MinezHandler;
import com.darkbrook.minez.command.entity.CommandEntity;
import com.darkbrook.minez.command.minez.CommandMinez;
import com.darkbrook.minez.command.project.CommandProject;
import com.darkbrook.minez.command.project.ProjectListener;
import com.darkbrook.minez.entity.MinezEntityGiant;
import com.darkbrook.minez.entity.MinezEntityPigZombie;
import com.darkbrook.minez.entity.MinezEntityPlayer;
import com.darkbrook.minez.entity.MinezEntitySkeleton;
import com.darkbrook.minez.entity.MinezEntityZombie;

public class DarkbrookIsland extends JavaPlugin {
	
	public static String getResourcePath() {
		return instance.getDataFolder().getAbsolutePath();
	}
	
	public static String getServerPath() {
		return getResourcePath().replace("\\plugins\\" + instance.getName(), "");
	}
	
	public static MinecraftConfig playerdata;
	public static MinecraftConfig kitdata;
	private static Plugin instance;
	
	@Override
	public void onEnable() {
		
		PluginGrabber.grab(this);
		instance = PluginGrabber.getPlugin();
		
		playerdata = new MinecraftConfig("playerdata");
		kitdata = new MinecraftConfig("kitdata");
		
		FileChecker.createMissingDirectory(instance.getDataFolder().getAbsolutePath());
		FileChecker.createMissingDirectory(DarkbrookIsland.getResourcePath());
		FileChecker.createMissingDirectory(DarkbrookIsland.getResourcePath() + "\\Blueprints");
		FileChecker.createMissingDirectory(DarkbrookIsland.getResourcePath() + "\\BlueprintTasks");
		
		TimeHandler.enableRealTime();
		
		DarkbrookIslandListener.register();
		AntiCheat.register();
		GuiHandler.register();
		Bind.register();
		ElementalCheckers.register();
		
		NpcLoader.load();
		
		registerListener(new BlockSelectionHandler());
		registerListener(new MinezHandler());
		registerListener(new MinezEntitySkeleton());
		registerListener(new MinezEntityZombie());
		registerListener(new MinezEntityPigZombie());
		registerListener(new MinezEntityPlayer());
		registerListener(new MinezEntityGiant());
		registerListener(new ProjectListener());
		
		registerCommandListener(new CommandSee());
		registerCommandListener(new CommandWorld());
		registerCommandListener(new CommandKit());
		registerCommandListener(new CommandNpc());
		registerCommandListener(new CommandBind());
		registerCommandListener(new CommandStop());
		registerCommandListener(new CommandMaze());
		registerCommandListener(new CommandBlueprint());
		registerCommandListener(new CommandEc());
		registerCommandListener(new CommandRealtime());
		registerCommandListener(new CommandProject());
		registerCommandListener(new CommandEntity());
		registerCommandListener(new CommandMinez());

		for(Player player : Bukkit.getOnlinePlayers()) {
			DarkbrookIsland.updateLogoutLocation(player);
		}
										
	}
	
	@Override
	public void onDisable() {
		
		ProjectListener.resetProjections();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			DarkbrookIsland.updateLogoutLocation(player);
		}
		
		BossBar.onServerReload();
		NpcLoader.unload();
				
	}
	
	public static void updateUsername(Player player) {
		DarkbrookIsland.playerdata.setString(player.getUniqueId() + ".username", player.getName());
	}
	
	public static void updateIp(Player player) {
		DarkbrookIsland.playerdata.setString(player.getUniqueId() + ".ip", player.getAddress().getHostString());
	}
	
	public static void updateOpStatus(Player player) {
		DarkbrookIsland.playerdata.setBoolean(player.getUniqueId() + ".op", player.isOp());
	}
	
	public static void updateLogoutLocation(Player player) {
		DarkbrookIsland.playerdata.setLocation(player.getUniqueId() + ".logout", player.getLocation());
	}
	
	private void registerListener(Listener listener) {
		instance.getServer().getPluginManager().registerEvents(listener, instance);
	}
	
	private void registerCommandListener(CommandListener listener) {
		this.getCommand(listener.getCommandPrefix()).setExecutor(listener);
	}
	
}
