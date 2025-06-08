package com.darkbrook.library.data.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.darkbrook.library.data.object.ObjectDataInterpreter;
import com.darkbrook.library.data.object.ObjectDataQueue;
import com.darkbrook.library.util.ResourceLocation;

public class Config extends ConfigBase
{
	
	public Config(String name) 
	{
		this(new ResourceLocation("$data/" + name));
	}
	
	public Config(ResourceLocation location)
	{
		super(location.getFile());
	}

	public ObjectDataQueue getData(String prefix, String keys)
	{
		String[] keyArray = ObjectDataInterpreter.interpretKeys(keys);
		Object[] data = new Object[keyArray.length];
		
		for(int i = 0; i < data.length; i++)
		{
			data[i] = getValue(prefix + "." + keyArray[i]);
		}
		
		return new ObjectDataQueue(data);
	}
	
	public void setData(Object[] data, String prefix, String keys)
	{
		String[] keyArray = ObjectDataInterpreter.interpretKeys(keys);
		setAutoSave(false);
	
		for(int i = 0; i < keyArray.length; i++)
		{
			setValue(prefix + "." + keyArray[i], data[i]);
		}
		
		setAutoSave(true);
	}
	
	public ObjectDataQueue getDataArray(String prefix, String subprefix)
	{
		prefix += "." + subprefix;
		
		if(!hasValue(prefix))
		{
			return null;
		}
		
		Object[] data = new Object[(int) getValue(prefix)];

		for(int i = 0; i < data.length; i++)
		{
			data[i] = getValue(prefix + "." + i);
		}
		
		return new ObjectDataQueue(data);
	}
	
	public void setDataArray(Object[] data, String prefix, String subprefix)
	{
		setAutoSave(false);
		setValue(prefix + "." + subprefix, data.length);
		
		for(int i = 0; i < data.length; i++)
		{
			setValue(prefix + "." + subprefix + "." + i, data[i]);
		}
		
		setAutoSave(true);
	}
	
	public FileConfiguration getConfig()
	{
		return config;
	}
	
}
