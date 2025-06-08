package com.darkbrook.library.event.armor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.event.DarkbrookEvent;

public class PlayerArmorEvent extends DarkbrookEvent 
{
	
	private Player player;
	private ItemStack armor;
	private ArmorType slot;
	
	public PlayerArmorEvent(Player player, ItemStack armor, ArmorType slot)
	{
		this.player = player;
		this.armor = armor;
		this.slot = slot;
	}

	public Player getPlayer()
	{
		return player;
	}

	public ItemStack getArmor()
	{
		return armor;
	}

	public ArmorType getSlot()
	{
		return slot;
	}
	
}
