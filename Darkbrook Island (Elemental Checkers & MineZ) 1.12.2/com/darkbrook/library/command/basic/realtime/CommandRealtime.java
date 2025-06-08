package com.darkbrook.library.command.basic.realtime;

import org.bukkit.entity.Player;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;

public class CommandRealtime extends CommandListener {

	public CommandRealtime() {
		super("realtime");
	}

	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
				
		if(event.getLength() == 0) {
			
			TimeHandler.isRealTime = !TimeHandler.isRealTime;
			FormatMessage.info(player, TimeHandler.isRealTime ? "The server is currently using a 'real time to Minecraft time' conversion." : "The server is now using the default Minecraft time system.");
			
		} else {
			FormatMessage.usage(player, "realtime", "");
		}
		
	}
	
}
