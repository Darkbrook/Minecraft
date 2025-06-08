package com.darkbrook.library.blueprint.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Player;

import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.selection.BlockSelectionHandler;
import com.darkbrook.library.blueprint.selection.PlayerSelection;
import com.darkbrook.library.blueprint.task.base.BlueprintTask;
import com.darkbrook.library.blueprint.task.file.BlueprintTaskFileBounce;
import com.darkbrook.library.blueprint.task.file.BlueprintTaskFileMove;
import com.darkbrook.library.blueprint.task.tasks.BlueprintTaskBounce;
import com.darkbrook.library.blueprint.task.tasks.BlueprintTaskMove;
import com.darkbrook.library.command.CommandHelpMenu;
import com.darkbrook.library.command.CommandListener;
import com.darkbrook.library.command.events.BlockCommandEvent;
import com.darkbrook.library.command.events.CommandHandler;
import com.darkbrook.library.command.events.OperatorCommandEvent;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;
import com.darkbrook.library.misc.MathHandler;

public class CommandBlueprint extends CommandListener {

	private CommandHelpMenu helpMenu;
	
	public CommandBlueprint() {
		
		super("bp");
		
		helpMenu = new CommandHelpMenu("",
									   ChatColor.YELLOW + "" + ChatColor.BOLD + "---------" + ChatColor.GOLD + " Blueprint Help (${page_number}/${page_numbers}) " + ChatColor.YELLOW + "" + ChatColor.BOLD + "-----------------", 
									   ChatColor.DARK_GREEN + "/bp ${command}: " + ChatColor.GREEN  + "${description}",
									   ChatColor.DARK_GREEN + "/bp ${command}: " + ChatColor.GREEN  + "${description}",
									   ChatColor.DARK_GREEN + "/bp ${command}: " + ChatColor.GREEN  + "${description}",
									   ChatColor.DARK_GREEN + "/bp ${command}: " + ChatColor.GREEN  + "${description}",
									   ChatColor.YELLOW + "" + ChatColor.BOLD + "------------------------------------------",
									   "");
		
		helpMenu.addEntry("offset", "Finds the offset of your selected positions.");
		helpMenu.addEntry("copy", "Copies your current selection to your clipboard.");
		helpMenu.addEntry("cut", "Copies your current selection to your clipboard and turns the selected region to air.");
		helpMenu.addEntry("paste", "Pastes your your clipboard.");
		helpMenu.addEntry("help <number>", "Lists all the blueprint commands with a small description.");
		helpMenu.addEntry("save <name>", "Saves your clipboard to a blueprint file.");
		helpMenu.addEntry("load <name>", "Loads a blueprint file to your clipboard.");

		helpMenu.addEntry("taskmove <name> <direction> <count> <delay>", "Creates a move task that can be used to manipulate blueprints.");
		helpMenu.addEntry("taskbounce <name> <direction> <count> <delay> <pauses>", "Creates a bounce task that can be used to manipulate blueprints.");

		helpMenu.addEntry("boolean <key> <value>", "Saves a true or false value in memory binded to the specified key.");

		helpMenu.addEntry("delay <x> <y> <z>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block.");
		helpMenu.addEntry("delay <x> <y> <z> <delay>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block after a delay.");

		helpMenu.addEntry("delayif <x> <y> <z> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if true.");
		helpMenu.addEntry("delayif <x> <y> <z> <delay> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if true after a delay.");
		helpMenu.addEntry("!delayif <x> <y> <z> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if false.");
		helpMenu.addEntry("!delayif <x> <y> <z> <delay> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if false after a delay.");
		
		helpMenu.addEntry("delayifcompare <x> <y> <z> <key> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if true.");
		helpMenu.addEntry("delayifcompare <x> <y> <z> <delay> <key> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if true after a delay.");
		helpMenu.addEntry("!delayifcompare <x> <y> <z> <key> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if false.");
		helpMenu.addEntry("!delayifcompare <x> <y> <z> <delay> <key> <key>", "Pastes a redstone block at the specified offset and then replaces it with the locations prior block if false after a delay.");
		
		helpMenu.addEntry("paste <x> <y> <z> <blueprint>", "Pastes a blueprint at the specified offset.");
		helpMenu.addEntry("paste <x> <y> <z> <blueprint> <delay>", "Pastes a blueprint at the specified offset after a delay.");

		helpMenu.addEntry("pasterandom <x> <y> <z> <blueprints>", "Pastes a random specified blueprint at the specified offset.");
		helpMenu.addEntry("pasterandom <x> <y> <z> <blueprints> <delay>", "Pastes a random specified blueprint at the specified offset after a delay.");
		
		helpMenu.addEntry("pasteair <x> <y> <z> <blueprint>", "Pastes air blocks that mask a blueprint at the specified offset.");
		helpMenu.addEntry("pasteair <x> <y> <z> <blueprint> <delay>", "Pastes air blocks that mask a blueprint at the specified offset after a delay.");
		
		helpMenu.addEntry("pasteblock <x> <y> <z> <block> <data>", "Pastes a block at the specified offset.");
		helpMenu.addEntry("pasteblock <x> <y> <z> <block> <data> <delay>", "Pastes a block at the specified offset after a delay.");

		helpMenu.addEntry("task <x> <y> <z> <task> <blueprint>", "Attempts to run a task at the specified offset.");
		
		helpMenu.sortEntryList();
		
	}
	
	@CommandHandler
	public void onOppedCommand(OperatorCommandEvent event) {

		Player player = event.getCommandSender();
		String[] arguments = event.getArguments();
		int length = event.getLength();
		String argument = length > 0 ? arguments[0] : null;
		
		if(length == 1) {
			
			if(argument.equalsIgnoreCase("offset")) {

				if(!BlockSelectionHandler.hasSelection(player)) {
					FormatMessage.error(player, MessageErrorType.SELECTION);
					return;
				}
				
				PlayerSelection selection = BlockSelectionHandler.getPlayerSelection(player);
				Location position0 = selection.getPosition0();
				Location position1 = selection.getPosition1();

				int x = position1.getBlockX() - position0.getBlockX();
				int y = position1.getBlockY() - position0.getBlockY();
				int z = position1.getBlockZ() - position0.getBlockZ();

				FormatMessage.info(player, "Offset X=" + x + ", Y=" + y + ", Z=" + z);
				return;
			
			} else if(argument.equalsIgnoreCase("copy")) {
				
				if(!BlockSelectionHandler.hasSelection(player)) {
					FormatMessage.error(player, MessageErrorType.SELECTION);
					return;
				}
				
				Blueprint blueprint = new Blueprint("internal");
				blueprint.loadFromSelection(player);
				BlockSelectionHandler.setClipboard(player, blueprint);

				FormatMessage.info(player, "Selection saved to clipboard.");
				return;
				
			} else if(argument.equals("cut")) {
				
				if(!BlockSelectionHandler.hasSelection(player)) {
					FormatMessage.error(player, MessageErrorType.SELECTION);
					return;
				}
				
				Blueprint blueprint = new Blueprint("internal");
				blueprint.loadFromSelection(player);
				
				BlockSelectionHandler.setClipboard(player, blueprint);
				for(MemoryBlock block : BlockSelectionHandler.getSelection(player)) new Location(player.getWorld(), block.getBlockX(), block.getBlockY(), block.getBlockZ()).getBlock().setType(Material.AIR);
				
				FormatMessage.info(player, "Selection saved to clipboard and cut.");
				return;
				
			} else if(argument.equalsIgnoreCase("paste")) {
				
				if(BlockSelectionHandler.getClipboard(player) == null) {
					FormatMessage.error(player, MessageErrorType.CLIPBOARD_SELECTION);
					return;
				}
				
				FormatMessage.fluid(player, BlockSelectionHandler.getClipboard(player).paste(player.getLocation(), false), "Clipboard pasted.", MessageErrorType.CLIPBOARD_PASTE);
				return;
				
			}
			
		} else if(length == 2) {
			
			if(argument.equalsIgnoreCase("help")) {
				
				int page = 0;
				
				try {
					page = Integer.parseInt(arguments[1]);
				} catch (NumberFormatException e) {
					FormatMessage.error(player, MessageErrorType.PAGE);
					return;
				}
				
				helpMenu.sendPageInformation(player, page);
				return;
				
			} else if(argument.equalsIgnoreCase("save")) {
				
				if(BlockSelectionHandler.getClipboard(player) == null) {
					FormatMessage.error(player, MessageErrorType.CLIPBOARD_SELECTION);
					return;
				}
				
				String name = arguments[1];
				Blueprint blueprint = new Blueprint(name);
				
				if(blueprint.exists()) {
					FormatMessage.error(player, MessageErrorType.BLUEPRINT_ALREADY_EXISTING);
					return;
				}
				
				blueprint.createFromClipboard(player);
				
				FormatMessage.info(player, "Blueprint " + name + " saved.");
				return;
				
			} else if(argument.equalsIgnoreCase("load")) {
				
				String name = arguments[1];
				Blueprint blueprint = new Blueprint(name);
				
				if(!blueprint.exists()) {
					FormatMessage.error(player, MessageErrorType.BLUEPRINT_NOT_EXISTING);
					return;
				}
				
				blueprint.load();
				BlockSelectionHandler.setClipboard(player, blueprint);
				FormatMessage.info(player, "Blueprint " + name + " loaded to clipboard.");	
				
				return;
				
			}
			
		} else if(arguments.length == 5) {
			
			if(argument.equalsIgnoreCase("taskmove")) {
				BlueprintTaskFileMove file = new BlueprintTaskFileMove(arguments[1]);
				FormatMessage.fluid(player, file.create(arguments[2], arguments[3], arguments[4]), "Task created.", MessageErrorType.CREATE_TASK);
			}
			
			return;
			
		} else if(arguments.length == 6) {
			
			if(argument.equalsIgnoreCase("taskbounce")) {
				BlueprintTaskFileBounce file = new BlueprintTaskFileBounce(arguments[1]);
				FormatMessage.fluid(player, file.create(arguments[2], arguments[3], arguments[4], arguments[5]), "Task created.", MessageErrorType.CREATE_TASK);
			}
			
			return;
			
		}
		
		FormatMessage.usage(player, "bp", new String[]{ "offset", 
														"copy", 
														"cut", 
														"paste", 
														"help <number>", 
														"save <name>", 
														"load <name>", 
														"taskmove <name> <direction> <count> <delay>", 
														"taskbounce <name> <direction> <count> <delay> <pauses>"});
		
	}
	
	@CommandHandler
	public void onCommandBlock(BlockCommandEvent event) {
		
		BlockCommandSender sender = event.getCommandSender();
		String[] arguments = event.getArguments();
		Location location = sender.getBlock().getLocation();
		int length = event.getLength();
		String argument = length > 0 ? arguments[0] : null;

		if(arguments.length == 3) {

			if(argument.equalsIgnoreCase("boolean")) {
				String key = arguments[1];
				boolean value = arguments[2].equals("true");
				BlueprintSyntax.addBoolean(key, value);
				FormatMessage.info(sender, "Boolean set " + key + " to " + value + ".");
				return;
			}
		
		} else if(length == 4) {
			
			if(argument.equalsIgnoreCase("delay")) {
				FormatMessage.fluid(sender, BlueprintSyntax.delay(location, arguments[1], arguments[2], arguments[3]), "Delay called.", MessageErrorType.DELAY);
				return;
			}
		
		} else if(length == 5) {
			
			if(argument.equalsIgnoreCase("delay")) {
				FormatMessage.fluid(sender, BlueprintSyntax.delayWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4]), "Delay called.", MessageErrorType.DELAY);
				return;
			} else if(argument.equalsIgnoreCase("delayif")) {
				FormatMessage.fluid(sender, BlueprintSyntax.isBooleanTrue(arguments[4]) && BlueprintSyntax.delay(location, arguments[1], arguments[2], arguments[3]), "Delay called.", MessageErrorType.DELAY);
				return;
			} else if(argument.equalsIgnoreCase("!delayif")) {
				FormatMessage.fluid(sender, !BlueprintSyntax.isBooleanTrue(arguments[4]) && BlueprintSyntax.delay(location, arguments[1], arguments[2], arguments[3]), "Delay called.", MessageErrorType.DELAY);
				return;	
			} else if(argument.equalsIgnoreCase("paste")) {
				FormatMessage.fluid(sender, BlueprintSyntax.paste(location, arguments[1], arguments[2], arguments[3], arguments[4]), "Blueprint pasted.", MessageErrorType.BLUEPRINT_PASTE);
				return;
			} else if(argument.equalsIgnoreCase("pasterandom")) {
				String[] temp = arguments[4].split(",");
				FormatMessage.fluid(sender, BlueprintSyntax.paste(location, arguments[1], arguments[2], arguments[3], temp[MathHandler.RANDOM.nextInt(temp.length)]), "Blueprint pasted.", MessageErrorType.BLUEPRINT_PASTE);
			} else if(argument.equalsIgnoreCase("pasteair")) {
				FormatMessage.fluid(sender, BlueprintSyntax.paste(location, Material.AIR, 0, arguments[1], arguments[2], arguments[3], arguments[4]), "Blueprint pasted.", MessageErrorType.BLUEPRINT_PASTE);
				return;
			}
			
		} else if(length == 6) {

			 if(argument.equalsIgnoreCase("delayif")) {
				FormatMessage.fluid(sender, BlueprintSyntax.isBooleanTrue(arguments[5]) && BlueprintSyntax.delayWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4]), "Delay called.", MessageErrorType.DELAY);
				return;
			} else if(argument.equalsIgnoreCase("!delayif")) {
				FormatMessage.fluid(sender, !BlueprintSyntax.isBooleanTrue(arguments[5]) && BlueprintSyntax.delayWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4]), "Delay called.", MessageErrorType.DELAY);
				return;
			} else if(argument.equals("delayifcompare")) {
				FormatMessage.fluid(sender, (BlueprintSyntax.isBooleanTrue(arguments[4]) == BlueprintSyntax.isBooleanTrue(arguments[5])) && BlueprintSyntax.delay(location, arguments[1], arguments[2], arguments[3]), "Delay called.", MessageErrorType.DELAY);
			} else if(argument.equals("!delayifcompare")) {
				FormatMessage.fluid(sender, (BlueprintSyntax.isBooleanTrue(arguments[4]) != BlueprintSyntax.isBooleanTrue(arguments[5])) && BlueprintSyntax.delay(location, arguments[1], arguments[2], arguments[3]), "Delay called.", MessageErrorType.DELAY);
			} else if(argument.equals("paste")) {
				FormatMessage.fluid(sender, BlueprintSyntax.pasteWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]), "Blueprint pasted.", MessageErrorType.BLUEPRINT_PASTE);
				return;
			} else if(argument.equalsIgnoreCase("pasterandom")) {
				String[] temp = arguments[4].split(",");
				FormatMessage.fluid(sender, BlueprintSyntax.pasteWithDelay(location, arguments[1], arguments[2], arguments[3], temp[MathHandler.RANDOM.nextInt(temp.length)], arguments[5]), "Blueprint pasted.", MessageErrorType.BLUEPRINT_PASTE);
			} else if(argument.equalsIgnoreCase("pasteair")) {
				FormatMessage.fluid(sender, BlueprintSyntax.pasteWithDelay(location, Material.AIR, 0, arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]), "Blueprint pasted.", MessageErrorType.BLUEPRINT_PASTE);
				return;
			} else if(argument.equalsIgnoreCase("pasteblock")) {
				FormatMessage.fluid(sender, BlueprintSyntax.pasteBlock(location, arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]), "Block pasted.", MessageErrorType.BLOCK_PASTE);
				return;
			} else if(argument.equalsIgnoreCase("task")) {
				
				Location paste = BlueprintSyntax.getLocation(arguments[1], arguments[2], arguments[3]);
				
				if(paste == null) {
					FormatMessage.error(sender, MessageErrorType.TASK_NOT_EXISTING);
					return;
				}
				
				String taskName = arguments[4];
				String blueprintName = arguments[5];
				
				int xOffset = paste.getBlockX();
				int yOffset = paste.getBlockY();
				int zOffset = paste.getBlockZ();
				
				if(BlueprintTaskFileBounce.isTask(taskName)) {
					
					try {
						
						BlueprintTaskFileBounce file = new BlueprintTaskFileBounce(taskName);
						Blueprint blueprint = new Blueprint(blueprintName);
						BlueprintTaskBounce task = (BlueprintTaskBounce) file.getBlueprintTask(blueprint, location, xOffset, yOffset, zOffset);
						
						if(BlueprintTask.cancleIfLocationMatches(location)) {
							FormatMessage.info(sender, "Task cancled.");
						} else {
							task.start();
							FormatMessage.info(sender, "Task called.");
						}
						
					} catch (Exception e) {
						FormatMessage.error(sender, MessageErrorType.TASK_NOT_EXISTING);
						return;
					}
					
				} else if(BlueprintTaskFileMove.isTask(taskName)) {
				
					try {
	
						BlueprintTaskFileMove file = new BlueprintTaskFileMove(taskName);
						Blueprint blueprint = new Blueprint(blueprintName);
						BlueprintTaskMove task = (BlueprintTaskMove) file.getBlueprintTask(blueprint, location, xOffset, yOffset, zOffset);
						
						if(BlueprintTask.cancleIfLocationMatches(location)) {
							FormatMessage.info(sender, "Task cancled.");
						} else {
							task.start();
							FormatMessage.info(sender, "Task called.");
						}
						
					} catch (Exception e) {
						FormatMessage.error(sender, MessageErrorType.TASK_NOT_EXISTING);
						return;
					}
					
				}
				
				return;
			}
			
		} else if(length == 7) {
			
			if(argument.equals("delayifcompare")) {
				FormatMessage.fluid(sender, (BlueprintSyntax.isBooleanTrue(arguments[5]) == BlueprintSyntax.isBooleanTrue(arguments[6])) && BlueprintSyntax.delayWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4]), "Delay called.", MessageErrorType.DELAY);
			} else if(argument.equals("!delayifcompare")) {
				FormatMessage.fluid(sender, (BlueprintSyntax.isBooleanTrue(arguments[5]) != BlueprintSyntax.isBooleanTrue(arguments[6])) && BlueprintSyntax.delayWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4]), "Delay called.", MessageErrorType.DELAY);
			} else if(argument.equalsIgnoreCase("pasteblock")) {
				FormatMessage.fluid(sender, BlueprintSyntax.pasteBlockWithDelay(location, arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6]), "Block pasted.", MessageErrorType.BLOCK_PASTE);
				return;
			}
			
		}
		
		FormatMessage.usage(sender, "bp", new String[] {"boolean <key> <value>", 
														"delay <x> <y> <z>", 
														"delay <x> <y> <z> <delay>", 
														"delayif <x> <y> <z> <key>", 
														"delayif <x> <y> <z> <delay> <key>", 
														"!delayif <x> <y> <z> <key>", 
														"!delayif <x> <y> <z> <delay> <key>", 
														"delayifcompare <x> <y> <z> <key> <key>", 
														"delayifcompare <x> <y> <z> <delay> <key> <key>", 
														"!delayifcompare <x> <y> <z> <key> <key>",
														"!delayifcompare <x> <y> <z> <delay> <key> <key>", 
														"paste <x> <y> <z> <blueprint>", 
														"paste <x> <y> <z> <blueprint> <delay>", 
														"pasteair <x> <y> <z> <blueprint>", 
														"pasteair <x> <y> <z> <blueprint> <delay>", 
														"pasterandom <x> <y> <z> <blueprints>", 
														"pasterandom <x> <y> <z> <blueprints> <delay>", 
														"task <x> <y> <z> <task> <blueprint>", 
														"pasteblock <x> <y> <z> <block> <data>", 
														"pasteblock <x> <y> <z> <block> <data> <delay>"});

	}

}
