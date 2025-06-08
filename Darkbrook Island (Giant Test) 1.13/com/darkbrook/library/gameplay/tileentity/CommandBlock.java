package com.darkbrook.library.gameplay.tileentity;

import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;

import net.minecraft.server.v1_13_R1.ChatComponentText;
import net.minecraft.server.v1_13_R1.CommandBlockListenerAbstract;
import net.minecraft.server.v1_13_R1.TileEntity;
import net.minecraft.server.v1_13_R1.TileEntityCommand;


public class CommandBlock extends TileEntityWrapper<CommandBlockListenerAbstract>
{
		
	public CommandBlock(BlockCommandSender sender) 
	{
		this(sender.getBlock());
	}
	
	public CommandBlock(Block block) 
	{
		super(block.getLocation());
	}
	
	public void setOutputMessage(String output) 
	{
		entity.sendMessage(new ChatComponentText(output));
	}
	
	public String getCommand() 
	{
		return entity.getCommand();
	}
	
	public void setCommand(String command)
	{
		entity.setCommand(command);
	}

	@Override
	protected CommandBlockListenerAbstract loadTileEntity(TileEntity entity) 
	{
		return ((TileEntityCommand) entity).getCommandBlock();
	}

}
