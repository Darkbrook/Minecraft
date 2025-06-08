package com.darkbrook.library.event.armor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUnEquipArmorEvent extends PlayerArmorEvent
{

	public PlayerUnEquipArmorEvent(Player player, ItemStack armor, ArmorType slot)
	{
		super(player, armor, slot);
	}

}
