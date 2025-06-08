package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.common.item.nbt.DisplayNameData;
import com.darkbrook.kingdoms.common.item.nbt.HideFlagsData;
import com.darkbrook.kingdoms.common.item.nbt.LoreData;
import com.darkbrook.kingdoms.common.util.ColorSupplier;
import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.mmo.Category;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack.TooltipSection;

public class CategorizedItem extends KingdomsItem
{
	protected final Category category;
	
	public CategorizedItem(Category category, String displayName, ColorSupplier color, Item item)
	{
		this(category, displayName, color, item, Data.empty());
	}
	
	public CategorizedItem(Category category, String displayName, ColorSupplier color, Item item, Data data)
	{
		this(toIdentifier(displayName), category, displayName, color, item, data);
	}
	
	protected CategorizedItem(String name, Category category, String displayName, ColorSupplier color, Item item, Data data)
	{
		super(name, item, data);
		this.category = category;
		data.add(new DisplayNameData(color.on(displayName)));
		data.add(new LoreData(category.toText()));
		data.add(new HideFlagsData(TooltipSection.values()));
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	protected static String toIdentifier(String displayName)
	{
		return displayName.toLowerCase().replace(" ", "_");
	}
}
