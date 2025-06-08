package com.darkbrook.library.event.armor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerEquipArmorEvent extends PlayerArmorEvent
{

	public PlayerEquipArmorEvent(Player player, ItemStack armor, ArmorType slot)
	{
		super(player, armor, slot);
	}

}
