package com.darkbrook.island.mmo.equipment.weapon;

public enum MeleeWeaponType 
{
	
	WAR_HAMMER(0.2f, 0.8f), SHORTSWORD(0.4f, 0.6f), BATTLEAXE(0.6f, 0.4f), FLANGED_MACE(0.8f, 0.2f);
	
	private float crushing;
	private float piercing;
	
	private MeleeWeaponType(float crushing, float piercing) 
	{
		this.crushing = crushing;
		this.piercing = piercing;
	}

	public float getCrushing() 
	{
		return crushing;
	}

	public float getPiercing() 
	{
		return piercing;
	}	
	
}
