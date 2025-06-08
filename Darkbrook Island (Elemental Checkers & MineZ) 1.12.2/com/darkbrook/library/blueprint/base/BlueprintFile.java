package com.darkbrook.library.blueprint.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.darkbrook.darkbrookisland.DarkbrookIsland;
import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.blueprint.AsyncBlueprintTask;
import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.selection.PlayerSelection;
import com.darkbrook.library.file.loggable.CachedLoggableEncode;
import com.darkbrook.library.file.loggable.LoggableDecode;
import com.darkbrook.library.file.loggable.LoggableEncode;
import com.darkbrook.library.file.loggable.LoggableFile;

public class BlueprintFile extends LoggableFile {

	private final BlueprintFile file = this;
	private final CachedLoggableEncode encode = new CachedLoggableEncode();
	private List<MemoryBlock> memoryBlocks;
	private String name;
	private boolean isLoaded;
	
	public BlueprintFile(String name) {
		super(DarkbrookIsland.getResourcePath() + "\\Blueprints\\" + name + ".bp");
		this.name = name;
		this.isLoaded = false;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}

	private List<MemoryBlock> getSortedBlocks(List<MemoryBlock> blocks, AsyncBlueprintTask task) {
		
		Collections.sort(blocks, new Comparator<MemoryBlock>() {

			@Override
			public int compare(MemoryBlock block0, MemoryBlock block1) {
				if(task != null) task.setValue(task.getValue() + 1);
				return block0.getType().compareTo(block1.getType());
			}
			
		});
		
		return blocks;
		
	}
	 
	protected void create(List<MemoryBlock> blocks, Location location) {

		String taskId = AsyncBlueprintTask.scheduleNewTask("Sorting Blocks", blocks.size());
		AsyncBlueprintTask task = AsyncBlueprintTask.getTask(taskId);
		
		List<MemoryBlock> blocksSorted = getSortedBlocks(blocks, task);
		task.setTaskName("Adding Locations To Memory");
		task.setValue(0);
		
		for(MemoryBlock block : blocksSorted) {
			task.setValue(task.getValue() + 1);
			addLocation(block.getBlockX() - location.getBlockX(), block.getBlockY() - location.getBlockY(), block.getBlockZ() - location.getBlockZ(), block.getType(), block.getData(), block.getExtras());
		}
		
		AsyncBlueprintTask.endTask(taskId);
	
	}
	
	protected void create(List<MemoryBlock> blocks) {
				
		super.create();
		
		String taskId = AsyncBlueprintTask.scheduleNewTask("Sorting Blocks", blocks.size());
		AsyncBlueprintTask task = AsyncBlueprintTask.getTask(taskId);
		
		List<MemoryBlock> blocksSorted = getSortedBlocks(blocks, task);
		task.setTaskName("Adding Locations To Memory");
		task.setValue(0);
		
		for(MemoryBlock block : blocksSorted) {
			task.setValue(task.getValue() + 1);
			addLocation(block.getBlockX(), block.getBlockY(), block.getBlockZ(), block.getType(), block.getData(), block.getExtras());
		}
		
		encode.writeToFile(file, task);	
	
	}
	
	protected void internalLoad(Blueprint blueprint) {
		super.create();
		this.memoryBlocks = getBlocks();
		this.isLoaded = true;
	}
	
	protected void internalMemoryLoad(Player player) {
		this.memoryBlocks = new ArrayList<MemoryBlock>();
		List<MemoryBlock> blocks = getSortedBlocks(PlayerSelection.getPlayerSelection(player).getSelection(true), null);
		Location location = player.getLocation();
		for(MemoryBlock block : blocks) memoryBlocks.add(new MemoryBlock(block.getType(), block.getData(), block.getBlockX() - location.getBlockX(), block.getBlockY() - location.getBlockY(), block.getBlockZ() - location.getBlockZ(), block.getExtras()));
		this.isLoaded = true;
	}
	
	protected List<MemoryBlock> getRawBlocks() {
		return memoryBlocks;
	}
	
	protected List<MemoryBlock> getBlocksWithOffset(Location offset) {
		List<MemoryBlock> blocks = new ArrayList<MemoryBlock>();
		for(MemoryBlock block : memoryBlocks) blocks.add(new MemoryBlock(block.getType(), block.getData(), block.getBlockX() + offset.getBlockX(), block.getBlockY() + offset.getBlockY(), block.getBlockZ() + offset.getBlockZ(), block.getExtras()));
		return blocks;
	}
	
	private void addLocation(int blockX, int blockY, int blockZ, Material type, int data, String[] extras) {
		LoggableEncode selection = encode.getLoggableEncode(type, data, extras);
		selection.addLocation(blockX, blockY, blockZ);
	}
	
	private List<MemoryBlock> getBlocks() {
		List<MemoryBlock> memoryBlocks = new ArrayList<MemoryBlock>();
		for(String data : super.read()) new LoggableDecode(data).addMemoryBlocks(memoryBlocks);
		return memoryBlocks;
	}

}
