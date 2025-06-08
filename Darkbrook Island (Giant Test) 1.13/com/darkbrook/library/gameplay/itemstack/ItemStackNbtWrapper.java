package com.darkbrook.library.gameplay.itemstack;

import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R1.NBTTagCompound;

public abstract class ItemStackNbtWrapper extends ItemStackMetaWrapper
{

	private NBTTagWrapper wrapper;
	private NBTTagCompound tag;
	
	public ItemStackNbtWrapper(ItemStack stack)
	{
		super(stack);
	}

	public ItemStackNbtWrapper(String typeName, int amount, int durability)
	{
		super(typeName, amount, durability);
	}
	
	public ItemStackNbtWrapper openNbt()
	{
		if(tag == null)
		{
			tag = (wrapper = new NBTTagWrapper(stack)).getTag();
		}
		
		return this;
	}

	public ItemStack applyNbt()
	{
		return stack = wrapper.getBukkitItemStack();
	}

	public String getString(String key)
	{
		return tag.getString(key);
	}
	
	public void setString(String key, String value)
	{
		tag.setString(key, value);
	}
	
	public int getInt(String key)
	{
		return tag.getInt(key);
	}
	
	public void setInt(String key, int value)
	{
		tag.setInt(key, value);
	}
	
	public boolean getBoolean(String key)
	{
		return tag.getBoolean(key);
	}
	
	public void setBoolean(String key, boolean value)
	{
		tag.setBoolean(key, value);
	}
	
	public boolean hasKey(String key)
	{
		return tag.hasKey(key);
	}
	
}
