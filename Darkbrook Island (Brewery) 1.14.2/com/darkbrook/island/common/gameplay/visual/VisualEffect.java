package com.darkbrook.island.common.gameplay.visual;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.common.registry.visual.local.IRegistryVisualLocal;

public class VisualEffect implements IRegistryVisualLocal
{

	private PotionEffect effect;
		
	public VisualEffect(PotionEffectType type, int duration, int amplifier, boolean isAmbient, boolean isVisible) 
	{
		effect = new PotionEffect(type, duration, amplifier, isAmbient, isVisible);
	}

	@Override
	public void playLocal(Player player) 
	{
		player.addPotionEffect(effect);		
	}
	
}
