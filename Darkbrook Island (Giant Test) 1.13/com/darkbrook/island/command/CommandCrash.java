package com.darkbrook.island.command;

import org.bukkit.event.EventHandler;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.command.event.CommandEventTarget;
import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.chat.message.ChatMessageInfo;
import com.darkbrook.library.gameplay.player.DarkbrookPlayer;

public class CommandCrash extends Command 
{
	
	public CommandCrash() 
	{
		super("crash");
	}
	
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{

		String[] arguments = event.getArguments();
		
		if(event.isValid(command, arguments.length == 0 || arguments.length == 1, "", "<name>"))
		{
			
			CommandEventTarget target = event.getCommandEventTarget(0, 1);
						
			if(target.getReceiver() != null)
			{
				
				DarkbrookPlayer player = new DarkbrookPlayer(target.getReceiver());
				player.crash();
				
				if(!target.isSelfSend())
				{
					event.sendMessage(new ChatMessageInfo("You have crashed ", "" + target.getReceiver().getName(), "."));
				}
				
			}
			else
			{
				event.sendMessage(new ChatMessageError("The player you specified is currently not online."));
			}
			
		}

	}

}
