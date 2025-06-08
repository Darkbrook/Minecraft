package com.darkbrook.island.brewery;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.brewery.registry.IFermentableLiquid;

public enum BreweryLiquid implements IFermentableLiquid
{
	
	MAROON(new PotionEffect(PotionEffectType.HEAL, 0, 3), Material.RED_STAINED_GLASS_PANE, ChatColor.RED, Color.MAROON),
	AMBER(new PotionEffect(PotionEffectType.FAST_DIGGING, 3600, 1), Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW, Color.fromRGB(0xFF, 0xC0, 0x00));

	private PotionEffect potionEffect;
	private Material material;
	private ChatColor displayColor;
	private Color color;
		
	private BreweryLiquid(PotionEffect potionEffect, Material material, ChatColor displayColor, Color color) 
	{
		this.potionEffect = potionEffect;
		this.material = material;
		this.displayColor = displayColor;
		this.color = color;
	}
	
	@Override
	public PotionEffect getPotionEffect() 
	{
		return potionEffect;
	}

	@Override
	public Material getMaterial() 
	{
		return material;
	}
	
	@Override
	public ChatColor getDisplayColor() 
	{
		return displayColor;
	}
	
	@Override
	public Color getColor() 
	{
		return color;
	}

}
