package com.darkbrook.library.command.events;

import org.bukkit.command.BlockCommandSender;

public class BlockCommandEvent extends CommandEvent {
	
	public BlockCommandEvent(BlockCommandSender sender, String[] arguments) {
		super(sender, arguments);
	}

	@Override
	public BlockCommandSender getCommandSender() {
		return (BlockCommandSender) super.sender;
	}

}
