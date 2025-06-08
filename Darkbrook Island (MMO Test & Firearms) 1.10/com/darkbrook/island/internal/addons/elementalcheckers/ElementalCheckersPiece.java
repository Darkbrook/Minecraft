package com.darkbrook.island.internal.addons.elementalcheckers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.CompressedSound;
import com.darkbrook.island.library.misc.MathHandler;

public class ElementalCheckersPiece {
	
	private static final HashMap<ElementalCheckersPlayer, HashMap<Integer, ElementalCheckersPiece>> PIECES = new HashMap<ElementalCheckersPlayer, HashMap<Integer, ElementalCheckersPiece>>();
	
	public static final int MIN_HEALTH = 20;
	public static final int MAX_HEALTH = 80;
	public static final int MIN_DEFENCE = 1;
	public static final int MAX_DEFENCE = 4;
	public static final int MIN_MIN_DAMAMGE = 2;
	public static final int MIN_MAX_DAMAMGE = 6;
	public static final int MAX_MIN_DAMAMGE = 8;
	public static final int MAX_MAX_DAMAMGE = 12;
	public static final int MIN_LUCK = 0;
	public static final int MAX_LUCK = 2;
	
	public ItemStack stack;
	public int health;
	public int defence;
	public int damageMin;
	public int damageMax;
	public int luck;
	public String condition;

	public static ElementalCheckersPiece generatePiece(ItemStack stack) {
		return new ElementalCheckersPiece(stack, MathHandler.getRandomNumber(MIN_HEALTH, MAX_HEALTH), MathHandler.getRandomNumber(MIN_DEFENCE, MAX_DEFENCE), MathHandler.getRandomNumber(ElementalCheckersPiece.MIN_MIN_DAMAMGE, ElementalCheckersPiece.MIN_MAX_DAMAMGE), MathHandler.getRandomNumber(MAX_MIN_DAMAMGE, MAX_MAX_DAMAMGE), MathHandler.getRandomNumber(MIN_LUCK, MAX_LUCK));
	}
	
	private static HashMap<Integer, ElementalCheckersPiece> getPieceMapping(ElementalCheckersPlayer player) {
		return PIECES.containsKey(player) ? PIECES.get(player) : new HashMap<Integer, ElementalCheckersPiece>();
	}
	
	public static void addPieceToMapping(ElementalCheckersPlayer player, int slot, ElementalCheckersPiece piece) {
		HashMap<Integer, ElementalCheckersPiece> pieces = getPieceMapping(player);
		pieces.put(slot, piece);
		PIECES.put(player, pieces);
	}

	public static void movePieceOnMapping(ElementalCheckersPlayer player, int slot, int slotNew) {
		HashMap<Integer, ElementalCheckersPiece> pieces = getPieceMapping(player);
		ElementalCheckersPiece piece = pieces.get(slot);
		pieces.remove(slot);
		pieces.put(slotNew, piece);
		PIECES.put(player, pieces);
	}
	
	public static ElementalCheckersPiece getPieceFromMapping(ElementalCheckersPlayer player, int slot) {
		HashMap<Integer, ElementalCheckersPiece> pieces = getPieceMapping(player);
		return pieces.containsKey(slot) ? pieces.get(slot) : null;
	}
	
	public static List<ElementalCheckersPiece> getAllPiecesFromMapping(ElementalCheckersPlayer player) {
		List<ElementalCheckersPiece> list = new ArrayList<ElementalCheckersPiece>();
		for(int i = 0; i < 54; i++) if(getPieceFromMapping(player, i) != null) list.add(getPieceFromMapping(player, i));
		return list;
	}
	
	public static void removePieceFromMapping(ElementalCheckersPlayer player, int slot) {
		HashMap<Integer, ElementalCheckersPiece> pieces = getPieceMapping(player);
		if(pieces.containsKey(slot)) pieces.remove(slot);
		PIECES.put(player, pieces);
	}
	
	public ElementalCheckersPiece(ItemStack stack, int health, int defence, int damageMin, int damageMax, int luck) {
		this.stack = stack;
		this.health = health;
		this.defence = defence;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
		this.luck = luck;
		this.condition = getCondition();
	}

	public ItemStack getHidden() {
		return stack.clone();
	}
	
	public String getCondition() {
		
		int conditionValue = MathHandler.getPercent((health * 8) + (defence * 8) + (damageMin * 2) + (damageMax * 4) + (luck * 16), (MAX_HEALTH * 8) + (MAX_DEFENCE * 8) + (MIN_MAX_DAMAMGE * 2) + (MAX_MAX_DAMAMGE * 4) + (MAX_LUCK * 16));
		String condition = "";

		if(conditionValue > 0 && conditionValue <= 40) {
			condition = ChatColor.GRAY + "Poor";
		} else if(conditionValue > 40 && conditionValue <= 60) {
			condition += ChatColor.YELLOW + "Average";
		} else if(conditionValue > 60 && conditionValue <= 80) {
			condition += ChatColor.GREEN + "Hearty";
		} else if(conditionValue > 80 && conditionValue <= 100) {
			condition += ChatColor.GOLD + "Pristine";
		}
		
		return condition;
		
	}
	
	public ItemStack getShown() {
		ItemStack stack = ItemHandler.appendLore(getHidden().clone(), ChatColor.RED + "HP: " + health);
		stack = ItemHandler.appendLore(stack, ChatColor.RED + "DEF: " + defence);
		stack = ItemHandler.appendLore(stack, ChatColor.RED + "DMG: " + damageMin + "-" + damageMax);
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "LUC: " + luck);
		stack = ItemHandler.appendLore(stack, ChatColor.AQUA + "CON: " + condition);
		return stack;
	}
	
	public ItemStack getDead() {
		return ElementalCheckers.addDecay(ItemHandler.setDisplayName(new ItemStack(Material.STAINED_GLASS, 1, stack.getDurability()), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Dead " + ChatColor.stripColor(ItemHandler.getDisplayName(stack))), 32);
	}
	
	public void subtractHealth(int amount) {
		this.health -= amount;
	}
	
	public ItemStack updatePiece(boolean hidden) {
		return health > 0 ? (hidden ? getHidden() : getShown()) : getDead();
	}
	
	public boolean onDeath(ElementalCheckersPlayer client, int slot, boolean isBody) {
		
		if(isBody) {
			
			if(updatePiece(false).isSimilar(getDead())) {
				client.ec.getOpposite(client.player).addCoins(2);
				client.ec.getOpposite(client.player).addActionPoints(16);
				removePieceFromMapping(client, slot);
				client.ec.updateTurnDisplay();
				return true;
			}
			
		} else {
			removePieceFromMapping(client, slot);
			client.ec.updateTurnDisplay();
			return true;
		}
		
		return false;
		
	}
	
	public void damage(ElementalCheckersPlayer client, int slot, ElementalCheckersPiece damager) {
		
		int damage = MathHandler.getRandomNumber(damager.damageMin, damager.damageMax);
		int damageActual = damage + damager.luck;
				
		if(damage > (defence + luck)) {
			subtractHealth(damageActual);
			if(!onDeath(client, slot, true)) client.ec.getOpposite(client.player).addActionPoints(1);
			client.setGameItem(slot, updatePiece(false));
			client.setOppositeGameItem(slot, updatePiece(true));
			client.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Hit For " + damageActual), 
			References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Hit For " + damageActual), 
			References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, client.player.getName() + "'s Warrior Hit For " + damageActual),
			new CompressedSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.0F));
		} else {
			client.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Blocked"), 
			References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Blocked"), 
			References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, client.player.getName() + "'s Warrior Blocked"), 
			new CompressedSound(Sound.BLOCK_ANVIL_HIT, 1.0F, 0.0F));
		}
		
		client.player.updateInventory();
		client.ec.getOpposite(client.player).player.updateInventory();

	}
	
	public void voidDeath(ElementalCheckersPlayer client, int slot, int mappingSlot, boolean canSurvive) {
		
		int roll = (MathHandler.RANDOM.nextInt(20) + 1) + (defence + luck);
		
		if(roll > 10 && canSurvive) {
			
			if(client == client.ec.playerCurrent) {
				client.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Successfully Jumped"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Successfully Jumped"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, client.player.getName() + "'s Warrior Successfully Jumped"),
				new CompressedSound(Sound.ENTITY_HORSE_JUMP, 1.0F, 0.0F));
			} else {
				client.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Successfully Jumped"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Successfully Jumped"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, client.player.getName() + "'s Warrior Successfully Jumped"),
				new CompressedSound(Sound.ENTITY_HORSE_JUMP, 1.0F, 0.0F));
			}
			
		} else {
			
			client.setGameItem(slot, ElementalCheckers.GROUND);
			client.setOppositeGameItem(slot, ElementalCheckers.GROUND);
			onDeath(client, mappingSlot, false);
			
			if(client == client.ec.playerCurrent) {
				client.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Fell Into The Void"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Fell Into The Void"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, client.player.getName() + "'s Warrior Fell Into The Void"),
				new CompressedSound(Sound.ENTITY_SHEEP_HURT, 1.0F, 0.0F));
			} else {
				client.ec.sendToAll(References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Opponent Fell Into The Void"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, "Warrior Fell Into The Void"), 
				References.getSpacedFormat(ChatColor.DARK_RED, ChatColor.RED, client.player.getName() + "'s Warrior Fell Into The Void"),
				new CompressedSound(Sound.ENTITY_SHEEP_HURT, 1.0F, 0.0F));
			}
			
		}
		
	}
	
}
