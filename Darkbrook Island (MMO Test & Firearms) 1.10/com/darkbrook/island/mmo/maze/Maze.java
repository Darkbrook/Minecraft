package com.darkbrook.island.mmo.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.bukkit.Location;
import org.bukkit.Material;

import com.darkbrook.island.library.misc.LocationHandler;
import com.darkbrook.island.library.misc.MathHandler;

public class Maze {
	
	private static boolean allVisited(ArrayList<Cell> cells) {
		for(Cell cell : cells) if(!cell.isVisited) return false;
		return true;
	}
	
	private static void generateMaze(ArrayList<Cell> cells, Stack<Cell> path) {
		for(Cell cell : cells) {
			if(cell.isSelected) {
				cell.isVisited = true;
				if(cell.isStuck()) {
					cell.isSelected = false;
					path.pop().isSelected = true;
				} else {
					path.push(cell.nextCell());
				}
			}
		}
	}
	
	private static ArrayList<CellData> generateMap(int cellAmount, int cellSize) {
		
		ArrayList<CellData> cellsData = new ArrayList<CellData>();
		ArrayList<Cell> cells = new ArrayList<Cell>();	
		HashMap<String, Cell> location = new HashMap<String, Cell>();
		Stack<Cell> path = new Stack<Cell>();
		cellAmount *= cellSize;
		
		for(int x = 0; x <= cellAmount - 1; x+= cellSize) {
			for(int z = 0; z <= cellAmount - 1; z+= cellSize) {
				Cell cell = new Cell(cells, location, cellAmount, cellSize, x, z);
				cells.add(cell);		
				location.put(x  + ", " + z, cell);
			}
		}
		
		cells.get(0).isSelected = true;
		while(!allVisited(cells)) generateMaze(cells, path);
		for(Cell cell : cells) cellsData.add(new CellData(cell.x, cell.z, cell.walls.getValue()));		
		return cellsData;
		
	}
	
	private static Material getRandomWallMaterial() {
		return MathHandler.RANDOM.nextFloat() > 0.5F ? Material.SMOOTH_BRICK : (MathHandler.RANDOM.nextFloat() > 0.5F ? Material.COBBLESTONE : (MathHandler.RANDOM.nextFloat() > 0.2F ? Material.GRAVEL : Material.AIR));
	}
	
	public static void generateClearing(Location location, int cellAmount, int cellSize) {
		for(int x = 0; x < (cellAmount * cellSize); x++) for(int y = 0; y < 7; y++) for(int z = 0; z < (cellAmount * cellSize); z++) LocationHandler.getLocationOffset(location, x, y, z).getBlock().setType(Material.AIR);	
	}
	
	private static void generatePath(Location location, int cellAmount, int cellSize) {
	
		ArrayList<CellData> celldata = Maze.generateMap(cellAmount, cellSize);
		
		for(CellData cd : celldata) {

			int xSmall = cd.xOffset / cellSize;
			int zSmall = cd.zOffset / cellSize;
			if(xSmall == 0 && zSmall == 0) cd.binaryValue = "0" + cd.binaryValue.substring(1, 4);
			if(xSmall == (cellAmount - 1) && zSmall == (cellAmount - 1)) cd.binaryValue = cd.binaryValue.substring(0, 1) + "0" + cd.binaryValue.substring(2, 4);
			
			for(int y = 0; y < 7; y++) {

				LocationHandler.getLocationOffset(location, cd.xOffset + (cellSize - 1), y, cd.zOffset + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());		
				LocationHandler.getLocationOffset(location, cd.xOffset, y, cd.zOffset + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());					
				LocationHandler.getLocationOffset(location, cd.xOffset + (cellSize - 1), y, cd.zOffset).getBlock().setType(getRandomWallMaterial());					
				LocationHandler.getLocationOffset(location, cd.xOffset, y, cd.zOffset).getBlock().setType(getRandomWallMaterial());

				if(cd.binaryValue.substring(0, 1).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getLocationOffset(location, cd.xOffset - i + (cellSize - 1), y, cd.zOffset).getBlock().setType(getRandomWallMaterial());
				if(cd.binaryValue.substring(1, 2).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getLocationOffset(location, cd.xOffset - i + (cellSize - 1), y, cd.zOffset + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());
				if(cd.binaryValue.substring(2, 3).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getLocationOffset(location, cd.xOffset, y, cd.zOffset - i + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());
				if(cd.binaryValue.substring(3, 4).equals("1")) for(int i = 1; i < 8; i++) LocationHandler.getLocationOffset(location, cd.xOffset + (cellSize - 1), y, cd.zOffset - i + (cellSize - 1)).getBlock().setType(getRandomWallMaterial());						
			
			}				
			
		}
		
	}
	
	private static void generateLighting(Location location, int cellAmount, int cellSize, float chance) {
		
		for(int x = 0; x < (cellAmount * cellSize); x++) {
			
			for(int z = 0; z < (cellAmount * cellSize); z++) {
								
				if(MathHandler.RANDOM.nextFloat() >= chance && (z % cellSize) == (cellSize - 1)) {
					
					if(LocationHandler.getLocationOffset(location, x + 1, 0, z).getBlock().getType() != Material.AIR || 
					   LocationHandler.getLocationOffset(location, x - 1, 0, z).getBlock().getType() != Material.AIR ||
					   LocationHandler.getLocationOffset(location, x, 0, z + 1).getBlock().getType() != Material.AIR ||
					   LocationHandler.getLocationOffset(location, x, 0, z - 1).getBlock().getType() != Material.AIR) {
						
						if(LocationHandler.getLocationOffset(location, x, 1, z).getBlock().getType() == Material.AIR &&
						   LocationHandler.getLocationOffset(location, x + 1, 0, z).getBlock().getType() != Material.FENCE && 
						   LocationHandler.getLocationOffset(location, x - 1, 0, z).getBlock().getType() != Material.FENCE &&
						   LocationHandler.getLocationOffset(location, x, 0, z + 1).getBlock().getType() != Material.FENCE &&
						   LocationHandler.getLocationOffset(location, x, 0, z - 1).getBlock().getType() != Material.FENCE) {
							
							LocationHandler.getLocationOffset(location, x, 0, z).getBlock().setType(Material.FENCE);
							LocationHandler.getLocationOffset(location, x, 1, z).getBlock().setType(Material.REDSTONE_TORCH_ON);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public static void generateMaze(Location location, int cellAmount, int cellSize) {
		generateClearing(location, cellAmount, cellSize);
		generatePath(location, cellAmount, cellSize);		
		generateLighting(location, cellAmount, cellSize, 0.5F);
	}
	
}
