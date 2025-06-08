package com.darkbrook.library.file.loggable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import com.darkbrook.library.block.MemoryBlock;

public class LoggableDecode {
 	
	private List<Location> locations;
	private List<String> extras;
	private Material type;
	private int data;
	private boolean hasExtras;
	
	public LoggableDecode(String syntaxData) {
		
		String key = getKey(syntaxData);
		
		this.hasExtras = key.contains("[") && key.contains("]");
		this.type = getType(key);
		this.data = getData(type.name().toLowerCase(), key, hasExtras);
		if(hasExtras) this.extras = getExtras(type.name().toLowerCase() + "," + data + ",[", key);	
	
		String value = getValue(syntaxData);
		this.locations = getLocations(value);
				
	}
	
	public void addMemoryBlocks(List<MemoryBlock> memoryBlocks) {
		
		for(Location location : locations) {
			memoryBlocks.add(this.getMemoryBlock(location));						
		}

	}
	
	public MemoryBlock getMemoryBlock(Location location) {
		return new MemoryBlock(type, data, location.getBlockX(), location.getBlockY(), location.getBlockZ(), hasExtras ? extras.get(0) : "");
	}
	
	public String getExtra(int extra) {
		return extras.get(extra);
	}
	
	public boolean hasExtras() {
		return hasExtras;
	}

	public Material getType() {
		return type;
	}

	public int getData() {
		return data;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	
	private String getKey(String syntaxData) {
		return syntaxData.substring(1, syntaxData.indexOf("}="));
	}
	
	private Material getType(String key) {
		return Material.valueOf(key.substring(0, key.indexOf(",")).toUpperCase());
	}
	
	private int getData(String prefix, String key, boolean hasExtras) {
		return Integer.parseInt(key.substring((prefix.length() + 1), (hasExtras ? key.indexOf(",[") : key.length())));
	}
	
	private List<String> getExtras(String prefix, String key) {
		List<String> extras = new ArrayList<String>();
		key = key.substring(prefix.length(), key.length() - 1);
		String[] values = key.split(",");
		for(String value : values) extras.add(value);
		return extras;
	}
	
	private String getValue(String syntaxData) {
		return syntaxData.substring(syntaxData.indexOf("}={") + 3, syntaxData.length() - 1);
	}
	
	private List<Location> getLocations(String values) {
		List<Location> locations = new ArrayList<Location>();
		addLocations(locations, values);
		return locations;
	}
	
	private void addLocations(List<Location> locations, String values) {

		String[] locationsArray = values.substring(1, values.length() - 1).split("\\],\\[");
		
		for(String locationString : locationsArray) {
			
			String[] locationArray = locationString.split(",");
			
			int blockX = Integer.parseInt(locationArray[0]);
			int blockY = Integer.parseInt(locationArray[1]);
			int blockZ = Integer.parseInt(locationArray[2]);
			
			locations.add(new Location(null, blockX, blockY, blockZ));

		}
		
 	}
		
}
