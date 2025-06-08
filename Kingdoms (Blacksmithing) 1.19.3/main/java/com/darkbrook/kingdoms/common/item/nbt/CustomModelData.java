package com.darkbrook.kingdoms.common.item.nbt;

import com.darkbrook.kingdoms.common.item.ItemStackData;

import net.minecraft.nbt.NbtCompound;

public class CustomModelData implements ItemStackData.Nbt
{
	private int customModelData = 7860000;
	
	public CustomModelData(Supplier... suppliers)
	{
		for (Supplier supplier : suppliers)
			customModelData += supplier.getCustomModelData();
	}

	@Override
	public void writeNbt(NbtCompound compound)
	{
		compound.putInt("CustomModelData", customModelData);
	}
	
	public static interface Supplier
	{
		int getCustomModelData();
	}
}
