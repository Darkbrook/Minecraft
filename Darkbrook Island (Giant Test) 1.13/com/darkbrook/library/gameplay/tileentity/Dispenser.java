package com.darkbrook.library.gameplay.tileentity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R1.TileEntity;
import net.minecraft.server.v1_13_R1.TileEntityDispenser;

public class Dispenser extends TileEntityWrapper<TileEntityDispenser>
{

	public Dispenser(Location location) 
	{
		super(location);
	}
	
	public void addItem(ItemStack stack) 
	{
		entity.addItem(CraftItemStack.asNMSCopy(stack));
		entity.update();
	}

	@Override
	protected TileEntityDispenser loadTileEntity(TileEntity entity) 
	{
		return (TileEntityDispenser) entity;
	}

}
