package com.darkbrook.library.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.darkbrook.darkbrookisland.DarkbrookIsland;

public class DefaultConfig implements ConfigInterface {
	
	protected FileConfiguration config;	
	protected File folderParent;
	protected File fileConfig;
	
	public DefaultConfig(String configName) {
		this(configName, new File(DarkbrookIsland.getResourcePath()));
	}
	
	public DefaultConfig(String configName, File folderParent) {
		this.folderParent = folderParent;
		this.fileConfig = new File(folderParent, configName + ".yml");
		this.create();
	}
	
	@Override
	public void create() {
		
		if(!folderParent.exists()) {
			folderParent.mkdirs();
		}
		
		if(!fileConfig.exists()) {
			
			try {
				fileConfig.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
		config = YamlConfiguration.loadConfiguration(fileConfig);
		save();
		
	}
	
	@Override
	public void save() {
		
		try {
			config.save(fileConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	@Override
	public boolean containsKey(String key) {
		return config.contains(key);
	}
	
	@Override
	public void addDefaults(String key, Object value) {
		if(!this.containsKey(key)) setValue(key, value);
	}
	
	@Override
	public Object getValue(String key) {
		return config.get(key);
	}
	
	@Override
	public void setValue(String key, Object value) {
		config.set(key, value);
		save();
	}
	
	@Override
	public String getString(String key) {
		return config.getString(key);
	}
	
	@Override
	public void setString(String key, String value) {
		setValue(key, value);
	}
	
	@Override
	public float getFloat(String key) {
		return (float) config.getDouble(key);
	}
	
	@Override
	public void setFloat(String key, float value) {
		setValue(key, value);
	}
	
	@Override
	public double getDouble(String key) {
		return config.getDouble(key);
	}
	
	@Override
	public void setDouble(String key, double value) {
		setValue(key, value);
	}
	
	@Override
	public long getLong(String key) {
		return config.getLong(key);
	}
	
	@Override
	public void setLong(String key, long value) {
		setValue(key, value);
	}
	
	@Override
	public int getInteger(String key) {
		return config.getInt(key);
	}
	
	@Override
	public void setInteger(String key, int value) {
		setValue(key, value);
	}
	
	@Override
	public byte getByte(String key) {
		return (byte) config.getInt(key);
	}

	@Override
	public void setByte(String key, byte value) {
		setValue(key, value);
	}
	
	@Override
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}
	
	@Override
	public void setBoolean(String key, boolean value) {
		setValue(key, value);
	}

}
