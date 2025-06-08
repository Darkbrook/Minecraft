package com.darkbrook.library.gameplay.blueprint;

import org.bukkit.event.EventHandler;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.chat.message.ChatMessageInfo;
import com.darkbrook.library.gameplay.blueprint.selection.player.PlayerSelectionMapping;

public class CommandBp extends Command 
{
	
	public CommandBp() 
	{
		super("bp");
	}
	
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{
			
		if(event.isValid(command, event.getArguments().length == 1, "copy"))
		{
			
			if(PlayerSelectionMapping.getPlayerSelection(event.getPlayer()).updateCanvas())
			{
				event.sendMessage(new ChatMessageInfo("Your selection has been copied to your canvas."));
			}
			else
			{
				event.sendMessage(new ChatMessageError("Your selection is invalid."));
			}
			
		}
		
	}
	
}
