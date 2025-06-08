package com.darkbrook.library.block;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import com.darkbrook.library.location.LocationHandler;

import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.CommandBlockListenerAbstract;
import net.minecraft.server.v1_12_R1.TileEntityCommand;

public class CommandBlock {
	
	private static final Map<Location, Material> MATERIAL_REFERENCES = new HashMap<Location, Material>();
	private static final Map<Location, Byte> DATA_REFERENCES = new HashMap<Location, Byte>();

	private CommandBlockListenerAbstract command;
	private Location location;
	
	public CommandBlock(BlockCommandSender sender) {
		this(sender.getBlock().getLocation());
	}
	
	public CommandBlock(Block block) {
		this(block.getLocation());
	}
	
	public CommandBlock(Location location) {
		this.command = getTileEntityCommandFromLocation(location);
		this.location = location;
	}
	
	public String getCommand() {
		return command.getCommand();
	}
	
	public void setBlockAt(Location location) {
		location.getBlock().setType(Material.COMMAND);
		CommandBlockListenerAbstract command = getTileEntityCommandFromLocation(location);
		command.setCommand(this.command.getCommand());
	}
	
	public void setCommand(String commandIn) {
		command.setCommand(commandIn);
	}
	
	public void setOutputMessage(String output) {
		command.sendMessage(new ChatComponentText(output));
	}
	
	public void setSignalStrength(int strength) {
		
		command.a(strength);
		command.a(true);
		
		updateAround(strength, 2, 0, 0);
		updateAround(strength, -2, 0, 0);
		updateAround(strength, 0, 0, 2);
		updateAround(strength, 0, 0, -2);

	}
	
	public Location getLocation() {
		return location;
	}
	
	@SuppressWarnings("deprecation")
	private void updateAround(int strength, int xOffset, int yOffset, int zOffset) {
		
		Block block = LocationHandler.getOffsetLocation(location, xOffset, yOffset, zOffset).getBlock();

		MATERIAL_REFERENCES.put(block.getLocation(), block.getType());
		DATA_REFERENCES.put(block.getLocation(), block.getData());
		
		block.setType(Material.AIR);
		block.setData((byte) 0);
		
		block.setType(MATERIAL_REFERENCES.get(block.getLocation()));
		block.setData(DATA_REFERENCES.get(block.getLocation()));
		
		MATERIAL_REFERENCES.clear();
		DATA_REFERENCES.clear();
		
	}
	
	private CommandBlockListenerAbstract getTileEntityCommandFromLocation(Location location) {
		return((TileEntityCommand) ((CraftWorld) location.getWorld()).getTileEntityAt(location.getBlockX(), location.getBlockY(), location.getBlockZ())).getCommandBlock();
	}

}
