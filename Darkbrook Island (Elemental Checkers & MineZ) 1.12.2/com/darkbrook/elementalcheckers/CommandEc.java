package com.darkbrook.elementalcheckers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;

import net.md_5.bungee.api.ChatColor;

public class CommandEc extends CommandListener {

	public CommandEc() {
		super("ec");
	}
	
	@CommandHandler
	public void onOppedPlayer(OperatorCommandEvent event) {
		
		Player player = event.getCommandSender();
		String[] arguments = event.getArguments();
		int length = event.getLength();
		
		if(length == 1) {
		
			Player target = Bukkit.getServer().getPlayer(arguments[0]);
			
			if(arguments[0].equals("replay")) {
				
				if(ElementalCheckers.lastGame != null) {
					ElementalCheckers.lastGame.play(player);
				} else {
					player.sendMessage(ChatColor.RED + "There is no available replay.");
				}
				
			} else if(target != null && target != player) {
				
				if(!ElementalCheckers.GAMES.containsKey(target)) {
					ElementalCheckers ec = new ElementalCheckers(player, target);
					ec.init();
				} else {
					player.sendMessage(ChatColor.RED + target.getName() + " is already in a game.");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "Invalid player.");
			}
			
		} else if(length == 2) {
			
			Player target = Bukkit.getServer().getPlayer(arguments[1]);
		
			if(arguments[0].equals("spectate")) {
				
				if(ElementalCheckers.GAMES.containsKey(target)) {
					
					ElementalCheckers ec = ElementalCheckers.GAMES.get(target);
					
					if(ec.isGame) {
						ec.addSpectator(player, ec.player1.player == target ? ec.player1 : ec.player2);
					} else {
						player.sendMessage(ChatColor.RED + target.getName() + "'s game has not started yet.");
					}
					
				} else {
					player.sendMessage(ChatColor.RED + target.getName() + " is not in a game.");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "Invalid player.");
			}
			
				
		} else {
			player.sendMessage(ChatColor.RED + "Usage: /ec <name>");
			player.sendMessage(ChatColor.RED + "Usage: /ec replay");
			player.sendMessage(ChatColor.RED + "Usage: /ec spectate <name>");
		}
	
	}

}
