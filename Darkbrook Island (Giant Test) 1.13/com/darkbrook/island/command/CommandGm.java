package com.darkbrook.island.command;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.command.event.CommandEventTarget;
import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.chat.message.ChatMessageInfo;
import com.darkbrook.library.util.helper.math.MathHelper;

public class CommandGm extends Command 
{

	public CommandGm() 
	{
		super("gm");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{
		
		String[] arguments = event.getArguments();

		if(event.isValid(command, arguments.length == 1 || arguments.length == 2 || (arguments.length == 3 && arguments[0].equals("toggle")), "<gamemode>", "<gamemode> <name>", "toggle <gamemode> <gamemode>"))
		{
			
			if(arguments.length == 1 || arguments.length == 2)
			{
				
				CommandEventTarget target = event.getCommandEventTarget(1, 2);
				int mode = MathHelper.parseUnsignedInt(arguments[0]);
				
				if(!isValidGameMode(event, mode))
				{
					return;
				}
				
				if(target.getReceiver() != null)
				{					
					if(!target.isSelfSend())
					{
						event.sendMessage(new ChatMessageInfo("Updated ", target.getReceiver().getName() + "'s", " gamemode to state ", String.valueOf(mode), "."));
					}
					
					updateGameMode(event, target.getReceiver(), mode);
				}
				else
				{
					event.sendMessage(new ChatMessageError("The player you specified is currently not online."));
				}
			
			}
			else
			{
				
				int modeA = MathHelper.parseUnsignedInt(arguments[1]);
				int modeB = MathHelper.parseUnsignedInt(arguments[2]);

				if(isValidGameMode(event, modeA) && isValidGameMode(event, modeB))
				{
					Player player = event.getPlayer();
					int mode = player.getGameMode().getValue();					
					updateGameMode(event, player, mode == modeA || (mode != modeA && mode != modeB) ? modeB : modeA);
				}

			}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void updateGameMode(CommandEventPlayer event, Player player, int mode)
	{
		player.setGameMode(GameMode.getByValue(mode));
		event.sendMessage(new ChatMessageInfo("Gamemode updated to state ", String.valueOf(mode), "."));
	}
	
	private boolean isValidGameMode(CommandEventPlayer event, int mode)
	{
		if(mode < 0 || mode > 3)
		{
			event.sendMessage(new ChatMessageError("The gamemode you specified is not valid."));
			return false;
		}
		
		return true;
	}

}
