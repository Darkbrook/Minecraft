package com.darkbrook.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityInt implements ITileEntityField
{
	private final String nbtKey;
	public int value;
	
	public TileEntityInt(String nbtKey) 
	{
		this.nbtKey = nbtKey;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		value = compound.getInteger(nbtKey);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) 
	{
		compound.setInteger(nbtKey, value);
	}
	
	@Override
	public int getMetadata() 
	{
		return value;
	}

	@Override
	public void setFromMetadata(int value) 
	{
		this.value = value;
	}
}
