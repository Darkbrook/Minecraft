package com.darkbrook.library.data.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class ConfigBase 
{
	
	protected FileConfiguration config;	
	protected boolean isAutoSave;
	
	private File file;
	private File folder;
	
	public ConfigBase(File file) 
	{
		this.file = file;
		
		folder = file.getParentFile();
		config = YamlConfiguration.loadConfiguration(file);
		isAutoSave = true;
		
		create();
	}
	
	public void create() 
	{
		folder.mkdirs();
		
		if(!file.exists())
		{
			
			try 
			{
				file.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
			
		save();
	}
	
	public void save() 
	{
		
		try 
		{
			config.save(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}	
	
	public Object getValue(String key) 
	{
		return config.get(key);
	}
	
	public void setValue(String key, Object value) 
	{
		
		config.set(key, value);
		
		if(isAutoSave)
		{
			save();
		}
		
	}
	
	public void setDefaultValue(String key, Object value) 
	{
		
		if(!hasValue(key)) 
		{
			setValue(key, value);
		}
		
	}
	
	public void setAutoSave(boolean isAutoSave)
	{
		
		this.isAutoSave = isAutoSave;
		
		if(isAutoSave)
		{
			save();
		}
		
	}
	
	public boolean exists() 
	{
		return file.exists();
	}
	
	public boolean hasValue(String key) 
	{
		return config.contains(key);
	}

}