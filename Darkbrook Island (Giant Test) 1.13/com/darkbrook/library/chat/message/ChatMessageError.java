package com.darkbrook.library.chat.message;

import org.bukkit.command.CommandSender;

import com.darkbrook.island.gameplay.visual.VisualRepository;
import com.darkbrook.library.util.string.StringBuilderWrapper;

public class ChatMessageError extends ChatMessage
{
	
	public ChatMessageError(String... message)
	{
		super(message);
	}
	
	@Override
	protected void sendMessage(CommandSender sender, String... message) 
	{
		StringBuilderWrapper builder = new StringBuilderWrapper();
		
		builder.append("{$red}");
		builder.append(message);

		sendMessage(sender, builder.toString());
		playSound(sender, VisualRepository.errorSound);
	}

}

