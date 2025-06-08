package com.darkbrook.library.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftGiant;
import org.bukkit.entity.Player;

import com.darkbrook.library.event.DarkbrookEvent;
import com.darkbrook.library.event.armor.PlayerArmorListener;
import com.darkbrook.library.event.tick.async.AsyncEntityTickCycleEvent;
import com.darkbrook.library.event.tick.async.AsyncTickEvent;
import com.darkbrook.library.event.tick.sync.TickCycleEvent;
import com.darkbrook.library.event.tick.sync.TickEvent;
import com.darkbrook.library.gameplay.blueprint.CommandBp;
import com.darkbrook.library.gameplay.blueprint.selection.player.PlayerSelectionMapping;
import com.darkbrook.library.gameplay.bossbar.EntityBossBarManager;
import com.darkbrook.library.gameplay.bossbar.EntityBossBarPreset;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.plugin.registry.RegistryManager;
import com.darkbrook.library.util.ResourceLocation;
import com.darkbrook.library.util.WorldLoader;

public abstract class DarkbrookPlugin extends RegistryManager 
{
	
	private static DarkbrookPlugin plugin;
	private static WorldLoader loader;
	
	@Override
	public void onEnable() 
	{		
		
		ResourceLocation.addReplacements("data", getDataFolder().getAbsolutePath(), "worlds", "worlds");

		plugin = this;
		loader = new WorldLoader(new ResourceLocation("$worlds"));

		register(new IRegistryValue[]
		{
			new CommandBp(),
			new AsyncTickEvent(),
			new EntityBossBarManager(new Class<?>[] {CraftGiant.class}, new EntityBossBarPreset[] {new EntityBossBarPreset(ChatColor.GRAY + "Giant", 64, BarColor.WHITE, BarStyle.SEGMENTED_6, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY)}),
			new PlayerSelectionMapping(),
			new PlayerArmorListener(),
			new TickEvent(),
			new AsyncEntityTickCycleEvent(),
			new TickCycleEvent()
		});
		
	}
	
	@Override
	public void onDisable()
	{
		for(Player player : Bukkit.getOnlinePlayers())
		{
			player.closeInventory();
		}
		
		EntityBossBarManager.unregisterAll();
	}
	
	public static void executeEventStatic(DarkbrookEvent event)
	{
		plugin.executeEvent(event);
	}
	
	public static void registerStatic(IRegistryValue... values)
	{
		plugin.register(values);
	}
	
	public static void unregisterStatic(IRegistryValue... values)
	{
		plugin.unregister(values);
	}
	
	public static DarkbrookPlugin getPlugin()
	{
		return plugin;
	}
	
	public static WorldLoader getWorldLoader()
	{
		return loader;
	}
	
}
