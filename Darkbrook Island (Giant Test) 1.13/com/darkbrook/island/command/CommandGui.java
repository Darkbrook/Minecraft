package com.darkbrook.island.command;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.darkbrook.island.gameplay.gui.GuiDefault;
import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.gameplay.gui.Gui;
import com.darkbrook.library.util.ResourceLocation;

public class CommandGui extends Command 
{
	
	private Class<? extends Gui> clazz;
	private boolean acceptsWorld;
	
	public CommandGui(String command, Class<? extends Gui> clazz, boolean acceptsWorld) 
	{
		super(command);
		this.clazz = clazz;
		this.acceptsWorld = acceptsWorld;
	}
	
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{
		String[] arguments = event.getArguments();
		
		if(command.equals("gui"))
		{
			if(!event.isValid(command, arguments.length == 1, "<name>"))
			{
				return;
			}
						
			if(new ResourceLocation("$data/gui/" + arguments[0] + "-gui.txt").getFile().exists())
			{
	
				try 
				{
					Gui gui = new GuiDefault(arguments[0]);
					gui.open(event.getPlayer());
					return;
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}

			event.sendMessage(new ChatMessageError("The gui you specified does not exist."));
		}
		else if(event.isValid(command, arguments.length == 0))
		{
			
			Player player = event.getPlayer();
			
			try 
			{
				Gui gui = acceptsWorld ? clazz.getDeclaredConstructor(World.class).newInstance(player.getWorld()) : clazz.newInstance();
				gui.open(player);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				event.sendMessage(new ChatMessageError("The gui you specified does not exist."));
			}
			
		}
		
	}

}
