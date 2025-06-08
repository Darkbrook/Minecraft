package com.darkbrook.library.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.darkbrook.library.command.events.BlockCommandEvent;
import com.darkbrook.library.command.events.CommandEvent;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.ConsoleCommandEvent;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.command.events.PlayerCommandEvent;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;

public class CommandListener implements CommandExecutor {

	public static void sendConsoleCommand(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
	
	public static String getLocationString(Location location) {
		return location.getX() + " " + location.getY() + " " + location.getZ();
	}
	
	private Map<Class<? extends CommandEvent>, Method> methodMapping;
	private String prefix;	
	
	public CommandListener(String prefix) {
		this.methodMapping = getCommandHandlers();
		this.prefix = prefix;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String prefix, String[] arguments) {
		
		if(methodMapping.isEmpty() || !prefix.equalsIgnoreCase(this.prefix)) {
			return false;
		}
		
		Method method = null;
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			method = methodMapping.get(player.isOp() ? OperatorCommandEvent.class : PlayerCommandEvent.class);
			
			try {
				method.invoke(this, player.isOp() ? new OperatorCommandEvent(player, arguments) : new PlayerCommandEvent(player, arguments));
			} catch (NullPointerException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				FormatMessage.error(player, MessageErrorType.PERMISSION);
			}			
			
		} else if(sender instanceof BlockCommandSender) {
			
			BlockCommandSender block = (BlockCommandSender) sender;
			method = methodMapping.get(BlockCommandEvent.class);
			
			try {
				method.invoke(this, new BlockCommandEvent(block, arguments));
			} catch (NullPointerException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				FormatMessage.error(block, MessageErrorType.PERMISSION);
			}
			
		} else {
			
			ConsoleCommandSender console = (ConsoleCommandSender) sender;
			method = methodMapping.get(ConsoleCommandEvent.class);
			
			try {
				method.invoke(this, new ConsoleCommandEvent(console, arguments));
			} catch (NullPointerException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				FormatMessage.error(console, MessageErrorType.PERMISSION);
			}
			
		}
		
		return false;
		
	}
	
	public String getCommandPrefix() {
		return prefix;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Class<? extends CommandEvent>, Method> getCommandHandlers() {
		
		Method[] methods = this.getClass().getMethods();
		Map<Class<? extends CommandEvent>, Method> methodMapping = new HashMap<Class<? extends CommandEvent>, Method>();
		
		for(Method method : methods) {
			
			if(!method.isAnnotationPresent(CommandHandler.class) || method.getParameterTypes().length != 1) {
				continue;
			}

			methodMapping.put((Class<? extends CommandEvent>) method.getParameterTypes()[0], method);
			
		}
		
		return methodMapping;
		
	}

}
