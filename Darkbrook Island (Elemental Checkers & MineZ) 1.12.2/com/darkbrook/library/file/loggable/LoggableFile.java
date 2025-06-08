package com.darkbrook.library.file.loggable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.darkbrook.library.file.MalleableFile;

public class LoggableFile extends MalleableFile {

	private Map<String, String> mapping;
	
	public LoggableFile(String path) {
		super(path);
		mapping = new HashMap<String, String>();
		if(!super.exists()) return;
		List<String> datas = read();
		for(String data : datas) if(data.contains("=")) mapping.put(data.substring(0, data.indexOf("=")), data.substring(data.indexOf("=") + 1));
	}
	
	@Override
	public void create() {
		super.create();
	}
	
	public boolean hasKey(String key) {
		return mapping.containsKey(key);
	}
	
	public void setString(String key, String value) {
		set(key, value);
	}
	
	public void setInteger(String key, int value) {
		set(key, value + "");
	}
	
	public void setDouble(String key, double value) {
		set(key, value + "D");
	}
	
	public void setFloat(String key, float value) {
		set(key, value + "F");
	}
	
	public void setBoolean(String key, boolean value) {
		set(key, value ? "true" : "false");
	}
	
	public String getString(String key) {
		return get(key);
	}
	
	public int getInteger(String key) {
		return Integer.parseInt(get(key));
	}
	
	public double getDouble(String key) {
		return Double.parseDouble(get(key));
	}
	
	public float getFloat(String key) {
		return Float.parseFloat(get(key));
	}
	
	public boolean getBoolean(String key) {
		return get(key).equals("true");
	}
	
	private void set(String key, String value) {
		
		mapping.put(key, value);
		
		List<String> datas = read();
		List<String> datasCopy = new ArrayList<String>();
		boolean replace = false;
		
		for(String data : datas) {
			
			if(!replace && data.startsWith(key + "=")) {
				replace = true;
				datasCopy.add(key + "=" + value);
			} 
			
			if(!data.startsWith(key + "="))datasCopy.add(data);
			
		}
		
		if(replace) replaceWith(datasCopy, true); else write(key + "=" + value, true);	
				
	}
	
	private String get(String key) {
		return mapping.get(key);
	}
	
}
