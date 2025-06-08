package com.darkbrook.island.mmo.equipment.armor;

import org.bukkit.ChatColor;

public enum ArmorMaterial 
{
	
	LEATHER("Leather", "Light", ChatColor.RED), 
	CHAINMAIL("Chainmail", "Light", ChatColor.GREEN), 
	BRONZE("Bronze", "Golden", "Heavy", ChatColor.YELLOW), 
	IRON("Iron", "Heavy", ChatColor.AQUA), 
	HARDENED_IRON("Hardened Iron", "Iron", "Heavy", ChatColor.AQUA);
	
	private String displayName;
	private String vanillaName;
	private String weightName;
	
	private ArmorMaterial(String displayName, String vanillaName, String weightName, ChatColor displayColor) 
	{
		this.displayName = displayColor + displayName;
		this.vanillaName = vanillaName.toUpperCase();
		this.weightName = weightName;
	}
	
	private ArmorMaterial(String displayName, String weightName, ChatColor displayColor) 
	{
		this(displayName, displayName, weightName, displayColor);
	}
		
	public String getDisplayName()
	{
		return displayName;
	}
	
	public String getVanillaName()
	{
		return vanillaName;
	}

	public String getWeightName() 
	{
		return weightName;
	}
	
}
