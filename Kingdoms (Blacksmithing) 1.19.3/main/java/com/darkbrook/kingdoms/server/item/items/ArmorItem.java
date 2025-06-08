package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.server.item.ArmorSet;
import com.darkbrook.kingdoms.server.item.mmo.ArmorMake;
import com.darkbrook.kingdoms.server.item.mmo.ArmorSlot;
import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;

import net.minecraft.item.Item;

public class ArmorItem extends MetalItem
{
	protected final ArmorMake make;
	protected final ArmorSlot slot;
	
	public ArmorItem(Metal metal, ArmorMake make, ArmorSlot slot, ArmorSet<Item> item, Data data)
	{
		this(metal, make, slot, item.get(slot), data);
	}
	
	public ArmorItem(Metal metal, ArmorMake make, ArmorSlot slot, Item item, Data data)
	{
		super(metal, String.format("%s_%s_%s", metal, make, slot).toLowerCase(), Category.ARMOR, String.format("%s %s", 
				metal, make.getEquipmentTypes().get(slot)), item, data);
		this.make = make;
		this.slot = slot;
	}

	public ArmorMake getMake()
	{
		return make;
	}

	public ArmorSlot getSlot()
	{
		return slot;
	}
}
