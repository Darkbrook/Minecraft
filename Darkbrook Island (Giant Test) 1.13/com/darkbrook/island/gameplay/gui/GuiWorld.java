package com.darkbrook.island.gameplay.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.darkbrook.library.gameplay.gui.GuiListener;
import com.darkbrook.library.gameplay.gui.data.GuiItem;
import com.darkbrook.library.gameplay.gui.inventory.CustomInventory;
import com.darkbrook.library.plugin.DarkbrookPlugin;
import com.darkbrook.library.util.WorldLoader;
import com.darkbrook.library.util.helper.math.MathHelper;

public class GuiWorld extends GuiListener
{
	
	private List<String> names;
	private WorldLoader loader;
	private String currentWorld;
	
	public GuiWorld(World world) 
	{
		super("world");

		names = (loader = DarkbrookPlugin.getWorldLoader()).getNames();
		currentWorld = loader.getDisplayName(world.getName());
		
		load("size", MathHelper.level(names.size(), 9));
		register();
	}
	
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		delayedReload();
	}
	
	@EventHandler 
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		delayedReload();
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		if(event.getFrom().getWorld() != event.getTo().getWorld()) delayedReload();
	}

	@Override
	protected void onItemClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked();				
		player.teleport(loader.loadExistingWorld(CustomInventory.getValueFromName(event)).getSpawnLocation());
		close(player);
	}
	
	@Override
	protected void onContentLoad() 
	{
		
		int population = Bukkit.getOnlinePlayers().size();		
		
		for(String name : names)
		{
			GuiItem item = mapping.getItem(currentWorld.equalsIgnoreCase(name) ? "occupied" : "available");
			int players = loader.loadExistingWorld(name).getPlayers().size();
			
			item.addMapping("name/glow/players/population", name, players > 0, players, population);
			addItem(item);
		}
		
	}
	
}
