package com.darkbrook.minez.command.project;

import org.bukkit.command.BlockCommandSender;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.BlockCommandEvent;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.message.FormatMessage;

public class CommandProject extends CommandListener {
	
	public CommandProject() {
		super("project");
	}
	
	@CommandHandler
	public void onCommandBlock(BlockCommandEvent event) {
		
		BlockCommandSender sender = event.getCommandSender();
		
		if(event.getLength() == 1 || event.getLength() == 2) {
			
			Projection projection = new Projection(event.getArguments()[0], event.getLength() == 1 ? "nothing" : event.getArguments()[1]);
			boolean isProjecting = projection.project(sender.getBlock().getLocation());
			FormatMessage.fluid(sender, isProjecting, "Selection paste initiated.", "Project", "The specified projection could not be pasted.");
			
		} else {
			FormatMessage.usage(sender, "project", "<blocks> <effects>");
		}
		
	}

}
