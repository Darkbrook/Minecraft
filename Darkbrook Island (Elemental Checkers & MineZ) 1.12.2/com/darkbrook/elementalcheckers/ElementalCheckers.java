package com.darkbrook.elementalcheckers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.darkbrook.elementalcheckers.replay.ElementalCheckersLog;
import com.darkbrook.elementalcheckers.scrolls.Scroll;
import com.darkbrook.elementalcheckers.scrolls.ScrollOfElectricity;
import com.darkbrook.elementalcheckers.scrolls.ScrollOfReincarnation;
import com.darkbrook.elementalcheckers.scrolls.ScrollOfRubble;
import com.darkbrook.elementalcheckers.scrolls.ScrollOfVoid;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.entity.PacketHandler;
import com.darkbrook.library.gui.GuiHandler;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.library.plugin.PluginGrabber;

public class ElementalCheckers implements Listener {
	
	public static final List<Integer> BOARD_SLOTS = GuiHandler.getColumns(6, 9, 3, 8);
	public static ElementalCheckersLog lastGame;
	
	public static final HashMap<Player, ElementalCheckers> GAMES = new HashMap<Player, ElementalCheckers>();
	public static final HashMap<Player, ElementalCheckers> SPECTATORS = new HashMap<Player, ElementalCheckers>();

	public static final ItemStack CURRENT = ItemHandler.setDisplayName(new ItemStack(Material.COAL_BLOCK), ChatColor.GRAY + "" + ChatColor.BOLD + "Selected Team: Empty");
	public static final ItemStack FIRE = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_CLAY, 1, (byte) 1), ChatColor.GOLD + "" + ChatColor.BOLD + "Fire");
	public static final ItemStack WATER = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_CLAY, 1, (byte) 11), ChatColor.BLUE + "" + ChatColor.BOLD + "Water");
	public static final ItemStack EARTH = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_CLAY, 1, (byte) 5), ChatColor.GREEN + "" + ChatColor.BOLD + "Earth");
	public static final ItemStack AIR = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_CLAY, 1, (byte) 3), ChatColor.AQUA + "" + ChatColor.BOLD + "Air");
	public static final ItemStack MAGIC = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_CLAY, 1, (byte) 10), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Magic");
	public static final ItemStack TAKEN = ItemHandler.setDisplayName(new ItemStack(Material.COAL_BLOCK), ChatColor.GRAY + "" + ChatColor.BOLD + "Team Already Selected");
	public static final ItemStack GROUND = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 12), ChatColor.GOLD + "" + ChatColor.BOLD + "Sturdy Ground");
	public static final ItemStack BOOK =  ItemHandler.appendLore(ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.BOOK), ChatColor.GOLD + "" + ChatColor.BOLD + "Book Of Scrolls"), ChatColor.YELLOW + "Left click to cycle through scrolls."), ChatColor.YELLOW + "Right click to purchase or select a scroll.");
	public static final ItemStack CANMOVETO = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13), ChatColor.GREEN + "" + ChatColor.BOLD + "Can Move To");
	public static final ItemStack CANTMOVETO = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), ChatColor.RED + "" + ChatColor.BOLD + "Cant Move To");
	public static final ItemStack EMPTY = ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), ChatColor.GRAY + "" + ChatColor.BOLD + "Holding Area");
	public static final ItemStack PASS_TURN = ItemHandler.setDisplayName(new ItemStack(Material.BARRIER), ChatColor.RED + "" + ChatColor.BOLD + "Pass Turn");
	public static final ItemStack EMPTY_SCROLL = ItemHandler.setDisplayName(new ItemStack(Material.PAPER), ChatColor.GRAY + "" + ChatColor.BOLD + "Selected Scroll: Empty");
	
	public static final Scroll SCROLL_OF_ELECTRICITY = new ScrollOfElectricity(0, 8, 32);
	public static final Scroll SCROLL_OF_RUBBLE = new ScrollOfRubble(0, 8, 64);
	public static final Scroll SCROLL_OF_VOID = new ScrollOfVoid(0, 32, 256);
	public static final Scroll SCROLL_OF_REINCARNATION = new ScrollOfReincarnation(0, 32, 256);

	private List<Player> spectators = new ArrayList<Player>();
	private HashMap<Player, ElementalCheckersPlayer> spectatorsView = new HashMap<Player, ElementalCheckersPlayer>();
		
	public ElementalCheckersPlayer player1;
	public ElementalCheckersPlayer player2;
	public ElementalCheckersPlayer playerCurrent;

	public int selectedSlot;
	
	public boolean isValid;
	public boolean isGame;
	public boolean isGameOpen;
	
	public static void register() {
		
		Plugin plugin = PluginGrabber.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(new ElementalCheckers(null, null), plugin);

		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			
			if(player.getOpenInventory().getTitle().contains("Elemental Checkers")) {
				player.closeInventory();
				PacketHandler.sendTitleMessage(player, ChatColor.RED + "" + ChatColor.BOLD + "Game Ended");				
				PacketHandler.sendSubTitleMessage(player, ChatColor.GRAY + "" + ChatColor.BOLD + "Reason: Server Reload");				
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 2.0F);
			}
			
		}
		
	}
	
	public static ItemStack addDecay(ItemStack stack, int turns) {
		return ItemHandler.appendLore(stack.clone(), ChatColor.GRAY + "" + ChatColor.ITALIC + "Decays in (" + turns + ") turns.");
	}

	public ElementalCheckers(Player player1, Player player2) {
		this.player1 = new ElementalCheckersPlayer(this, player1);
		this.player2 = new ElementalCheckersPlayer(this, player2);
		this.selectedSlot = -1;
	}
	
	public void addSpectator(Player player, ElementalCheckersPlayer view) {
		sendToAll(ChatColor.BLUE + player.getName() + " is now spectating.", ChatColor.BLUE + player.getName() + " is now spectating.", ChatColor.BLUE + player.getName() + " is now spectating.", new CompressedSound(Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.0F));
		player.sendMessage(ChatColor.BLUE + "Elemental Checkers game joined.");
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.0F);
		spectators.add(player);
		spectatorsView.put(player, view);
		view.game.open(player);
		SPECTATORS.put(player, this);
	}
	
	public void removeSpectator(Player player) {
		SPECTATORS.remove(player);
		spectators.remove(player);
		spectatorsView.remove(player);
		player.sendMessage(ChatColor.BLUE + "Elemental Checkers game left.");
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.0F);
		sendToAll(ChatColor.BLUE + player.getName() + " stopped spectating.", ChatColor.BLUE + player.getName() + " stopped spectating.", ChatColor.BLUE + player.getName() + " stopped spectating.", new CompressedSound(Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.0F));
	}
	
	public void sendMessageAndSoundToSpectator(String message, CompressedSound sound) {
		List<Player> spectators = new ArrayList<Player>();
		spectators.addAll(this.spectators);
		for(Player player : spectators) {
			if(!message.isEmpty()) player.sendMessage(message);
			sound.play(player, true);
		}
	}
	
	private void sendEndMessageToSpectators(String message, String subMessage) {
		List<Player> spectators = new ArrayList<Player>();
		spectators.addAll(this.spectators);
		for(Player player : spectators) {
			player.closeInventory();
			PacketHandler.sendTitleMessage(player, message);
			PacketHandler.sendSubTitleMessage(player, subMessage);
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 2.0F);
		}
	}
	
	public void onTurn(String clientMessage, String opponentMessage, String spectatorMessage, CompressedSound sound) {
		playerCurrent.clearMoveOptions();
		playerCurrent.resetSelectedPiece(selectedSlot);
		player1.decay();
		player2.decay();
		player1.subtractScrollCooldown();
		player2.subtractScrollCooldown();
		sendToAll(clientMessage, opponentMessage, spectatorMessage, sound);
	}
	
	public void updateTurnDisplay() {
		ItemStack turn = ItemHandler.appendLore(playerCurrent.turn.clone(), playerCurrent.getHealthString());
		player1.setGameItem(GuiHandler.getSlot(1, 2), turn);
		player2.setGameItem(GuiHandler.getSlot(1, 2), turn);
		player1.player.updateInventory();
		player2.player.updateInventory();
	}
	
	public void passTurn() {
		selectedSlot = -1;
		playerCurrent = getOpposite(playerCurrent.player);
		player1.checkForDefeat();
		player2.checkForDefeat();
		updateTurnDisplay();
		List<Player> remove = new ArrayList<Player>();
		for(Player player : spectators) if(isGame) player.updateInventory(); else remove.add(player);
		for(Player player : remove) player.closeInventory();
	}
	
	public void sendToAll(String clientMessage, String opponentMessage, String spectatorMessage, CompressedSound sound) {
		if(!clientMessage.isEmpty()) playerCurrent.player.sendMessage(clientMessage);
		sound.play(playerCurrent.player, true);
		if(!opponentMessage.isEmpty()) getOpposite(playerCurrent.player).player.sendMessage(opponentMessage);
		sound.play(getOpposite(playerCurrent.player).player, true);
		sendMessageAndSoundToSpectator(spectatorMessage, sound);
		player1.ecl.addLog(player1.getContents(), sound, playerCurrent == player1 ? clientMessage : opponentMessage);
	}

	public void init() {
		player1.openSelector();
		player2.openSelector();
		this.isValid = true;
		this.isGame = false;
		this.isGameOpen = false;
	}
	
	public void game() {
		
		isGame = true;				
		player1.openGame();
		player2.openGame();
		playerCurrent = player1;
		updateTurnDisplay();
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				isGameOpen = true;
			}
			
		}, 20);
		
	}
	
	public void sleep(String message, String subMessage) {
		player1.closeGame(message, subMessage);
		player2.closeGame(message, subMessage);
		sendEndMessageToSpectators(message, subMessage);
		this.isValid = false;
		this.isGame = false;
		lastGame = player1.ecl;
	}
	
	public ElementalCheckersPlayer getOpposite(Player player) {
		return player == player1.player ? player2 : player1;
	}
	
	public ElementalCheckersPlayer getClient(Player player) {
		return player == player1.player ? player1 : player2;
	}
	
	@EventHandler
	public void onPlayerCloseInventory(InventoryCloseEvent event) {
	
		Player player = (Player) event.getPlayer();
		
		if(event.getInventory().getName().equals(ChatColor.DARK_GRAY + "Elemental Checkers (Selector)") && GAMES.containsKey(player)) {
			ElementalCheckers ec = GAMES.get(player);
			if(!ec.isGame) ec.sleep(ChatColor.RED + "" + ChatColor.BOLD + "Game Ended", ChatColor.GRAY + "" + ChatColor.BOLD + "Reason: " + player.getName() + " quit.");
		} else if(event.getInventory().getName().equals(ChatColor.DARK_GRAY + "Elemental Checkers")) {
			
			if(GAMES.containsKey(player)) {
				ElementalCheckers ec = GAMES.get(player);
				if(ec.isGame && ec.isGameOpen) ec.sleep(ChatColor.RED + "" + ChatColor.BOLD + "Game Ended", ChatColor.GRAY + "" + ChatColor.BOLD + "Reason: " + player.getName() + " quit.");			
			} else if(SPECTATORS.containsKey(player)) {
				ElementalCheckers ec = SPECTATORS.get(player);
				ec.removeSpectator(player);
			}
			
		}
		
	}
	
	public static String getInfoFormat(ChatColor color, String message) {
		return color + message;
	}
	
	public static String getFormat(ChatColor color, ChatColor messageColor, String message) {
		return color + "*" + messageColor + message + color + "*";
	}
	
	public static String getSpacedFormat(ChatColor color, ChatColor messageColor, String message) {
		return color + "          *" + messageColor + message + color + "*";
	}
	
}
