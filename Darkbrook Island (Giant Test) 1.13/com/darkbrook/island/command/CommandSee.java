package com.darkbrook.island.command;

import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.message.ChatMessageInfo;
import com.darkbrook.library.gameplay.player.DarkbrookPlayer;

public class CommandSee extends Command 
{

	private static final PotionEffect potion = new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 0, false, false);
	
	public CommandSee() 
	{
		super("see");
	}
	
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{
			
		if(event.isValid(command, event.getArguments().length == 0))
		{
			DarkbrookPlayer cplayer = new DarkbrookPlayer( event.getPlayer());
			event.sendMessage(new ChatMessageInfo("See ", !cplayer.togglePotionEffect(potion) ? "enabled" : "disabled", "."));
		}

	}

}
