package com.darkbrook.kingdoms.server.command;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;

public class KingdomsCommandManager
{
	public static void register()
	{
		CommandRegistrationCallback.EVENT.register(KingdomsCommandManager::register);
	}
	
	private static void register(CommandDispatcher<ServerCommandSource> dispatcher,
			CommandRegistryAccess registryAccess, RegistrationEnvironment environment)
	{
		BestowCommand.register(dispatcher);
		GameRulesCommand.register(dispatcher);
	}
}
