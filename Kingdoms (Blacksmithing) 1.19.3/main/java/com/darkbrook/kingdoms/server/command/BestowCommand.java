package com.darkbrook.kingdoms.server.command;

import static net.minecraft.server.command.CommandManager.literal;

import com.darkbrook.kingdoms.server.inventory.BestowScreenHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BestowCommand
{
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
	{
		dispatcher.register(literal("bestow").requires(source -> source.hasPermissionLevel(2) && source
				.isExecutedByPlayer()).executes(context -> 
				{
					context.getSource().getPlayer().openHandledScreen(new SimpleNamedScreenHandlerFactory((id,
							inventory, viewer) -> new BestowScreenHandler(id, inventory), Text.literal(
									"Kingdoms Items")));
					return Command.SINGLE_SUCCESS;
				}));
	}
}
