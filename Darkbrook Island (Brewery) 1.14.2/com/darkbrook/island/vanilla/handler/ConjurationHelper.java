package com.darkbrook.island.vanilla.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.common.gameplay.visual.VisualEffect;
import com.darkbrook.island.common.gameplay.visual.VisualParticle;
import com.darkbrook.island.common.gameplay.visual.VisualSound;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.visual.VisualRegistry;
import com.darkbrook.island.common.util.helper.math.Vector3f;

public class ConjurationHelper 
{
	
	private static final ConjurationHelper INSTANCE = new ConjurationHelper();
	
	private VisualRegistry visuals;
	private VisualRegistry effects;
	private VisualRegistry invalid;
	
	public ConjurationHelper()
	{
		visuals = new VisualRegistry();
		visuals.initalize(new VisualSound(Sound.BLOCK_CONDUIT_ACTIVATE, 2.0f, 0.0f));
		visuals.initalize(new VisualParticle(Particle.LAVA, new Vector3f(0.0d, 0.0d, 0.0d), 1.0f, 128, true, false));
		
		effects = new VisualRegistry();
		effects.initalize(new VisualEffect(PotionEffectType.BLINDNESS, 40, 0, true, false));
		
		invalid = new VisualRegistry();
		invalid.initalize(new VisualSound(Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f));
	}
	
	public void playEffect(Player player)
	{
		visuals.playGlobal(player);
	}
	
	public void playInvalid(Player player)
	{
		invalid.playGlobal(player);
	}
	
	public void teleport(Player player)
	{
		Location location = player.getLocation().clone();
		
		effects.playLocal(player);
		visuals.playGlobal(location);
		
		Bukkit.getOnlinePlayers().forEach(online -> online.hidePlayer(RegistryPlugin.getInstance(), player));
		
		delay(() -> player.teleport(player.getWorld().getSpawnLocation().clone().add(0.5d, 0.0d, 0.5d)), 1);
		delay(() -> visuals.playGlobal(player), 1);
		delay(() -> Bukkit.getOnlinePlayers().forEach(online -> online.showPlayer(RegistryPlugin.getInstance(), player)), 10);
	}
	
	private void delay(Runnable runnable, long delay)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(RegistryPlugin.getInstance(), runnable, delay);
	}
	
	public static ConjurationHelper getInstance()
	{
		return INSTANCE;
	}

}
