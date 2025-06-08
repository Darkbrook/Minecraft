package com.darkbrook.island.internal.commands;

import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

abstract class CommandBase implements CommandExecutor {

	private String label;
	private String[] args;
	
	protected boolean isCommand(String command) {
		return label.equalsIgnoreCase(command);
	}
	
	protected boolean isArg(int arg, String command) {
		return args[arg].equalsIgnoreCase(command);
	}
	
	protected boolean isSize(int size) {
		return args.length == size;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		this.label = label;
		this.args = args;
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			onPlayer(player, player.getWorld(), label, args);
			if(player.isOp()) onOp(player, player.getWorld(), label, args);
		} else if(sender instanceof BlockCommandSender) {
			BlockCommandSender cmdblock = (BlockCommandSender) sender;
			onCommandBlock(cmdblock, cmdblock.getBlock().getWorld(), label, args);
		}
		
		return false;
		
	}

	abstract void onPlayer(Player player, World world, String command, String[] args);
	abstract void onOp(Player player, World world, String command, String[] args);
	abstract void onCommandBlock(BlockCommandSender cmdblock, World world, String command, String[] args);

}
