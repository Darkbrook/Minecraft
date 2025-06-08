package com.darkbrook.island.brewery.registry;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

public interface IFermentableLiquid 
{
	public PotionEffect getPotionEffect();
	
	public Material getMaterial();
	
	public ChatColor getDisplayColor();
	
	public Color getColor();
}
