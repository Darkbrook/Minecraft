package com.darkbrook.library.command.basic.bind;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;

public class CommandBind extends CommandListener {

	public CommandBind() {
		super("bind");
	}
	
	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
		String[] arguments = event.getArguments();
		
		if(arguments.length > 0) {
			
			ItemStack stack = player.getInventory().getItemInMainHand();
			
			if(stack != null && stack.getType() != Material.AIR) {
				
				String playerCommand = "";
				for(int i = 0; i < arguments.length; i++) playerCommand += i >= arguments.length - 1 ? arguments[i] : arguments[i] + " ";
				Bind.addCommand(player, playerCommand);
				
				FormatMessage.info(player, "Bound the command '" + playerCommand + "' to your hand.");
				
			} else {
				FormatMessage.error(player, MessageErrorType.HAND_ITEM);
			}
			
			return;
			
		}
		
		FormatMessage.usage(player, "bind <command...>");
		
	}

}
