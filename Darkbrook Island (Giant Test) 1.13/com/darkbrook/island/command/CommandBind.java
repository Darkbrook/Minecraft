package com.darkbrook.island.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.CommandVanilla;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.chat.message.ChatMessageInfo;
import com.darkbrook.library.gameplay.itemstack.DarkbrookItemStack;
import com.darkbrook.library.util.string.IStringCondition;
import com.darkbrook.library.util.string.StringBuilderWrapper;

import net.md_5.bungee.api.ChatColor;

public class CommandBind extends Command 
{

	public CommandBind() 
	{
		super("bind");
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		
		if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.getHand() == EquipmentSlot.HAND) 
		{
			
			Player player = event.getPlayer();
			
			DarkbrookItemStack stack = new DarkbrookItemStack(player.getInventory().getItemInMainHand()).openNbt();
			
			if(stack.hasKey("bind"))
			{
				CommandVanilla.execute(player, stack.getString("bind"));
			}

		}
		
	}
	
	@EventHandler
	public void onPlayerCommand(CommandEventPlayer event) 
	{
				
		if(event.isValid(command, true))
		{
			
			Player player = event.getPlayer();
			String[] arguments = event.getArguments();

			ItemStack stack = player.getInventory().getItemInMainHand();
			
			if(stack.getType() != Material.AIR) 
			{
				DarkbrookItemStack dstack = new DarkbrookItemStack(stack);
				String command = getCommand(arguments, player.getLocation());
				
				dstack.openMeta();
				dstack.setName(ChatColor.GOLD + dstack.getBaseName() + " of Binding");
				dstack.setLore(ChatColor.DARK_GRAY + "Bound: " + ChatColor.GRAY + "/" + command);
				dstack.applyMeta();
				
				dstack.openNbt().setString("bind", command);
				player.getInventory().setItemInMainHand(dstack.applyNbt());
							
				event.sendMessage(new ChatMessageInfo("Bound the command ", command, " to your hand item."));
			} 
			else 
			{
				event.sendMessage(new ChatMessageError("You currently do not have a valid item in your hand."));
			}
			
		}
		
	}
	
	private String getCommand(String[] arguments, Location location)
	{
		StringBuilderWrapper builder = new StringBuilderWrapper();
		String position = (location.getBlockX() + 0.5d) + " " + location.getBlockY() + " " + (location.getBlockZ() + 0.5d);

		builder.append(new IStringCondition()
		{

			@Override
			public String onModify(String string, int index, boolean isLastString)
			{
				return string.replace("{$here}", position) + (!isLastString ? " " : "");
			}
			
		}, arguments);
		
		return builder.toString();
	}

}