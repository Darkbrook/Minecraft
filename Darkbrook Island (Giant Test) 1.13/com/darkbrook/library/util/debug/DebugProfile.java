package com.darkbrook.library.util.debug;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.darkbrook.library.chat.message.ChatMessageInfo;

public class DebugProfile
{

	private String section;
	private long ms;
	private int index;
	
	public DebugProfile()
	{
		section = "";
		ms = -1;
		index = 65;
	}
	
	public void section(String section)
	{
		this.section += "-" + section;
	}
	
	public void log(String section)
	{
		section(section);
		log();
	}
		
	public void log()
	{
		sendMessage("Debug", String.valueOf((char) index++) + section);		
	}
	
	public void ms()
	{
		long now = System.currentTimeMillis();
		
		if(ms != -1)
		{
			sendMessage("Millisecond", String.valueOf((char) index++) + section + "-" + (now - ms));
		}
		
		ms = System.currentTimeMillis();
	}
	
	private void sendMessage(String type, String value)
	{
		
		ChatMessageInfo message = new ChatMessageInfo(type + " condition ", value, " has been reached.");
		message.mute();
		
		for(Player player : Bukkit.getOnlinePlayers()) if(player.isOp())
		{
			message.sendMessage(player);
		}
		
	}
	
}
