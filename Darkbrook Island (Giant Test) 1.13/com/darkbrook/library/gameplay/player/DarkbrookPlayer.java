package com.darkbrook.library.gameplay.player;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.darkbrook.island.gameplay.visual.VisualRepository;

public class DarkbrookPlayer 
{
	
	private Player player;
	
	public DarkbrookPlayer(Player player) 
	{
		this.player = player;
	}
	
	@SuppressWarnings("deprecation")
	public void sendBlockChange(Location location, Material material)
	{
		player.sendBlockChange(location, material, (byte) 0);
	}
	
	public void crash()
	{
		VisualRepository.crash.play(player, true);	
	}
	
	public void addPotionEffect(PotionEffect effect) 
	{
		player.removePotionEffect(effect.getType());
		player.addPotionEffect(effect);
	}
	
	public boolean togglePotionEffect(PotionEffect effect)
	{
		boolean hasPotionEffect = player.hasPotionEffect(effect.getType());
		
		if(hasPotionEffect)
		{
			player.removePotionEffect(effect.getType());
		}
		else
		{
			player.addPotionEffect(effect);
		}
		
		return hasPotionEffect;
	}

}
