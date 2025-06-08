package com.darkbrook.library.command.basic.world;

import org.bukkit.entity.Player;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;

public class CommandWorld extends CommandListener {

	public CommandWorld() {
		super("world");
	}
	
	@CommandHandler
	public void onOppedPlayer(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
		
		if(event.getLength() == 0) {
			GuiWorld gui = new GuiWorld(WorldLoader.getWorldFiles(), player.getWorld());
			gui.open(player);
		} else {
			FormatMessage.usage(player, "world", "");
		}
		
	}

}
