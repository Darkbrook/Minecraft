package com.darkbrook.library.command.basic.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import com.darkbrook.library.location.LocationHandler;
import com.darkbrook.library.misc.MathHandler;

public class Maze {
	
	public static void generateMaze(Location location, int cellAmount, int cellSize) {
		generateClearing(location, cellAmount, cellSize);
		generateFloor(location, cellAmount, cellSize);
		generatePath(location, cellAmount, cellSize);		
		generateLighting(location, cellAmount, cellSize, 0.5F);
	}
	
	public static void clearMaze(Location location, int cellAmount, int cellSize) {
		generateClearing(location, cellAmount, cellSize);
	}
	
	
	private static void generateMaze(List<MazeSection> cells, Stack<MazeSection> path) {
		
		for(MazeSection cell : cells) {
			
			if(!cell.isSelected) {
				continue;
			}
				
			cell.isVisited = true;
			
			if(cell.isStuck()) {
				cell.isSelected = false;
				path.pop().isSelected = true;
			} else {
				path.push(cell.nextCell());
			}
			
		}

	}
	
	private static List<MazeSectionData> generateMap(int cellAmount, int cellSize) {
		
		List<MazeSectionData> cellsData = new ArrayList<MazeSectionData>();
		List<MazeSection> cells = new ArrayList<MazeSection>();	
		Map<String, MazeSection> location = new HashMap<String, MazeSection>();
		Stack<MazeSection> path = new Stack<MazeSection>();
		cellAmount *= cellSize;
		
		for(int x = 0; x <= cellAmount - 1; x+= cellSize) {
			
			for(int z = 0; z <= cellAmount - 1; z+= cellSize) {
				MazeSection cell = new MazeSection(cells, location, cellAmount, cellSize, x, z);
				cells.add(cell);		
				location.put(x  + ", " + z, cell);
			}
			
		}
		
		cells.get(0).isSelected = true;
		while(!allVisited(cells)) generateMaze(cells, path);
		for(MazeSection cell : cells) cellsData.add(new MazeSectionData(cell.x, cell.z, cell.walls.getValue()));		
		return cellsData;
		
	}
	
	private static Material getRandomWallMaterial() {
		return MathHandler.RANDOM.nextFloat() > 0.5F ? Material.SMOOTH_BRICK : (MathHandler.RANDOM.nextFloat() > 0.5F ? Material.COBBLESTONE : (MathHandler.RANDOM.nextFloat() > 0.2F ? Material.GRAVEL : Material.AIR));
	}
	
	private static boolean allVisited(List<MazeSection> cells) {
		for(MazeSection cell : cells) if(!cell.isVisited) return false;
		return true;
	}
	
	private static void generateClearing(Location location, int cellAmount, int cellSize) {
		
		for(int x = 0; x < (cellAmount * cellSize); x++){
			
			for(int y = 0; y < 7; y++) {
				
				for(int z = 0; z < (cellAmount * cellSize); z++){
					
					Location offset = LocationHandler.getOffsetLocation(location, x, y, z);
					offset.getBlock().setType(Material.AIR);	
					
					for(Entity entity : offset.getWorld().getNearbyEntities(offset, 2, 2, 2)) {
						if(!(entity instanceof Item)) continue;
						Item item = (Item) entity;
						if(item.getItemStack().getType() == Material.REDSTONE_TORCH_ON) item.remove();
					}
					
				}
				
			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private static void generateFloor(Location location, int cellAmount, int cellSize) {
		
		int y = (int) (location.getY() - 4);
		
		for(int x = 0; x < (cellAmount * cellSize); x++) {
			
			for(int z = 0; z < (cellAmount * cellSize); z++) {
				Block block = LocationHandler.getOffsetLocation(location, x, y, z).getBlock();
				block.setType(Material.WOOD);
				block.setData((byte) 1);
			}
			
		}
		
	}
		
	private static void generatePath(Location location, int cellAmount, int cellSize) {
	
		List<MazeSectionData> celldata = Maze.generateMap(cellAmount, cellSize);
		
		for(MazeSectionData cd : celldata) {

			int xSmall = cd.xOffset / cellSize;
			int zSmall = cd.zOffset / cellSize;
			if(xSmall == 0 && zSmall == 0) cd.binaryValue = "0" + cd.binaryValue.substring(1, 4);
			if(xSmall == (cellAmount - 1) && zSmall == (cellAmount - 1)) cd.binaryValue = cd.binaryValue.substring(0, 1) + "0" + cd.binaryValue.substring(2, 4);
			
			for(int y = 0; y < 7; y++) {

				LocationHandler.getOffsetLocation(location, cd.xOffset + (cellSize - 1), y, cd.zOffset + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());		
				LocationHandler.getOffsetLocation(location, cd.xOffset, y, cd.zOffset + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());					
				LocationHandler.getOffsetLocation(location, cd.xOffset + (cellSize - 1), y, cd.zOffset).getBlock().setType(getRandomWallMaterial());					
				LocationHandler.getOffsetLocation(location, cd.xOffset, y, cd.zOffset).getBlock().setType(getRandomWallMaterial());

				if(cd.binaryValue.substring(0, 1).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getOffsetLocation(location, cd.xOffset - i + (cellSize - 1), y, cd.zOffset).getBlock().setType(getRandomWallMaterial());
				if(cd.binaryValue.substring(1, 2).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getOffsetLocation(location, cd.xOffset - i + (cellSize - 1), y, cd.zOffset + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());
				if(cd.binaryValue.substring(2, 3).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getOffsetLocation(location, cd.xOffset, y, cd.zOffset - i + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());
				if(cd.binaryValue.substring(3, 4).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getOffsetLocation(location, cd.xOffset + (cellSize - 1), y, cd.zOffset - i + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());						
			
			}				
			
		}
		
	}
	
	private static void generateLighting(Location location, int cellAmount, int cellSize, float chance) {
		
		for(int x = 0; x < (cellAmount * cellSize); x++) {
			
			for(int z = 0; z < (cellAmount * cellSize); z++) {
								
				if(MathHandler.RANDOM.nextFloat() >= chance && (z % cellSize) == (cellSize - 1)) {
					
					if(LocationHandler.getOffsetLocation(location, x + 1, 0, z).getBlock().getType() != Material.AIR || 
					   LocationHandler.getOffsetLocation(location, x - 1, 0, z).getBlock().getType() != Material.AIR ||
					   LocationHandler.getOffsetLocation(location, x, 0, z + 1).getBlock().getType() != Material.AIR ||
					   LocationHandler.getOffsetLocation(location, x, 0, z - 1).getBlock().getType() != Material.AIR) {
						
						if(LocationHandler.getOffsetLocation(location, x, 1, z).getBlock().getType() == Material.AIR &&
						   LocationHandler.getOffsetLocation(location, x + 1, 0, z).getBlock().getType() != Material.FENCE && 
						   LocationHandler.getOffsetLocation(location, x - 1, 0, z).getBlock().getType() != Material.FENCE &&
						   LocationHandler.getOffsetLocation(location, x, 0, z + 1).getBlock().getType() != Material.FENCE &&
						   LocationHandler.getOffsetLocation(location, x, 0, z - 1).getBlock().getType() != Material.FENCE) {
							
							LocationHandler.getOffsetLocation(location, x, 0, z).getBlock().setType(Material.FENCE);
							LocationHandler.getOffsetLocation(location, x, 1, z).getBlock().setType(Material.REDSTONE_TORCH_ON);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
