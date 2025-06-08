package com.darkbrook.island.mmo.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Cell {
	
	public ArrayList<Cell> cells;
	public HashMap<String, Cell> location;
	public int cellAmount;
	public int size;
	public int x;
	public int z;
	public boolean isSelected;
	public boolean isVisited;
	public CellWalls walls;

	public Cell(ArrayList<Cell> cells, HashMap<String, Cell> location, int cellAmount, int size, int x, int z) {
		this.cells = cells;
		this.location = location;
		this.cellAmount = cellAmount;
		this.size = size;
		this.x = x;
		this.z = z;
		this.isSelected = false;
		this.isVisited = false;
		this.walls = new CellWalls(true, true, true, true);
	}
	
	private boolean upVisited() {
		if(z - size < 0) return true;
		if(location.get(x + ", " + (z - size)).isVisited) return true;
		return false;
	}
	
	private boolean downVisited() {
		if(z + size >= cellAmount - 1) return true;
		if(location.get(x + ", " + (z + size)).isVisited) return true;
		return false;
	}
	
	private boolean leftVisited() {
		if(x - size < 0) return true;
		if(location.get((x - size) + ", " + z).isVisited) return true;
		return false;
	}
	
	private boolean rightVisited() {
		if(x + size >= cellAmount - 1) return true;
		if(location.get((x + size) + ", " + z).isVisited) return true;
		return false;
	}
	
	public boolean isStuck() {
		return upVisited() && downVisited() && leftVisited() && rightVisited();
	} 
	
	public Cell nextCell() {
		
		Random random = new Random();
		switch(random.nextInt(4)) {
			case 0: 
				
				if(z - size >= 0) {
					
					Cell cell = location.get(x + ", " + (z - size));
					if(cells.contains(cell) && !cell.isVisited) {
						walls.up = false;
						isSelected = false;
						cell.walls.down = false;
						cell.isSelected = true;				
						return cell;
					}
					
				} 
				
				break;
			case 1: 
				
				if(z + size <= cellAmount - 1) {

					Cell cell = location.get(x + ", " + (z + size));
					if(cells.contains(cell) && !cell.isVisited) {
						walls.down = false;
						isSelected = false;
						cell.walls.up = false;
						cell.isSelected = true;	
						return cell;
					}
					
				} 
				
				break;
			case 2: 
				
				if(x - size >= 0) {
					
					Cell cell = location.get((x - size) + ", " + z);
					if(cells.contains(cell) && !cell.isVisited) {
						walls.left = false;
						isSelected = false;
						cell.walls.right = false;
						cell.isSelected = true;		
						return cell;
					}
					
				} 
				
				break;
			case 3: 
				
				if(x + size <= cellAmount - 1) {
				
					Cell cell = location.get((x + size) + ", " + z);
					if(cells.contains(cell) && !cell.isVisited) {						
						walls.right = false;
						isSelected = false;
						cell.walls.left = false;
						cell.isSelected = true;	
						return cell;
					}
					
				} 
				
			break;
		}
		
		return nextCell();
		
	}

	public class CellWalls {
		
		public boolean up;
		public boolean down;
		public boolean left;
		public boolean right;

		public CellWalls(boolean up, boolean down, boolean left, boolean right) {
			this.up = up;
			this.down = down;
			this.left = left;
			this.right = right;
		}		
		
		public String getValue() {
			String s = "";
			s += walls.up ? "1" : "0";
			s += walls.down ? "1" : "0";
			s += walls.left ? "1" : "0";
			s += walls.right ? "1" : "0";
			return s;
		}
		
	}

}
