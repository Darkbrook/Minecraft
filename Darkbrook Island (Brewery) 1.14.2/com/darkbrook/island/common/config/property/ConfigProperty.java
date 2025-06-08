package com.darkbrook.island.common.config.property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class ConfigProperty<T>
{
	
	public static class PropertyStringList extends ConfigProperty<List<String>>
	{

		public PropertyStringList(String propertyKey) 
		{
			super(propertyKey);
		}

		@Override
		protected List<String> onPropertyValueLoad(YamlConfiguration config, String key) 
		{
			return config.getStringList(key);
		}
		
	}
	
	public static class PropertyString extends ConfigProperty<String>
	{

		public PropertyString(String propertyKey) 
		{
			super(propertyKey);
		}

		@Override
		protected String onPropertyValueLoad(YamlConfiguration config, String key) 
		{
			return config.getString(key);
		}
		
	}
	
	public static class PropertyFloat extends ConfigProperty<Float>
	{

		public PropertyFloat(String propertyKey) 
		{
			super(propertyKey);
		}

		@Override
		protected Float onPropertyValueLoad(YamlConfiguration config, String key) 
		{
			return (float) config.getDouble(key);
		}
		
	}
	
	public static class PropertyLong extends ConfigProperty<Long>
	{
		
		public PropertyLong(String propertyKey) 
		{
			super(propertyKey);
		}

		@Override
		protected Long onPropertyValueLoad(YamlConfiguration config, String key) 
		{
			return config.getLong(key);
		}
		
	}

	private Map<String, T> propertyMapping;
	private PropertyConfig config;
	private String propertyKey;
	
	public ConfigProperty(String propertyKey)
	{
		propertyMapping = new HashMap<String, T>();
		this.propertyKey = propertyKey;
	}
	
	public T getPropertyValue(T defaultPropertyValue)
	{		
		if(!config.exists())
		{
			return defaultPropertyValue;
		}
		
		String masterKey = config.getMasterKey();
		
		if(!propertyMapping.containsKey(masterKey))
		{
			String key = masterKey + "." + propertyKey;
			propertyMapping.put(masterKey, config.contains(key) ? onPropertyValueLoad(config.getYamlConfiguration(), key) : defaultPropertyValue);
		}
		
		return propertyMapping.get(masterKey);
	}
	
	public void setPropertyValue(T propertyValue)
	{
		propertyMapping.put(config.getMasterKey(), propertyValue);
	}
	
	protected String getPropertyKey(PropertyConfig config)
	{
		this.config = config;
		return propertyKey;
	}
	
	protected void write()
	{
		propertyMapping.keySet().forEach(masterKey -> config.set(masterKey + "." + propertyKey, propertyMapping.get(masterKey)));
	}
	
	protected abstract T onPropertyValueLoad(YamlConfiguration config, String key);
	
}
