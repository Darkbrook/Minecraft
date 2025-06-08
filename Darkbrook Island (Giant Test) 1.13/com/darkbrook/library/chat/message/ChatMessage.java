package com.darkbrook.library.chat.message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.darkbrook.library.gameplay.visual.DarkbrookSound;

public abstract class ChatMessage 
{
	private String[] message;
	private boolean isMuted;

	public ChatMessage(String... message)
	{
		this.message = message;
	}
	
	public void mute()
	{
		isMuted = true;
	}

	public void sendMessage(CommandSender sender)
	{
		sendMessage(sender, message);
	}
	
	protected void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(new CustomMessage(message).toString());
	}
	
	protected void playSound(CommandSender sender, DarkbrookSound sound)
	{
		if(!isMuted && sender instanceof Player) sound.play((Player) sender, true);		
	}
	
	protected abstract void sendMessage(CommandSender sender, String... message);
}
