package com.darkbrook.island.internal.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.darkbrook.island.References;
import com.darkbrook.island.internal.addons.elementalcheckers.ElementalCheckers;
import com.darkbrook.island.library.config.WorldLoader;
import com.darkbrook.island.library.entity.Npc;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.LocationHandler;
import com.darkbrook.island.library.misc.SkullCreator;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.library.template.Selection;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.combat.InstanceBattle;
import com.darkbrook.island.mmo.maze.Maze;

import net.md_5.bungee.api.ChatColor;

public class CommandRegistry extends CommandBase {

	public static final HashMap<Player, Integer> CIRCLES = new HashMap<Player, Integer>();
	public static final HashMap<Player, String> COPY = new HashMap<Player, String>();
	
	@Override
	void onPlayer(Player player, World world, String command, String[] args) {
		
		if(this.isCommand("buildvoxel")) {
		
			if(this.isSize(2)) {
				
				String material = args[0];
				int size = 0;
				
				try {
					size = Integer.parseInt(args[1]);
				} catch(NumberFormatException e) {
					player.sendMessage(References.error + "Invalid size.");
				}
				
				Bukkit.dispatchCommand(player, "b b");
				Bukkit.dispatchCommand(player, "v " + material);
				Bukkit.dispatchCommand(player, "b " + size);

				player.sendMessage(References.message + "Attempted setting your voxel brush to a ball, with the material: " + material + ", with the size: " + size + ".");
				
			} else {
				player.sendMessage(References.error + "Usage: /buildvoxel <material> <size>");
			}
			
		} else if(this.isCommand("ec")) {
			
			if(this.isSize(1)) {
				
				if(this.isArg(0, "replay")) {
				
					if(ElementalCheckers.lastGame != null) {
						ElementalCheckers.lastGame.play(player);
					} else {
						player.sendMessage(References.error + "There is no available replay.");
					}
					
				} else {
				
					if(Bukkit.getServer().getPlayer(args[0]) != null && Bukkit.getServer().getPlayer(args[0]) != player) {
						Player p = Bukkit.getServer().getPlayer(args[0]);

						if(!ElementalCheckers.GAMES.containsKey(p)) {
							ElementalCheckers ec = new ElementalCheckers(player, p);
							ec.init();
						} else {
							player.sendMessage(References.error + p.getName() + " is already in a game.");
						}
					
					} else {
						player.sendMessage(References.error + "Invalid Player.");
					}
				
				}
				
				
				
			} else if(this.isSize(2)) {
				
				if(this.isArg(0, "spectate")) {
					
					if(Bukkit.getServer().getPlayer(args[1]) != null && Bukkit.getServer().getPlayer(args[1]) != player) {
						
						Player p = Bukkit.getServer().getPlayer(args[1]);
						
						if(ElementalCheckers.GAMES.containsKey(p)) {
							ElementalCheckers ec = ElementalCheckers.GAMES.get(p);
							if(ec.isGame) {
								ec.addSpectator(player, ec.player1.player == p ? ec.player1 : ec.player2);
							} else {
								player.sendMessage(References.error + p.getName() + "'s game has not started yet.");
							}
						} else {
							player.sendMessage(References.error + p.getName() + " is not in a game.");
						}
						
					} else {
						player.sendMessage(References.error + "Invalid Player.");
					}

				} else {
					player.sendMessage(References.error + "Usage: /ec spectate <name>");
				}
				
			} else {
				player.sendMessage(References.error + "Usage: /ec <name>");
				player.sendMessage(References.error + "Usage: /ec spectate <name>");
			}
		}
		
	}
	
	@Override
	void onOp(Player player, World world, String command, String[] args) {
		
		if(this.isCommand("freeze")) {
		
			if(this.isSize(1)) {
				
			} else {
				player.sendMessage(References.error + "Usage: /freeze <name>");
			}
			
		} else if(this.isCommand("blueprint")) {
		
			if(this.isSize(2)) {
				
				if(this.isArg(0, "save")) {
					
					if(Selection.addBlueprint(player, args[1], player.getLocation())) {
						player.sendMessage(References.message + "Blueprint " + args[1] + " saved.");
					} else {
						player.sendMessage(References.error + "Invalid selection/Name already in use.");
					}
					
				} else if(this.isArg(0, "load")) {
					
					if(Selection.loadBlueprint(args[1], player.getLocation())) {
						player.sendMessage(References.message + "Blueprint " + args[1] + " loaded.");
					} else {
						player.sendMessage(References.error + "Invalid blueprint.");
					}
					
				} else {
					player.sendMessage(References.error + "Usage: /blueprint save <name>");
					player.sendMessage(References.error + "Usage: /blueprint load <name>");
				}
				
			} else {
				player.sendMessage(References.error + "Usage: /blueprint save <name>");
				player.sendMessage(References.error + "Usage: /blueprint load <name>");
			}
			
		} else if(this.isCommand("copy")) {
		
			if(this.isSize(0)) {
				String s = Selection.getSelectionStringFromOffset(player, player.getLocation());
				if(s != null) {
					COPY.put(player, s);
					player.sendMessage(References.message + "Selection copied.");
				} else {
					player.sendMessage(References.error + "Invalid selection.");
				}
			} else {
				player.sendMessage(References.error + "Usage: /copy");
			}
			
		} else if(this.isCommand("paste")) {
			
			if(this.isSize(0)) {
				if(COPY.containsKey(player)) {
					Selection.paste(COPY.get(player), player.getLocation());
					player.sendMessage(References.message + "Selection pasted.");
				} else {
					player.sendMessage(References.error + "Invalid selection.");
				}
			} else {
				player.sendMessage(References.error + "Usage: /paste");
			}
			
		} else if(this.isCommand("stop")) {
		
			if(this.isSize(0)) {
				for(Player players : Bukkit.getServer().getOnlinePlayers()) players.kickPlayer(ChatColor.RED + "Server Stopping");
				for(World worlds : Bukkit.getServer().getWorlds()) worlds.save();
				Bukkit.getServer().shutdown();
			} else {
				player.sendMessage(References.error + "Usage: /stop");
			}
			
		} else if(this.isCommand("instancebattle")) {
		
			InstanceBattle ib = new InstanceBattle();
			ib.addEntity(player, 0, 0, 0, 0, 0);
			ib.addLocalPlayer(player, 64, 0);
			ib.addLocalEntities(player, 64, 1);
			ib.init();
			
		} else if(this.isCommand("giveskull")) {
		
			if(this.isSize(1)) {
				ItemHandler.addItem(player, SkullCreator.getSkull("Skull", args[0]));
				player.sendMessage(References.message + "Skull received.");
			} else {
				player.sendMessage(References.error + "Usage: /giveskull <link>");
			}
			
		} else if(this.isCommand("spawnnpc")) {
		
			if(this.isSize(1)) {
				Npc npc = new Npc(world, args[0]);
				npc.spawn(player);
				player.sendMessage(References.message + "Npc spawned.");
			} else {
				player.sendMessage(References.error + "Usage: /spawnnpc <name>");
			}
			
		} else if(this.isCommand("removenpc")) {
			
			if(this.isSize(0)) {
				Npc.removeClosestNpc(player);
			} else {
				player.sendMessage(References.error + "Usage: /removenpc");
			}
			
		} else if(this.isCommand("menu")) {
		
			if(this.isSize(0)) {
				GameRegistry.MENU.openInventory(player);
			} else {
				player.sendMessage(References.error + "Usage: /menu");
			}
			
		} else if(this.isCommand("bind")) {
		
			if(args.length >= 1) { 
				
				if(player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
					String playerCommand = "";
					for(int i = 0; i < args.length; i++) playerCommand += i >= args.length - 1 ? args[i] : args[i] + " ";
					Bind.addCommand(player, playerCommand);
					player.sendMessage(References.message + "Bound the command \"" + playerCommand + "\" to your hand.");
				} else {
					player.sendMessage(References.error + "Invalid Item.");
				}
				
			} else {
				player.sendMessage(References.error + "Usage: /bind <command>");
			}
			
		} else if(this.isCommand("kit")) {
			
			if(this.isSize(1)) {
				Kit.give(player, args[0]);
			} else if(this.isSize(2)) {
				
				if(this.isArg(0, "add")) {
					Kit.add(player, args);
				} else if(this.isArg(0, "remove")) {
					Kit.remove(player, args);
				} else {
					player.sendMessage(References.error + "Usage: /kit add <name>");
					player.sendMessage(References.error + "Usage: /kit remove <name>");
				}
				
			} else if(this.isSize(3)) {
				
				if(this.isArg(0, "give")) {
					Kit.give(player, args);
				} else {
					player.sendMessage(References.error + "Usage: /kit give <player> <kit>");
				}
				
			} else {
				player.sendMessage(References.error + "Usage: /kit <kit>");
				player.sendMessage(References.error + "Usage: /kit add <name>");
				player.sendMessage(References.error + "Usage: /kit remove <name>");
				player.sendMessage(References.error + "Usage: /kit give <player> <kit>");
			}
		
		} else if(this.isCommand("setspawn")) {
			
			if(this.isSize(0)) {
				Respawn.setRespawnLocation(player);
				player.sendMessage(References.message + "Spawn set to " + (int) (player.getLocation().getX()) + ", " + (int) (player.getLocation().getY()) + ", " + (int) (player.getLocation().getZ()) + ".");
			} else {
				player.sendMessage(References.error + "Usage: /setspawn");
			}
			
		} else if(this.isCommand("spawn")) {
			
			if(this.isSize(0)) {
				player.teleport(Respawn.getRespawnLocation(world));
				player.sendMessage(References.message + "Teleported to spawn.");
			} else {
				player.sendMessage(References.error + "Usage: /setspawn");
			}
			
		} else if(this.isCommand("circles")) {
			
			if(!CIRCLES.containsKey(player)) {
				
				if(this.isSize(1)) {
				
					int id = UpdateHandler.repeat(new UpdateListener() {

						@Override
						public void onUpdate() {
							player.setVelocity(new Vector(-(Math.sin(Math.toRadians(player.getLocation().getYaw())) * 2), 0, (Math.cos(Math.toRadians(player.getLocation().getYaw())) * 2)));						
							Location location = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw() + Integer.parseInt(args[0]), player.getLocation().getPitch());
							player.teleport(location);
						}
					
					}, 0, 20);
				
					CIRCLES.put(player, id);
					player.sendMessage(References.message + "Circles Started.");
					
				} else {
					player.sendMessage(References.error + "Usage: /circles <number>");
				}
				
			} else if(this.isSize(0)) {
				UpdateHandler.cancle(CIRCLES.get(player));
				CIRCLES.remove(player);
				player.sendMessage(References.message + "Circles Ended.");
			} else {
				player.sendMessage(References.error + "Usage: /circles");
			}
			
		} else if(this.isCommand("see")) {
			
			if(this.isSize(0)) {
			
				if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					player.sendMessage(References.message + "See disabled.");
				} else {
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 0, false, false));
					player.sendMessage(References.message + "See enabled.");
				}
			
			} else {
				player.sendMessage(References.error + "Usage: /see");
			}
			
		} else if(this.isCommand("world")) {
			
			if(this.isSize(1)) {
				
				String worldname = args[0];

				if(References.getServerFolderFile(worldname).exists()) {
					player.teleport(Respawn.getRespawnLocation(WorldLoader.loadWorld(worldname)));
					player.sendMessage(References.message + "Teleported to " + WorldLoader.getWorldName(worldname) + ".");
				} else if(References.getServerFolderFile("worlds\\" + worldname).exists()) {
					player.teleport(Respawn.getRespawnLocation(WorldLoader.loadWorld("worlds\\" + worldname)));
					player.sendMessage(References.message + "Teleported to " + WorldLoader.getWorldName(worldname) + ".");
				} else {
					player.sendMessage(References.error + "Invalid World.");
				}
				
				
			} else {
				player.sendMessage(References.error + "Usage: /world <name>");
			}
						
		}

	}

	@Override
	void onCommandBlock(BlockCommandSender cmdblock, World world, String command, String[] args) {
		
		if(this.isCommand("maze")) {
			if(this.isSize(3) && this.isArg(2, "clear")) {
				Maze.generateClearing(LocationHandler.getLocationCenterWithOffset(cmdblock.getBlock().getLocation(), 0, 2, 0), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			} else {
				Maze.generateMaze(LocationHandler.getLocationCenterWithOffset(cmdblock.getBlock().getLocation(), 0, 2, 0), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			}
		}
		
	}

}
