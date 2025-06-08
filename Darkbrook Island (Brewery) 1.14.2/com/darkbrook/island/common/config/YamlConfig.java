package com.darkbrook.island.common.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.darkbrook.island.common.registry.RegistryPlugin;

public class YamlConfig
{

	private YamlConfiguration config;	
	private File file;
	
	public YamlConfig(RegistryPlugin plugin, String name)
	{
		this(new File(plugin.getDataFolder(), name));
	}
	
	public YamlConfig(File file) 
	{
		config = YamlConfiguration.loadConfiguration(this.file = file);
	}
	
	public boolean save() 
	{
		boolean isNewSave = !exists();
		
		try 
		{
			config.save(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		return isNewSave;
	}

	public void load() 
	{

		try 
		{
			config.load(file);
		} 
		catch (IOException | InvalidConfigurationException e) 
		{
			e.printStackTrace();
		}
		
	}

	public YamlConfiguration getYamlConfiguration()
	{
		return config;
	}
	
	public void set(String key, Object value) 
	{
		config.set(key, value);
	}

	public boolean contains(String key) 
	{
		return config.contains(key);
	}
	
	public boolean exists()
	{
		return file.exists();
	}
	
	public boolean delete()
	{
		return file.delete();
	}

}
