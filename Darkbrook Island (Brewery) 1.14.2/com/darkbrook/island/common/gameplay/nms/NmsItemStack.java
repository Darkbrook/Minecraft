package com.darkbrook.island.common.gameplay.nms;

import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;

import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.NBTTagCompound;

public abstract class NmsItemStack 
{
	
	protected NBTTagCompound tag;
	protected ItemStack stack;
	
	public NmsItemStack(org.bukkit.inventory.ItemStack stack)
	{
		tag = (this.stack = CraftItemStack.asNMSCopy(stack)).getOrCreateTag();
	}
	
	public org.bukkit.inventory.ItemStack getBukkitItemStack()
	{
		return CraftItemStack.asBukkitCopy(stack);
	}
	
}
