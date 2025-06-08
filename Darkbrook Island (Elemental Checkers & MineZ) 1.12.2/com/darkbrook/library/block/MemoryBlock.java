package com.darkbrook.library.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class MemoryBlock {

	private List<String> extras;
	private Material type;
	private int data;
	private int blockX;
	private int blockY;
	private int blockZ;
	 
	public MemoryBlock(Material type, int data) {
		this.extras = new ArrayList<String>();
		this.type = type;
		this.data = data;
	}
	
	public MemoryBlock(Material type, int data, int blockX, int blockY, int blockZ, String... extras) {
		this.extras = new ArrayList<String>();
		this.type = type;
		this.data = data;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		if(extras == null || extras.length == 0) return;
		for(String extra : extras) this.extras.add(extra);
	}
	
	@SuppressWarnings("deprecation")
	public void setBlockAt(Location location) {
		Block block = location.getBlock();
		block.setType(type);
		block.setData((byte) data);
		if(type != Material.COMMAND) return;
		CommandBlock commandBlock = new CommandBlock(block);
		commandBlock.setCommand(extras.get(0));
		commandBlock.setBlockAt(location);
	}
	
	public void addExtras(String... extras) {
		for(String extra : extras) this.extras.add(extra);
	}
	
	public void setType(Material type) {
		this.type = type;
	}
	
	public void setData(int data) {
		this.data = data;
	}

	public Material getType() {
		return type;
	}

	public int getData() {
		return data;
	}	
	
	public int getBlockX() {
		return blockX;
	}
	
	public int getBlockY() {
		return blockY;
	}
	
	public int getBlockZ() {
		return blockZ;
	}
	
	public String[] getExtras() {
		String[] strings = new String[extras.size()];
		for(int i = 0; i < strings.length; i++) strings[i] = extras.get(i);
		return strings; 
	}
	
}
