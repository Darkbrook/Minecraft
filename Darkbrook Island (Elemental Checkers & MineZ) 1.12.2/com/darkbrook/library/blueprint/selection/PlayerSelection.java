package com.darkbrook.library.blueprint.selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.darkbrook.library.block.CommandBlock;
import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.blueprint.Blueprint;

public class PlayerSelection {
	
	private static final Map<Player, PlayerSelection> PLAYER_MAPPING = new HashMap<Player, PlayerSelection>();
	
	private Player player;
	private List<MemoryBlock> selection;
	private Blueprint clipboard;
	private Location position0;
	private Location position1;
	private boolean isNewSelection;
	
	public PlayerSelection(Player player) {
		this.player = player;
		this.isNewSelection = true;
		PLAYER_MAPPING.put(player, this);
	}
	
	public static PlayerSelection getPlayerSelection(Player player) {
		return PLAYER_MAPPING.containsKey(player) ? PLAYER_MAPPING.get(player) : new PlayerSelection(player);
	}
	
	public void setClipboard(Blueprint clipboard) {
		this.clipboard = clipboard;
	}

	public void setPosition0(Location position0) {
		this.position0 = position0;
		this.isNewSelection = true;
	}
	
	public void setPosition1(Location position1) {
		this.position1 = position1;
		this.isNewSelection = true;
	}
	
	public boolean hasSelection() {
		if(this.position0 == null || this.position1 == null) return false;
		return true;
	}
	
	public Location getPosition0() {
		return position0;
	}
	
	public Location getPosition1() {
		return position1;
	}
	
	@SuppressWarnings("deprecation")
	public List<MemoryBlock> getSelection(boolean updateSelectedBlocks) {
				
		if(!isNewSelection && !updateSelectedBlocks) return this.selection;
		
		List<MemoryBlock> selection = new ArrayList<MemoryBlock>();
		
		if(!hasSelection()) return selection;
		
		int position0BlockX = position0.getBlockX();
		int position0BlockY = position0.getBlockY();
		int position0BlockZ = position0.getBlockZ();
		
		int position1BlockX = position1.getBlockX();
		int position1BlockY = position1.getBlockY();
		int position1BlockZ = position1.getBlockZ();

		int minX = min(position0BlockX, position1BlockX);
		int maxX = max(position0BlockX, position1BlockX);
		
		int minY = min(position0BlockY, position1BlockY);
		int maxY = max(position0BlockY, position1BlockY);
		
		int minZ = min(position0BlockZ, position1BlockZ);
		int maxZ = max(position0BlockZ, position1BlockZ);
		
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				for(int z = minZ; z <= maxZ; z++) {
					Block block = new Location(player.getWorld(), x, y, z).getBlock();
					MemoryBlock selectedBlock = block.getType() == Material.COMMAND ? new MemoryBlock(block.getType(), block.getData(), x, y, z, new CommandBlock(block).getCommand()) : new MemoryBlock(block.getType(), block.getData(), x, y, z);
					selection.add(selectedBlock);
				}
			}
		}
		
		this.isNewSelection = false;
		this.selection = selection;
				
		return selection;
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Blueprint getClipboard() {
		return this.clipboard;
	}
	
	private int max(int a, int b) {
		return a == b ? a : (a > b ? a : b);
	}
	
	private int min(int a, int b) {
		return a == b ? a : (a < b ? a : b);
	}

}
