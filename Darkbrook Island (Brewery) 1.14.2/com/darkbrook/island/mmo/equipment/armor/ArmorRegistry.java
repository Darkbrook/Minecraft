package com.darkbrook.island.mmo.equipment.armor;

import java.util.HashMap;
import java.util.Map;

public class ArmorRegistry 
{

	private static final String[] LEATHER_NAMESET = {"Helmet", "Cuirass", "Leggings", "Boots"};
	private static final String[] CHAINMAIL_NAMESET = {"Coif", "Haubergeon", "Leggings", "Sabatons"};
	private static final String[] HEAVY_NAMESET = {"Helmet", "Cuirass", "Leggings", "Sabatons"};

	private static final Map<ArmorMaterial, ArmorEquipment> ARMOR_MAPPING = new HashMap<ArmorMaterial, ArmorEquipment>();
	
	static
	{
		register(new ArmorEquipment(LEATHER_NAMESET, ArmorMaterial.LEATHER, 2, 8));
		register(new ArmorEquipment(CHAINMAIL_NAMESET, ArmorMaterial.CHAINMAIL, 6, 12));
		register(new ArmorEquipment(HEAVY_NAMESET, ArmorMaterial.BRONZE, 10, 16));
		register(new ArmorEquipment(HEAVY_NAMESET, ArmorMaterial.IRON, 14, 20));
		register(new ArmorEquipment(HEAVY_NAMESET, ArmorMaterial.HARDENED_IRON, 18, 24));
	}
	
	public static ArmorEquipment getArmor(ArmorMaterial material)
	{
		return ARMOR_MAPPING.get(material);
	}
	
	private static void register(ArmorEquipment equipment)
	{
		ARMOR_MAPPING.put(equipment.getMaterial(), equipment);
	}
	
}
