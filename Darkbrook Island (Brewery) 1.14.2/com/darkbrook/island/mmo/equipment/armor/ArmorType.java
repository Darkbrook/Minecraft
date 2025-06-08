package com.darkbrook.island.mmo.equipment.armor;

import org.bukkit.inventory.EquipmentSlot;

public enum ArmorType 
{
	
	HELMET(EquipmentSlot.HEAD), 
	CHESTPLATE(EquipmentSlot.CHEST), 
	LEGGINGS(EquipmentSlot.LEGS), 
	BOOTS(EquipmentSlot.FEET);
	
	private EquipmentSlot slot;

	private ArmorType(EquipmentSlot slot) 
	{
		this.slot = slot;
	}

	public EquipmentSlot getEquipmentSlot() 
	{
		return slot;
	}	
	
}
