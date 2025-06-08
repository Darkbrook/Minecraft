package com.darkbrook.library.command.events;

import org.bukkit.entity.Player;

public class PlayerCommandEvent extends CommandEvent {
	
	public PlayerCommandEvent(Player player, String[] arguments) {
		super(player, arguments);
	}

	@Override
	public Player getCommandSender() {
		return (Player) super.sender;
	}

}
