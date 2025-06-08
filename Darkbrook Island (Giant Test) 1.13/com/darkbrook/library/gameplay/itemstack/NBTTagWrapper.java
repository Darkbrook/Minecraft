package com.darkbrook.library.gameplay.itemstack;

import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;

import net.minecraft.server.v1_13_R1.ItemStack;
import net.minecraft.server.v1_13_R1.NBTTagCompound;

public class NBTTagWrapper 
{
	
	private NBTTagCompound tag;
	private ItemStack stack;
	
	public NBTTagWrapper(org.bukkit.inventory.ItemStack stack)
	{
		tag = (this.stack = CraftItemStack.asNMSCopy(stack)).getOrCreateTag();
	}

	public NBTTagCompound getTag()
	{
		return tag;
	}
	
	public org.bukkit.inventory.ItemStack getBukkitItemStack()
	{
		return CraftItemStack.asBukkitCopy(stack);
	}
	
}
