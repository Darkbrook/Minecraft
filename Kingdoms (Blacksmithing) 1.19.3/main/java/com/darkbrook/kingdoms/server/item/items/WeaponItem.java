package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.common.item.nbt.CustomModelData;
import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;
import com.darkbrook.kingdoms.server.item.mmo.Weapon;

import net.minecraft.item.Item;

public class WeaponItem extends MetalItem
{
	protected final Weapon weapon;
	
	public WeaponItem(Metal metal, Weapon weapon, Item item, Data data)
	{
		super(metal, Category.WEAPON, String.format("%s %s", metal, weapon), item, data);
		this.weapon = weapon;
	}
	
	public Weapon getWeapon()
	{
		return weapon;
	}
	
	@Override
	protected CustomModelData getTexture()
	{
		return new CustomModelData(metal, weapon);
	}
}
