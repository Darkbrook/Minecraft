package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.common.item.nbt.CustomModelData;
import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;

import net.minecraft.item.Item;

public class MetalItem extends CategorizedItem
{
	protected final Metal metal;
	
	public MetalItem(Metal metal, Category category, String displayName, Item item, Data data)
	{
		this(metal, toIdentifier(displayName), category, displayName, item, data);
	}
	
	protected MetalItem(Metal metal, String name, Category category, String displayName, Item item, Data data)
	{
		super(name, category, displayName, metal, item, data);
		this.metal = metal;
		data.setTextureSupplier(() -> getTexture());
	}
	
	public Metal getMetal()
	{
		return metal;
	}
	
	protected CustomModelData getTexture()
	{
		return new CustomModelData(metal);
	}
}
