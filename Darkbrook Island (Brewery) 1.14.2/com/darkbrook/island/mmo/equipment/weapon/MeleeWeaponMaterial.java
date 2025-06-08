package com.darkbrook.island.mmo.equipment.weapon;

import org.bukkit.ChatColor;

public enum MeleeWeaponMaterial 
{
	
	WOOD("Wooden", ChatColor.RED), 
	STONE("Stone", ChatColor.GREEN), 
	BRONZE("Bronze", "Golden", ChatColor.YELLOW), 
	IRON("Iron", ChatColor.AQUA), 
	HARDENED_IRON("Hardened Iron", "Iron", ChatColor.AQUA);
	
	private String displayName;
	private String vanillaName;
	
	private MeleeWeaponMaterial(String displayName, String vanillaName, ChatColor displayColor) 
	{
		this.displayName = displayColor + displayName;
		this.vanillaName = vanillaName.toUpperCase();
	}
	
	private MeleeWeaponMaterial(String displayName, ChatColor displayColor) 
	{
		this(displayName, displayName, displayColor);
	}
		
	public String getDisplayName()
	{
		return displayName;
	}
	
	public String getVanillaName()
	{
		return vanillaName;
	}

}
