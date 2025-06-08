package com.darkbrook.library.config;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.command.basic.world.WorldLoader;

public class MinecraftConfig extends DefaultConfig {

	public MinecraftConfig(String configName) {
		super(configName);
	}
	
	public MinecraftConfig(String configName, File folderParent) {
		super(configName, folderParent);
	}
	
	public Location getLocation(String key) {
	
		String displayWorldName = WorldLoader.getDisplayWorldName(getString(key + ".world"));
		boolean doesWorldExist = false;
		for(String worldName : WorldLoader.getWorldFiles()) if(worldName.equals(displayWorldName)) doesWorldExist = true;
		
		World world = doesWorldExist ? WorldLoader.getWorld(getString(key + ".world")) : null;
		double x = getDouble(key + ".x");
		double y = getDouble(key + ".y");
		double z = getDouble(key + ".z");
		float yaw = getFloat(key + ".yaw");
		float pitch = getFloat(key + ".pitch");
		
		return new Location(world, x, y, z, yaw, pitch);
		
	}
	
	public void setLocation(String key, Location location) {
		setString(key + ".world", location.getWorld().getName());
		setDouble(key + ".x", location.getX());
		setDouble(key + ".y", location.getY());
		setDouble(key + ".z", location.getZ());
		setFloat(key + ".yaw", location.getYaw());
		setFloat(key + ".pitch", location.getPitch());
	}
	
	public ItemStack getItemStack(String key) {
		return config.getItemStack(key);
	}
	
	public void setItemStack(String key, ItemStack value) {
		setValue(key, value);
	}
	
	public MemoryBlock getBlock(String key) {
		Material material = Material.getMaterial(getString(key + ".material"));
		byte data = getByte(key + ".data");
		return new MemoryBlock(material, data);
	}
	
	public void setBlock(String key, Material material, byte data) {
		setString(key + ".material", material.name());
		setByte(key + ".data", data);
	}

}
