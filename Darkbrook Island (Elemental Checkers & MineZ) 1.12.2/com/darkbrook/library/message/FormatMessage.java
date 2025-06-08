package com.darkbrook.library.message;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.darkbrook.library.compressed.CompressedSound;

public class FormatMessage {
	
	public static final CompressedSound INFO_SOUND = new CompressedSound(Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 0.5F, 2.0F);
	public static final ChatColor INFO_PREFIX = ChatColor.GOLD;
	public static final ChatColor INFO_MAIN = ChatColor.YELLOW;
	
	public static final CompressedSound ERROR_SOUND = new CompressedSound(Sound.ENTITY_ARROW_HIT, 0.5F, 2.0F);
	public static final ChatColor ERROR_PREFIX = ChatColor.DARK_RED;
	public static final ChatColor ERROR_MAIN = ChatColor.RED;

	public static String getShutdownMessage() {
		return INFO_PREFIX + "" + ChatColor.BOLD + "    *" + INFO_MAIN + "Server Shutting Down" + INFO_PREFIX + "*\n" + INFO_PREFIX + "" + ChatColor.BOLD + "            *" + INFO_MAIN + "This is either the result of a restart or due to perceived inactivity" + INFO_PREFIX + "*";
	}
	
	public static String getBanMessage(String banReason) {
		return ERROR_PREFIX + "" + ChatColor.BOLD + "    *" + ERROR_MAIN + "You have been perminantly banned from the server." + ERROR_PREFIX + "*\n" + ERROR_MAIN + "Reason: " + banReason;
	}
	
	public static void message(CommandSender sender, String message, ChatColor prefix, ChatColor main, CompressedSound sound) {
		sender.sendMessage(prefix + "" + ChatColor.BOLD + ">     *" + main + message + prefix + "*");
		if(sound != null && sender instanceof Player) sound.play((Player) sender, true);
	}
	
	public static void info(CommandSender sender, String info) {
		message(sender, info, INFO_PREFIX, INFO_MAIN, INFO_SOUND);
	}
	
	public static void error(CommandSender sender, String error, String data) {
		message(sender, error + " Error: " + data, ERROR_PREFIX, ERROR_MAIN, ERROR_SOUND);
	}
	
	public static void error(CommandSender sender, MessageErrorType type) {
		error(sender, type.getError(), type.getData());
	}
	
	public static void fluid(CommandSender sender, boolean isInfo, String info, String error, String data) {
		if(isInfo) info(sender, info); else error(sender, error, data);
	}
	
	public static void fluid(CommandSender sender, boolean isInfo, String info, MessageErrorType type) {
		if(isInfo) info(sender, info); else error(sender, type);
	}
	
	public static void usage(CommandSender sender, String prefix, String usage) {
		message(sender, "Usage: /" + prefix + " " + usage, ERROR_PREFIX, ERROR_MAIN, ERROR_SOUND);
	}
	
	public static void usage(CommandSender sender, String prefix, String... usages) {
		for(String usage : usages) message(sender, "Usage: /" + prefix + " " + usage, ERROR_PREFIX, ERROR_MAIN, null);
		if(sender instanceof Player) ERROR_SOUND.play((Player) sender, true);
	}
	
}
