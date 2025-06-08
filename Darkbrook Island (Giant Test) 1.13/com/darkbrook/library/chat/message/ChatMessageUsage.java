package com.darkbrook.library.chat.message;

import org.bukkit.command.CommandSender;

import com.darkbrook.island.gameplay.visual.VisualRepository;

public class ChatMessageUsage extends ChatMessage
{
	
	public ChatMessageUsage(String command, String... usages)
	{		
		super(getUsageArray(command, usages));
	}
	
	@Override
	protected void sendMessage(CommandSender sender, String... message) 
	{
		if(message.length > 1)
		{
		
			for(int i = 1; i < message.length; i++)
			{
				sendMessage(sender, "{$red}Usage: /" + message[0] + " " + message[i]);
			}
			
		}
		else
		{
			sendMessage(sender, "{$red}Usage: /" + message[0]);
		}
		
		playSound(sender, VisualRepository.errorSound);
	}

	private static String[] getUsageArray(String command, String... usages)
	{
		
		if(usages != null)
		{
			String[] array = new String[usages.length + 1];
			array[0] = command;
			
			for(int i = 0; i < usages.length; i++)
			{
				array[i + 1] = usages[i];
			}
				
			return array;
		}
		else
		{
			return new String[] {command};
		}
		
	}
	
}
