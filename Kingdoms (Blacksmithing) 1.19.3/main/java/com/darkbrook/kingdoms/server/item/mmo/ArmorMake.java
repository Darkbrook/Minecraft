package com.darkbrook.kingdoms.server.item.mmo;

import com.darkbrook.kingdoms.server.item.ArmorSet;

public enum ArmorMake
{
	MAIL("Mail", new ArmorSet<>("Coif", "Byrnie", "Chausses", "Sabatons")), 
	PLATE("Plate", new ArmorSet<>("Helmet", "Cuirass", "Leggings", "Sabatons"));
	
	private final String displayName;
	private final ArmorSet<String> equipmentTypes;
	
	ArmorMake(String displayName, ArmorSet<String> equipmentTypes)
	{
		this.displayName = displayName;
		this.equipmentTypes = equipmentTypes;
	}

	@Override
	public String toString()
	{
		return displayName;
	}

	public ArmorSet<String> getEquipmentTypes()
	{
		return equipmentTypes;
	}
}
