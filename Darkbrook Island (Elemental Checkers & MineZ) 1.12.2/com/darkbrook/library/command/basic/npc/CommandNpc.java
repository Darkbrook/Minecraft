package com.darkbrook.library.command.basic.npc;

import org.bukkit.entity.Player;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;

public class CommandNpc extends CommandListener {

	public CommandNpc() {
		super("npc");
	}
	
	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
		String[] arguments = event.getArguments();
		
		if(arguments.length == 1 && arguments[0].equals("remove")) {
		
			Npc npc = Npc.getClosestNpc(player);
			
			if(npc != null) {
				Npc.removeClosestNpc(player);
				FormatMessage.info(player, "Npc '" + npc.getName() + "' removed.");
			} else {
				FormatMessage.error(player, "Npc", "There are no loaded Npcs in your area.");
			}
			
			return;

		} else if(arguments.length == 2 && arguments[0].equals("spawn")) {
		
			Npc npc = new Npc(player.getWorld(), arguments[1]);
			npc.spawn(player);
			
			FormatMessage.info(player, "Npc '" + arguments[1] + "' spawned at your position.");
			return;
			
		}
	
		FormatMessage.usage(player, "npc", new String[] {"spawn <name>", "remove"});
		
	}

}
