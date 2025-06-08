package com.darkbrook.island.library.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;

public class Config {

	private String name;
	private File dataFolder;
	public FileConfiguration config;
	
	public Config(String name) {
		this.name = name;
		this.dataFolder = References.getDataFolder();
		this.create();
	}
	
	public Config(String name, File dataFolder) {
		this.name = name;
		this.dataFolder = dataFolder;
		this.create();
	}
	
	private File getConfigFile() {
		return new File(dataFolder, name + ".yml");
	}
	
	public void create() {
		
		if(!dataFolder.exists()) dataFolder.mkdirs();
		
		if(!getConfigFile().exists()) {
			
			try {
				getConfigFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
		config = YamlConfiguration.loadConfiguration(getConfigFile());
		save();
		
	}
	
	public void load() {
		
		try {
			config.load(getConfigFile());
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void save() {
		
		try {
			config.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void set(String key, Object value) {
		config.set(key, value);
		save();
	}
	
	public void setLocation(String key, Location location) {
		set(key + ".world", location.getWorld().getName());
		set(key + ".x", location.getX());
		set(key + ".y", location.getY());
		set(key + ".z", location.getZ());
		set(key + ".yaw", location.getYaw());
		set(key + ".pitch", location.getPitch());
	}
	
	public void setBlock(String key, Location location, Material material, byte data) {
		setLocation(key, location);
		set(key + ".material", material.name());
		set(key + ".data", data);
	}
	
	public void addDefault(String key, Object value) {
		if(!contains(key)) set(key, value);
	}
	
	public boolean contains(String key) {
		return config.contains(key);
	}
	
	public String getString(String key) {
		return config.getString(key);
	}
	
	public int getInt(String key) {
		return config.getInt(key);
	}
	
	public double getDouble(String key) {
		return config.getDouble(key);
	}
	
	public float getFloat(String key) {
		return (float) getDouble(key);
	}
	
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}
	
	public Location getLocation(String key) {
		return new Location(WorldLoader.loadWorld(getString(key + ".world")), getDouble(key + ".x"), getDouble(key + ".y"), getDouble(key + ".z"), getFloat(key + ".yaw"), getFloat(key + ".pitch"));
	}
	
	public Material getMaterial(String key) {
		return Material.getMaterial(getString(key + ".material"));
	}
	
	public byte getData(String key)  {
		return (byte) getInt(key + ".data");
	}
	
	public ItemStack getItemStack(String key) {
		return config.getItemStack(key);
	}
	
}
