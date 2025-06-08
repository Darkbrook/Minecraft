package com.darkbrook.library.command.basic.shutdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.BlockCommandEvent;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.ConsoleCommandEvent;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;

public class CommandStop extends CommandListener {

	public CommandStop() {
		super("stop");
	}
	
	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {
		executeCleanShutdown(event.getArguments());
	}

	@CommandHandler
	public void onCommandBlock(BlockCommandEvent event) {
		executeCleanShutdown(event.getArguments());
	}
	
	@CommandHandler
	public void onConsole(ConsoleCommandEvent event) {
		executeCleanShutdown(event.getArguments());
	}

	private void executeCleanShutdown(String[] arguments) {
						
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(FormatMessage.getShutdownMessage());
		}
		
		Bukkit.shutdown();
		
	}

}
