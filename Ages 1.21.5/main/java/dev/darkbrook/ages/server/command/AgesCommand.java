package dev.darkbrook.ages.server.command;

import com.mojang.brigadier.CommandDispatcher;

import dev.darkbrook.ages.Ages;
import dev.darkbrook.ages.common.AgeProgressionManager;
import dev.darkbrook.ages.common.lang.Texts;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class AgesCommand
{
	private static final AgeProgressionManager MANAGER = Ages.getInstance().getProgressionManager();
	private static final Text DELIMITER = Text.literal(", ");
	
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
	{
		dispatcher.register(CommandManager.literal("ages")
			.executes(context -> listAges(context.getSource()))
			.then(CommandManager.literal("reload")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> reloadAges(context.getSource()))));
	}
	
	public static int listAges(ServerCommandSource source)
	{
		source.sendFeedback(() -> Texts.join(DELIMITER, MANAGER.getAgeDisplayNames()), false);
		return 0;
	}
	
	private static int reloadAges(ServerCommandSource source)
	{
		if (MANAGER.load())
			source.sendFeedback(() -> Text.literal("Ages reloaded!"), true);
		else
			source.sendError(Text.literal("An issue occured while attempting to reload ages"));
		return 0;
	}
}
