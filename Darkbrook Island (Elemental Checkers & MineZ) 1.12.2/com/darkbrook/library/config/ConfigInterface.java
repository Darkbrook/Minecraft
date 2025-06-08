package com.darkbrook.library.config;

public interface ConfigInterface {
	
	public void create();
	public void save();
	
	public boolean containsKey(String key);
	public void addDefaults(String key, Object value);
	
	public Object getValue(String key);
	public void setValue(String key, Object value);
	
	public String getString(String key);
	public void setString(String key, String value);
	
	public float getFloat(String key);
	public void setFloat(String key, float value);
	
	public double getDouble(String key);
	public void setDouble(String key, double value);
	
	public long getLong(String key);
	public void setLong(String key, long value);
	
	public int getInteger(String key);
	public void setInteger(String key, int value);
	
	public byte getByte(String key);
	public void setByte(String key, byte value);
	
	public boolean getBoolean(String key);
	public void setBoolean(String key, boolean value);
	
}
