package com.darkbrook.library.block;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.TileEntityDispenser;

public class Dispenser {

	private TileEntityDispenser dispenser;

	public Dispenser(Location location) {
		this.dispenser = this.getTileEntityDispenserFromLocation(location);
	}
	
	public void addItem(ItemStack stack) {
		dispenser.addItem(CraftItemStack.asNMSCopy(stack));
	}
	
	private TileEntityDispenser getTileEntityDispenserFromLocation(Location location) {
		return((TileEntityDispenser) ((CraftWorld) location.getWorld()).getTileEntityAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
	}
	
}
