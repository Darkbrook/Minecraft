package com.darkbrook.elementalcheckers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.darkbrookisland.DarkbrookIsland;
import com.darkbrook.elementalcheckers.guis.GuiTypeElementalCheckers;
import com.darkbrook.elementalcheckers.guis.GuiTypeElementalCheckersSelector;
import com.darkbrook.elementalcheckers.replay.ElementalCheckersLog;
import com.darkbrook.elementalcheckers.scrolls.Scroll;
import com.darkbrook.elementalcheckers.scrolls.ScrollOfVoid;
import com.darkbrook.library.entity.PacketHandler;
import com.darkbrook.library.gui.Gui;
import com.darkbrook.library.gui.GuiHandler;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.misc.MathHandler;
import com.darkbrook.library.misc.SkullHandler;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class ElementalCheckersPlayer {

	public ElementalCheckers ec;
	public ElementalCheckersLog ecl;
	public Scroll scroll;
	public Player player;
	public Gui selector;
	public Gui game;
	public ItemStack piece;
	public ItemStack warrior;
	public ItemStack selected;
	public ItemStack enemy;
	public ItemStack turn;
	public int zapDamage;
	public boolean hasSelected;
	
	private int actionPoints;
	private int maxHealth;
	private boolean isClickable;
	private boolean isPaused;
	
	public static int getOppositeSlot(int slot) {
		return (53 - slot) + 3;
	}
	
	public ElementalCheckersPlayer(ElementalCheckers ec, Player player) {
		this.ec = ec;
		this.ecl = new ElementalCheckersLog();
		this.player = player;
		this.actionPoints = 0;
		this.maxHealth = 0;
		this.zapDamage = 3;
		this.hasSelected = false;
		this.isClickable = true;
		this.isPaused = false;
	}

	public void slowClicks(int delay) {
		
		isClickable = false;
				
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				isClickable = true;
			}
			
		}, delay);
		
	}
	
	public boolean isClickable() {
		return isClickable;
	}
	
	public void pauseTurn(int delay) {
		
		isPaused = true;
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				isPaused = false;
			}
			
		}, delay);
		
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void openSelector() {
		selector = new Gui(new GuiTypeElementalCheckersSelector(ec), ChatColor.DARK_GRAY + "Elemental Checkers (Selector)", 1);
		selector.setItem(0, ElementalCheckers.CURRENT);
		selector.setItem(2, ElementalCheckers.FIRE);
		selector.setItem(3, ElementalCheckers.WATER);
		selector.setItem(4, ElementalCheckers.EARTH);
		selector.setItem(5, ElementalCheckers.AIR);
		selector.setItem(6, ElementalCheckers.MAGIC);
		selector.setItem(8, ElementalCheckers.BOOK);
		selector.open(player);
		ElementalCheckers.GAMES.put(player, ec);
	}
	
	private void generatePiece(int slot) {
		ElementalCheckersPiece piece = ElementalCheckersPiece.generatePiece(warrior);
		ElementalCheckersPiece.addPieceToMapping(this, slot, piece);
		setGameItem(slot, piece.getShown());
	}
	
	public void generatePieceOnBoard(int slot) {
		ElementalCheckersPiece piece = ElementalCheckersPiece.generatePiece(warrior);
		ElementalCheckersPiece.addPieceToMapping(this, slot, piece);
		setGameItem(slot, piece.getShown());
		setOppositeGameItem(slot, piece.getHidden());
	}
	
	public void openGame() {
		
		warrior = ItemHandler.setDisplayName(piece.clone(), ItemHandler.getDisplayName(piece) + " Warrior");
		selected = ItemHandler.setDisplayName(piece.clone(), ChatColor.GRAY + "" + ChatColor.BOLD + "Selected Team: " + ItemHandler.getDisplayName(piece));
		turn = ItemHandler.setDisplayName(SkullHandler.getSkullFromPlayerName(player), ChatColor.GRAY + "" + ChatColor.BOLD + "Current Turn: " + ItemHandler.getDisplayName(piece));
		enemy = ec.player1 == this ? ItemHandler.setDisplayName(ec.player2.piece.clone(), ItemHandler.getDisplayName(ec.player2.piece.clone()) + " Warrior") : ItemHandler.setDisplayName(ec.player1.piece.clone(), ItemHandler.getDisplayName(ec.player1.piece.clone()) + " Warrior");
		
		game = new Gui(new GuiTypeElementalCheckers(ec), ChatColor.DARK_GRAY + "Elemental Checkers", 6);
		game.setContents(GuiHandler.setColumnsContents(null, 6, 9, 0, 2, ElementalCheckers.EMPTY));
		game.setContents(GuiHandler.setColumnsContents(game.getContents(), 6, 9, 3, 8, ElementalCheckers.GROUND));
		
		game.open(player);
		
		for(int i = 4; i < 8; i++) setGameItem(i, enemy);
		for(int i = 49; i < 53; i++) generatePiece(i);
		for(ElementalCheckersPiece piece : ElementalCheckersPiece.getAllPiecesFromMapping(this)) maxHealth += piece.health;
		
		setGameItem(GuiHandler.getSlot(1, 1), selected);
		setGameItem(GuiHandler.getSlot(1, 3), scroll == null ? ElementalCheckers.EMPTY_SCROLL : scroll.getDisplayGame());
		setGameItem(GuiHandler.getSlot(1, 4), ElementalCheckers.PASS_TURN);
		addCoins(0);
		addActionPoints(0);
		
	}
	
	public String getHealthString() {
		int health = 0;
		for(ElementalCheckersPiece piece : ElementalCheckersPiece.getAllPiecesFromMapping(this)) health += piece.health;
		return ChatColor.RED + "HP: " + health + "/" + maxHealth;
	}
	
	public boolean isAlive() {
		for(Integer i : ElementalCheckers.BOARD_SLOTS) if(getGameItem(i).getType() == Material.STAINED_CLAY && getGameItem(i).getDurability() == piece.getDurability()) return true;
		return false;
	}
		
	public void checkForDefeat() {
		if(!isAlive()) {
			ec.getOpposite(player).addCoins(16);
			ec.sleep(ChatColor.RED + "" + ChatColor.BOLD + "Game Ended", ChatColor.GRAY + "" + ChatColor.BOLD + "Reason: " + ec.getOpposite(player).player.getName() + " won.");
		}
	}
	
	public void closeGame(String message, String subMessage) {
		ElementalCheckers.GAMES.remove(player);
		player.closeInventory();
		PacketHandler.sendTitleMessage(player, message);
		PacketHandler.sendSubTitleMessage(player, subMessage);
		player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0F, 2.0F);
		ecl.end(message, subMessage);
	}
	
	public void setSelectionItem(int slot, ItemStack stack) {
		selector.setItem(slot, stack);
	}
	
	public ItemStack getSelectionItem(int slot) {
		return selector.getItem(slot);
	}
	
	public void setGameItem(int slot, ItemStack stack) {
		game.setItem(slot, stack);
	}
	
	public ItemStack getGameItem(int slot) {
		return slot < 0 || game.getSize() <= slot ? null : game.getContents()[slot];
	}
	
	public void setOppositeGameItem(int slot, ItemStack stack) {
		ec.getOpposite(player).setGameItem(getOppositeSlot(slot), stack);
	}
	
	public ItemStack getOppositeGameItem(int slot) {
		return ec.getOpposite(player).getGameItem((53 - slot) + 3);
	}
	
	public ItemStack getGameBoardItem(int slot) {
		return ElementalCheckers.BOARD_SLOTS.contains(slot) ? getGameItem(slot) : null;
	}
	
	public void clearMoveOptions() {
		for(Integer i : ElementalCheckers.BOARD_SLOTS) if(getGameItem(i).isSimilar(ElementalCheckers.CANMOVETO) || getGameItem(i).isSimilar(ElementalCheckers.CANTMOVETO)) setGameItem(i, ElementalCheckers.GROUND);
	}
	
	public void resetSelectedPiece(int slot) {
		if(slot < 0 || slot > 53 || ec.selectedSlot == -1) return;
		if(getGameItem(slot).getType() == Material.WOOL) {
			ItemStack stack = new ItemStack(getGameItem(ec.selectedSlot).clone());
			stack.setType(Material.STAINED_CLAY);
			setGameItem(slot, stack);
		}
	}
	
	public void decay() {
		
		for(Integer i : ElementalCheckers.BOARD_SLOTS) {
			
			if(!ItemHandler.getLore(getGameItem(i)).isEmpty()) {
			
				String firstLore = ItemHandler.getLoreFromLine(getGameItem(i), 0);
				
				if(firstLore.contains("Decays")) {
					
					int turns = Integer.parseInt(firstLore.substring(firstLore.indexOf("(") + 1, firstLore.indexOf(")")));
			
					if(turns - 1 > 0) {
						ItemStack stack = getGameItem(i).clone();
						ItemHandler.replaceLore(stack, firstLore, ChatColor.GRAY + "" + ChatColor.ITALIC + "Decays in (" + (turns - 1) + ") turns.");
						setGameItem(i, stack);
					} else {
						setGameItem(i, ElementalCheckers.GROUND);
					}
				
				}
			
			}
			
		}
		
	}
	
	public void subtractScrollCooldown() {
		if(scroll == null) return;
		if(getGameItem(GuiHandler.getSlot(1, 3)).getType() == Material.PAPER) setGameItem(GuiHandler.getSlot(1, 3), scroll.subtractCooldown(getGameItem(GuiHandler.getSlot(1, 3)), 1));
	}
	
	public int getCoins() {
		return DarkbrookIsland.playerdata.containsKey(player.getUniqueId() + ".gamecoins") ? DarkbrookIsland.playerdata.getInteger(player.getUniqueId() + ".gamecoins") : 0;
	}
	
	public void addCoins(int amount) {
		int amountSaved = getCoins() + amount;
		setGameItem(GuiHandler.getSlot(0, 3), ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.GOLD_NUGGET), ChatColor.GOLD + "" + ChatColor.BOLD + "Game Coins"), ChatColor.YELLOW + "Amount: " + amountSaved));
		DarkbrookIsland.playerdata.setValue(player.getUniqueId() + ".gamecoins", amountSaved);
		if(amount <= 0) return;
		player.sendMessage(ElementalCheckers.getSpacedFormat(ChatColor.GOLD, ChatColor.YELLOW, "+" + amount + " Game Coin" + (amount > 1 ? "s" : "")));
		player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 2.0F, 2.0F);
	}
	
	public boolean removeCoins(int amount) {
		int amountSaved = getCoins() - amount;
		if(amountSaved < 0) return false;
		if(ec.isGameOpen) setGameItem(GuiHandler.getSlot(0, 3), ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.GOLD_NUGGET), ChatColor.GOLD + "" + ChatColor.BOLD + "Game Coins"), ChatColor.YELLOW + "Amount: " + amountSaved));
		DarkbrookIsland.playerdata.setValue(player.getUniqueId() + ".gamecoins", amountSaved);
		player.sendMessage(ElementalCheckers.getSpacedFormat(ChatColor.GOLD, ChatColor.YELLOW, "-" + amount + " Game Coin" + (amount > 1 ? "s" : "")));
		player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 2.0F, 2.0F);
		return true;
	}
	
	public void addActionPoints(int amount) {
		int amountSaved = actionPoints + amount;
		actionPoints = amountSaved;
		setGameItem(GuiHandler.getSlot(0, 2), ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.NETHER_STAR), ChatColor.AQUA + "" + ChatColor.BOLD + "Action Points"), ChatColor.WHITE + "Amount: " + amountSaved));
		if(amount <= 0) return;
		player.sendMessage(ElementalCheckers.getSpacedFormat(ChatColor.AQUA, ChatColor.WHITE, "+" + amount + " Action Point" + (amount > 1 ? "s" : "")));
		player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 2.0F, 2.0F);
	}
	
	public boolean removeActionPoints(int amount) {
		int amountSaved = actionPoints - amount;
		if(amountSaved < 0) return false;
		actionPoints = amountSaved;
		setGameItem(GuiHandler.getSlot(0, 2), ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.NETHER_STAR), ChatColor.AQUA + "" + ChatColor.BOLD + "Action Points"), ChatColor.WHITE + "Amount: " + amountSaved));
		player.sendMessage(ElementalCheckers.getSpacedFormat(ChatColor.AQUA, ChatColor.WHITE, "-" + amount + " Action Point" + (amount > 1 ? "s" : "")));
		player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 2.0F, 2.0F);
		return true;
	}
	
	private void getMoveOption(int offset, boolean isDead) {
		
		ItemStack stack = getGameBoardItem(ec.selectedSlot + offset);
		
		if(stack != null) {
			
			ItemStack moveTo = isDead ? ElementalCheckers.CANTMOVETO.clone() : ElementalCheckers.CANMOVETO.clone();
			
			if(stack.isSimilar(ElementalCheckers.GROUND)) {
				setGameItem(ec.selectedSlot + offset, moveTo);
			} else if(getGameBoardItem(ec.selectedSlot + (offset * 2)) != null && getGameBoardItem(ec.selectedSlot + (offset * 2)).isSimilar(ElementalCheckers.GROUND)) {	
				
				int jumpSlot = ec.selectedSlot + (offset * 2);
				
				if(stack.getType() == Material.STAINED_CLAY && stack.getDurability() == ec.getOpposite(player).piece.getDurability()) {
					setGameItem(jumpSlot, moveTo);
					JumpData.setJumpData(this, jumpSlot, new JumpData(ec.selectedSlot + offset, (53 - (ec.selectedSlot + offset)) + 3, JumpReason.ATTACK));		
				} else if(ItemHandler.hasSameDisplayName(stack, ScrollOfVoid.VOID)) {
					setGameItem(jumpSlot, moveTo);
					JumpData.setJumpData(this, jumpSlot, new JumpData(ec.selectedSlot + offset, (53 - (ec.selectedSlot + offset)) + 3, JumpReason.VOID));		
				}
				
			}
			
		}
		
	}
	
	public void getMovementOptions(boolean isDead) {
		JumpData.resetJumpData(this);
		clearMoveOptions();
		resetSelectedPiece(ec.selectedSlot);
		getMoveOption(1, isDead);
		getMoveOption(-1, isDead);
		getMoveOption(9, isDead);
		getMoveOption(-9, isDead);
	}
	
	public void movePiece(int slot) {
		setGameItem(slot, getGameItem(ec.selectedSlot));
		setGameItem(ec.selectedSlot, ElementalCheckers.GROUND);
		setOppositeGameItem(slot, warrior);
		setOppositeGameItem(ec.selectedSlot, ElementalCheckers.GROUND);
		JumpData.update(this, slot);
		ElementalCheckersPiece.movePieceOnMapping(this, ec.selectedSlot, slot);
		JumpData.resetJumpData(this);
	}
	
	public int getRandomEmptyBoardSlot() {
		List<Integer> openSlots = new ArrayList<Integer>();
		for(Integer i : ElementalCheckers.BOARD_SLOTS) if(getGameItem(i).isSimilar(ElementalCheckers.GROUND)) openSlots.add(i);
		return openSlots.get(MathHandler.RANDOM.nextInt(openSlots.size()));
	}
	
	public ItemStack[] getContents() {
		ItemStack[] contents = new ItemStack[game.getSize()];
		for(int i = 0; i < game.getSize(); i++) contents[i] = game.getItem(i);
		return contents;
	}

}
