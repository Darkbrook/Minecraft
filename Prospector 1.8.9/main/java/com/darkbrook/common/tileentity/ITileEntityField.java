package com.darkbrook.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileEntityField 
{
	void readFromNBT(NBTTagCompound compound);
	
	void writeToNBT(NBTTagCompound compound);
	
	int getMetadata();
	
	void setFromMetadata(int data);
}
