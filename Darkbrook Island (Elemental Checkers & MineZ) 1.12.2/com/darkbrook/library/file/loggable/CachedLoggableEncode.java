package com.darkbrook.library.file.loggable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import com.darkbrook.library.blueprint.AsyncBlueprintTask;

public class CachedLoggableEncode {

	private Map<String, LoggableEncode> encodeMapping;
	private List<String> encodeListing;

	public CachedLoggableEncode() {
		encodeMapping = new HashMap<String, LoggableEncode>();
		encodeListing = new ArrayList<String>();
	}
	
	public void writeToFile(LoggableFile file, AsyncBlueprintTask task) {
		
		task.setTaskName("Writing To File");
		
		for(String key : encodeListing) {
			LoggableEncode encode = encodeMapping.get(key);
			task.setValue(task.getValue() + 1);
			file.setString(encode.getSyntaxKey(), encode.getSyntaxData());
		}
		
		AsyncBlueprintTask.endTask(task.getTaskId());
		
	}
	
	public LoggableEncode getLoggableEncode(Material type, int data, String... extras) {
		LoggableEncode encode = new LoggableEncode(type, data, extras);
		String key = encode.getSyntaxKey();
		addMapping(key, encode);
		return encodeMapping.get(key);
	}
	
	public LoggableEncode getValue(String key) {
		return encodeMapping.get(key);
	}
	
	public List<String> getKeyList() {
		return new ArrayList<String>(encodeListing);
	}
	
	private void addMapping(String key, LoggableEncode encode) {
		if(encodeMapping.containsKey(key)) return;
		encodeMapping.put(key, encode);
		encodeListing.add(key);
	}
	 
}
