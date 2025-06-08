package com.darkbrook.kingdoms.common.io;

import java.util.Properties;
import java.util.function.Function;

public class PropertiesManager
{	
	protected Properties properties;
	
	public PropertiesManager()
	{
		this(new Properties());
	}
	
	public PropertiesManager(Properties properties)
	{
		this.properties = properties;
	}
	
	public Properties getProperties()
	{
		return properties;
	}
	
	public boolean getBoolean(Object property)
	{
		return getProperty(property, Boolean::parseBoolean);
	}
	
	public int getInt(Object property)
	{
		return getProperty(property, Integer::parseInt);
	}
	
	public <T> T getProperty(Object propery, Function<String, T> parseFunc)
	{
		return parseFunc.apply(getProperty(propery));
	}
	
	public String getProperty(Object propery)
	{
		return properties.getProperty(propery.toString());
	}
	
	public void setProperty(Object property, Object value)
	{
		properties.setProperty(property.toString(), value.toString());
	}
}
