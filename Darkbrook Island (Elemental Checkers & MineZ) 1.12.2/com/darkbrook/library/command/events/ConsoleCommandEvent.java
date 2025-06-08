package com.darkbrook.library.command.events;

import org.bukkit.command.ConsoleCommandSender;

public class ConsoleCommandEvent extends CommandEvent {
	
	public ConsoleCommandEvent(ConsoleCommandSender sender, String[] arguments) {
		super(sender, arguments);
	}

	@Override
	public ConsoleCommandSender getCommandSender() {
		return (ConsoleCommandSender) super.sender;
	}
	
}
