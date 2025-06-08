package com.darkbrook.library.chat.message;

import org.bukkit.command.CommandSender;

import com.darkbrook.island.gameplay.visual.VisualRepository;
import com.darkbrook.library.util.string.IStringCondition;
import com.darkbrook.library.util.string.StringBuilderWrapper;

public class ChatMessageInfo extends ChatMessage implements IStringCondition
{

	public ChatMessageInfo(String... message)
	{
		super(message);
	}

	@Override
	protected void sendMessage(CommandSender sender, String... message) 
	{
		StringBuilderWrapper builder = new StringBuilderWrapper();
		builder.append(this, message);
			
		sendMessage(sender, builder.toString());
		playSound(sender, VisualRepository.messageSound);			
	}

	@Override
	public String onModify(String string, int index, boolean isLastString)
	{
		return (index % 2 == 0 ? "{$yellow}" : "{$gold}") + string;
	}

}
