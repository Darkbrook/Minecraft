package com.darkbrook.island.command;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.chat.message.ChatMessageInfo;

public class CommandIdentify extends Command 
{
	
	public CommandIdentify() 
	{
		super("identify");
	}
	
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{
			
		if(event.isValid(command, event.getArguments().length == 0))
		{

			ItemStack stack = event.getPlayer().getInventory().getItemInMainHand();
			
			if(stack != null)
			{
				int durability = stack.getDurability();
				event.sendMessage(new ChatMessageInfo(stack.getType().name().toLowerCase() + (durability != 0 ? ", " + durability : "")));
			}
			else
			{
				event.sendMessage(new ChatMessageError("You are not currently holding a valid item."));
			}
			
		}

	}

}
