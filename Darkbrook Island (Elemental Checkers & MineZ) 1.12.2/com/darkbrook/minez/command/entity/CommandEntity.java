package com.darkbrook.minez.command.entity;

import org.bukkit.command.BlockCommandSender;

import com.darkbrook.library.block.CommandBlock;
import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.BlockCommandEvent;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.message.FormatMessage;

public class CommandEntity extends CommandListener {

	public CommandEntity() {
		super("entity");
	}
	
	@CommandHandler
	public void onCommandBlock(BlockCommandEvent event) {
		
		BlockCommandSender sender = event.getCommandSender();
		CommandBlock block = new CommandBlock(sender);
		
		if(event.getLength() == 1) {
			
			EntityTracker tracker = new EntityTracker(block, event.getArguments()[0]);
			boolean isSpawned = tracker.spawnEntity();
			FormatMessage.fluid(sender, isSpawned, "Entity tracking initiated.", "EntityTracker", "The specified entity could not be spawned.");
			
		} else {
			FormatMessage.usage(sender, "entity", "<entity>,<x>,<y>,<z>,<status>");
		}
				
	}

}
