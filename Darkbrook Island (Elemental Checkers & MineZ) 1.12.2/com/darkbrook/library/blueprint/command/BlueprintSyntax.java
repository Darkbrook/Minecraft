package com.darkbrook.library.blueprint.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.location.LocationHandler;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class BlueprintSyntax {
	
	private static final Map<String, Boolean> BOOLEAN_MAPPING = new HashMap<String, Boolean>();;
	
	protected static void addBoolean(String key, boolean value) {
		BOOLEAN_MAPPING.put(key, value);
	}
	
	protected static boolean isBooleanTrue(String key) {
		return BOOLEAN_MAPPING.containsKey(key) && BOOLEAN_MAPPING.get(key);
	}
	
	protected static Location getLocation(String xIn, String yIn, String zIn) {
		
		int x = 0;
		int y = 0;
		int z = 0;

		try {
			x = Integer.parseInt(xIn);
			y = Integer.parseInt(yIn);
			z = Integer.parseInt(zIn);		
		} catch (NumberFormatException e) {
			return null;
		}
		
		return new Location(null, x, y, z);
		
	}
	
	@SuppressWarnings("deprecation")
	protected static boolean delay(Location location, String xIn, String yIn, String zIn) {
		
		Location paste = getLocation(location, xIn, yIn, zIn);
		
		if(paste == null) return false;
		
		Block block = paste.getBlock();
		MemoryBlock replace = new MemoryBlock(block.getType(), block.getData());
		
		block.setType(Material.REDSTONE_BLOCK);
		block.setData((byte) 0);
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				replace.setBlockAt(paste);
				if(block.getType() == Material.REDSTONE_BLOCK) block.setType(Material.AIR);
			}
		
		}, 12);
		
		return true;
		
	}
	
	@SuppressWarnings("deprecation")
	protected static boolean delayWithDelay(Location location, String xIn, String yIn, String zIn, String delayIn) {
		
		Location paste = getLocation(location, xIn, yIn, zIn);
		int delay = getDelay(delayIn);
		
		if(paste == null || delay < 0) return false;
		
		Block block = paste.getBlock();
		MemoryBlock replace = new MemoryBlock(block.getType(), block.getData());
		
		if(delay == 0) {
			
			block.setType(Material.REDSTONE_BLOCK);
			block.setData((byte) 0);
			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					replace.setBlockAt(paste);
					if(block.getType() == Material.REDSTONE_BLOCK) block.setType(Material.AIR);
				}
			
			}, 12);
			
		} else {
			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					
					block.setType(Material.REDSTONE_BLOCK);
					block.setData((byte) 0);
					
					UpdateHandler.delay(new UpdateListener() {

						@Override
						public void onUpdate() {
							replace.setBlockAt(paste);
							if(block.getType() == Material.REDSTONE_BLOCK) block.setType(Material.AIR);
						}
					
					}, 12);
					
				}
			
			}, delay);
			
		}
		
		return true;
		
	}
	
	protected static boolean paste(Location location, String xIn, String yIn, String zIn, String blueprintIn) {
		Blueprint blueprint = getBlueprint(location, xIn, yIn, zIn, blueprintIn);
		if(blueprint == null) return false;
		blueprint.paste(blueprint.getLocation(), false);
		return true;
	}
	
	protected static boolean pasteWithDelay(Location location, String xIn, String yIn, String zIn, String blueprintIn, String delayIn) {
		
		Blueprint blueprint = getBlueprint(location, xIn, yIn, zIn, blueprintIn);
		int delay = getDelay(delayIn);
		if(blueprint == null || delay < 0) return false;
		
		if(delay == 0) {
			blueprint.paste(blueprint.getLocation(), false);
		} else {
			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					blueprint.paste(blueprint.getLocation(), false);					
				}
				
			}, delay);
			
		}
		
		return true;
		
	}
		
	protected static boolean paste(Location location, Material type, int data, String xIn, String yIn, String zIn, String blueprintIn) {
		Blueprint blueprint = getBlueprint(location, xIn, yIn, zIn, blueprintIn);
		if(blueprint == null) return false;
		blueprint.paste(blueprint.getLocation(), type, data, false);
		return true;
	}

	protected static boolean pasteWithDelay(Location location, Material type, int data, String xIn, String yIn, String zIn, String blueprintIn, String delayIn) {
		
		Blueprint blueprint = getBlueprint(location, xIn, yIn, zIn, blueprintIn);
		int delay = getDelay(delayIn);

		if(blueprint == null || delay < 0) return false;
		
		if(delay == 0) {
			blueprint.paste(blueprint.getLocation(), type, data, false);
		} else {
			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					blueprint.paste(blueprint.getLocation(), type, data, false);					
				}
				
			}, delay);
			
		}
		
		return true;
		
	}
	
	@SuppressWarnings("deprecation")
	protected static boolean pasteBlock(Location location, String xIn, String yIn, String zIn, String typeIn, String dataIn) {
		
		Location paste = getLocation(location, xIn, yIn, zIn);
		MemoryBlock blockData = getMemoryBlock(typeIn, dataIn);
		if(paste == null || blockData == null) return false;
		
		Block block = paste.getBlock();
		block.setType(blockData.getType());
		block.setData((byte) blockData.getData());
		
		return true;
		
	}
	
	protected static boolean pasteBlockWithDelay(Location location, String xIn, String yIn, String zIn, String typeIn, String dataIn, String delayIn) {
					
		Location paste = getLocation(location, xIn, yIn, zIn);
		MemoryBlock blockData = getMemoryBlock(typeIn, dataIn);
		int delay = getDelay(delayIn);
		
		if(paste == null || blockData == null || delay < 0) return false;
		
		if(delay == 0) {
			blockData.setBlockAt(paste);
		} else {
			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					blockData.setBlockAt(paste);
				}
				
			}, delay);
			
		}
			
		return true;
		
	}
	
	private static MemoryBlock getMemoryBlock(String typeIn, String dataIn) {
		
		Material type = null;
		int data = 0;
		
		try {
			type = Material.valueOf(typeIn.toUpperCase());
			data = Integer.parseInt(dataIn);
		} catch (NumberFormatException | NullPointerException e) {
			return null;
		}
		
		return new MemoryBlock(type, data);
		
	}

	private static Location getLocation(Location location, String xIn, String yIn, String zIn) {
		
		int x = 0;
		int y = 0;
		int z = 0;

		try {
			x = Integer.parseInt(xIn);
			y = Integer.parseInt(yIn);
			z = Integer.parseInt(zIn);		
		} catch (NumberFormatException e) {
			return null;
		}
		
		Blueprint.playBlueprintEffects(LocationHandler.getOffsetLocation(location, x, y, z));
		return LocationHandler.getOffsetLocation(location, x, y, z);
		
	}
	
	private static int getDelay(String delayIn) {
		
		int delay = 0;
		
		try {
			delay = Integer.parseInt(delayIn);
		} catch (NumberFormatException e) {
			return -1;
		}
		
		return delay;
		
	}
	
	private static Blueprint getBlueprint(Location location, String xIn, String yIn, String zIn, String blueprintIn) {
				
		Location paste = getLocation(location, xIn, yIn, zIn);
		if(paste == null) return null;
				
		Blueprint blueprint = new Blueprint(blueprintIn);
		if(!blueprint.exists()) return null;
				
		blueprint.load();
		blueprint.setLocation(paste);
				
		return blueprint;
		
	}
	
}
