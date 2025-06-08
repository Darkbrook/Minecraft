package com.darkbrook.library.command.basic.maze;

import org.bukkit.command.BlockCommandSender;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.BlockCommandEvent;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.location.LocationHandler;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;

public class CommandMaze extends CommandListener {

	public CommandMaze() {
		super("maze");
	}
	
	@CommandHandler
	public void onCommandBlock(BlockCommandEvent event) {
		
		BlockCommandSender block = event.getCommandSender();
		String[] arguments = event.getArguments();
		
		if(arguments.length == 2) {
			
			int sections = 0;
			int sectionSize = 0;
			
			try {
				sections = Integer.parseInt(arguments[0]);
				sectionSize = Integer.parseInt(arguments[1]);
			} catch (NumberFormatException e) {
				FormatMessage.error(block, MessageErrorType.INTEGER_MULTIPLE);
				return;
			}
			
			Maze.generateMaze(LocationHandler.getOffsetLocation(block.getBlock().getLocation(), 0, 2, 0), sections, sectionSize);
			FormatMessage.info(block, "Maze generated with " + sections + " " + sectionSize + " * " + sectionSize + " sections.");
			return;
			
		} else if(arguments.length == 3 && arguments[0].equals("clear")) {
			
			int sections = 0;
			int sectionSize = 0;
			
			try {
				sections = Integer.parseInt(arguments[1]);
				sectionSize = Integer.parseInt(arguments[2]);
			} catch (NumberFormatException e) {
				FormatMessage.error(block, MessageErrorType.INTEGER_MULTIPLE);
				return;
			}
			
			Maze.clearMaze(LocationHandler.getOffsetLocation(block.getBlock().getLocation(), 0, 2, 0), sections, sectionSize);
			FormatMessage.info(block, "Maze cleared with " + sections + " " + sectionSize + " * " + sectionSize + " sections.");
			return;
			
		}
		
		FormatMessage.usage(block, "maze", "<sections> <section size>", "clear <sections> <section size>");
		
	}

}
