package com.darkbrook.island.mmo.equipment.weapon;

import java.util.HashMap;
import java.util.Map;

public class MeleeWeaponRegistry 
{

	private static final String[] MELEE_WEAPON_NAMESET = {"War Hammer", "Shortsword", "Battleaxe", "Flanged Mace"}; 

	private static final Map<MeleeWeaponMaterial, MeleeWeaponEquipment> MELEE_WEAPON_MAPPING = new HashMap<MeleeWeaponMaterial, MeleeWeaponEquipment>();
	
	static
	{
		register(new MeleeWeaponEquipment(MELEE_WEAPON_NAMESET, MeleeWeaponMaterial.WOOD, 4, 16));
		register(new MeleeWeaponEquipment(MELEE_WEAPON_NAMESET, MeleeWeaponMaterial.STONE, 12, 24));
		register(new MeleeWeaponEquipment(MELEE_WEAPON_NAMESET, MeleeWeaponMaterial.BRONZE, 20, 32));
		register(new MeleeWeaponEquipment(MELEE_WEAPON_NAMESET, MeleeWeaponMaterial.IRON, 28, 40));
		register(new MeleeWeaponEquipment(MELEE_WEAPON_NAMESET, MeleeWeaponMaterial.HARDENED_IRON, 36, 48));
	}
	
	public static MeleeWeaponEquipment getWeapon(MeleeWeaponMaterial material)
	{
		return MELEE_WEAPON_MAPPING.get(material);
	}
	
	private static void register(MeleeWeaponEquipment equipment)
	{
		MELEE_WEAPON_MAPPING.put(equipment.getMaterial(), equipment);
	}

}
