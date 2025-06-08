package com.darkbrook.kingdoms.server.item.mmo;

import com.darkbrook.kingdoms.common.item.nbt.CustomModelData;

public enum Tool implements CustomModelData.Supplier
{
	SMITHING_HAMMER("Smithing Hammer", 1);
	
	private final String displayName;
	private final int customModelData;

	Tool(String displayName, int customModelData)
	{
		this.displayName = displayName;
		this.customModelData = customModelData;
	}
	
	@Override
	public String toString()
	{
		return displayName;
	}
	
	@Override
	public int getCustomModelData()
	{
		return customModelData;
	}
}
