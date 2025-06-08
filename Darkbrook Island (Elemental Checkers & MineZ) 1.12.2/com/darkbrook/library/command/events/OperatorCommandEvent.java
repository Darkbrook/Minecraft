package com.darkbrook.library.command.events;

import org.bukkit.entity.Player;

public class OperatorCommandEvent extends PlayerCommandEvent {

	public OperatorCommandEvent(Player player, String[] arguments) {
		super(player, arguments);
	}

}
