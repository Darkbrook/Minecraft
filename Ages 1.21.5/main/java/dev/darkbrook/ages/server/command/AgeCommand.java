package dev.darkbrook.ages.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import dev.darkbrook.ages.Ages;
import dev.darkbrook.ages.common.Age;
import dev.darkbrook.ages.common.AgeProgressionManager;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class AgeCommand
{
	private static final AgeProgressionManager MANAGER = Ages.getInstance().getProgressionManager();
	
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
	{
		dispatcher.register(CommandManager.literal("age")
			.executes(context -> printCurrentAge(context.getSource()))
			.then(CommandManager.literal("list")
				.executes(context -> AgesCommand.listAges(context.getSource())))
			.then(CommandManager.literal("advance")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> advanceToNextAge(context.getSource())))
			.then(CommandManager.literal("reset")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> setCurrentAge(context.getSource(), MANAGER.getStartingAge())))
			.then(CommandManager.literal("set")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.argument("age", StringArgumentType.string())
					.suggests((context, builder) -> CommandSource.suggestMatching(MANAGER.getAgeIds(), builder))
					.executes(context -> setCurrentAge(context.getSource(), StringArgumentType.getString(context, "age"))))));
	}

	private static int printCurrentAge(ServerCommandSource source)
	{
		source.sendFeedback(() -> MANAGER.getCurrentAge().displayName(), false);
		return 0;
	}

	private static int advanceToNextAge(ServerCommandSource source)
	{
		Age lastAge = MANAGER.getCurrentAge();
		
		if (MANAGER.advanceToNextAge(source.getServer()))
			source.sendFeedback(() -> Text.literal("Advanced from ")
				.append(lastAge.displayName())
				.append(" to ")
				.append(MANAGER.getCurrentAge().displayName()), true);
		else
			source.sendError(Text.literal("Can not advance past the final age"));
		
		return 0;
	}

	private static int setCurrentAge(ServerCommandSource source, String id)
	{
		MANAGER.getAge(id)
			.ifPresentOrElse(age -> setCurrentAge(source, age), 
				() -> source.sendError(Text.literal("Unknown age id: " + id)));
		return 0;
	}
	
	private static int setCurrentAge(ServerCommandSource source, Age newAge)
	{
		Age age = MANAGER.getCurrentAge();
		if (newAge == age)
			return 0;
		
		MANAGER.setCurrentAge(source.getServer(), newAge);
		source.sendFeedback(() -> Text.literal("Moved from ")
			.append(age.displayName())
			.append(" to ")
			.append(newAge.displayName()), true);
		return 0;
	}
}
