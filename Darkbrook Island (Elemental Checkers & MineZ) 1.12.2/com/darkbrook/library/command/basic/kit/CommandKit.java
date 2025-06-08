package com.darkbrook.library.command.basic.kit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;

public class CommandKit extends CommandListener {

	public CommandKit() {
		super("kit");
	}

	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {
				
		Player player = event.getCommandSender();
		String[] arguments = event.getArguments();
		
		if(arguments.length == 1) {
			
			String name = arguments[0];
			KitInventory inventory = new KitInventory(name);
			
			if(inventory.exists()) {
				inventory.give(player);
				FormatMessage.info(player, "Kit " + name + " has been added to your inventory.");
			} else {
				FormatMessage.error(player, "Kit", "Kit " + name + " does not exist.");
			}
			
			return;
			
		}
		
		if(arguments.length == 2) {
			
			if(arguments[0].equals("add")) {
				
				String name = arguments[1];
				KitInventory inventory = new KitInventory(player, name);
				inventory.add();
				FormatMessage.info(player, "Kit " + name + " added.");	
				
			} else if(arguments[0].equals("remove")) {
				
				String name = arguments[1];
				KitInventory inventory = new KitInventory(name);
				
				if(inventory.exists()) {
					inventory.remove();
					FormatMessage.info(player, "Kit " + name + " removed.");
				} else {
					FormatMessage.error(player, "Kit", "Kit " + name + " does not exist.");
				}
				
			} else {
				FormatMessage.usage(player, "kit", new String[]{"add <name>", "remove <name>"});
			}
			
			return;
			
		}
		
		if(arguments.length == 3) {
			
			if(arguments[0].equals("give")) {
				
				String name = arguments[2];
				KitInventory inventory = new KitInventory(name);
				
				if(inventory.exists()) {
					
					String playerFrom = player.getDisplayName();
					
					if(arguments[1].equals("@a")) {
						
						for(Player target : Bukkit.getOnlinePlayers()) {
							
							String playerTo = target.getDisplayName();
							
							inventory.give(target);
							
							if(player != target) {		
								FormatMessage.info(player, "Kit " + name + " has been given to " + playerTo + ".");
								FormatMessage.info(target, "Kit " + name + " has been added to your inventory by " + playerFrom + ".");
							} else {
								FormatMessage.info(player, "Kit " + name + " has been added to your inventory.");
							}
							
						}
						
					} else {
						
						Player target = Bukkit.getPlayer(arguments[1]);

						if(target != null) {
							
							String playerTo = target.getDisplayName();
							
							inventory.give(target);
							
							if(player != target) {		
								FormatMessage.info(player, "Kit " + name + " has been given to " + playerTo + ".");
								FormatMessage.info(target, "Kit " + name + " has been added to your inventory by " + playerFrom + ".");
							} else {
								FormatMessage.info(player, "Kit " + name + " has been added to your inventory.");
							}
							
						} else {
							FormatMessage.error(player, MessageErrorType.PLAYER);
						}
						
					}
					
				} else {
					FormatMessage.error(player, "Kit", "Kit " + name + " does not exist.");
				}
 				
			} else {
				FormatMessage.usage(player, "kit", "give <player> <kit>");
			}
			
			return;
			
		}
		
		FormatMessage.usage(player, "kit", new String[]{"<kit>", "add <name>", "remove <name>", "give <player> <kit>"});

	}

}
