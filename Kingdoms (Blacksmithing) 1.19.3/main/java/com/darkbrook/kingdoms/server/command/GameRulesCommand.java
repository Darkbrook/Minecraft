package com.darkbrook.kingdoms.server.command;

import static net.minecraft.server.command.CommandManager.literal;

import com.darkbrook.kingdoms.common.io.GameRulesProperties;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

public class GameRulesCommand
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
	{
		dispatcher.register(literal("gamerules").requires(source -> source.hasPermissionLevel(4)).then(literal("sync")
				.executes(context ->
				{
					ServerCommandSource source = context.getSource();
					GameRulesProperties.reload();
					source.getWorld().getGameRules().setAllValues(new GameRules(), source.getServer());
					source.sendFeedback(Text.literal("World gamerules synced with gamerules.properties"), true);
					return Command.SINGLE_SUCCESS;
				})));
	}
}
