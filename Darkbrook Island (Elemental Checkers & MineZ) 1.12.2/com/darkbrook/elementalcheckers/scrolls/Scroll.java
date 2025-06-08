package com.darkbrook.elementalcheckers.scrolls;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.darkbrookisland.DarkbrookIsland;
import com.darkbrook.elementalcheckers.ElementalCheckersPlayer;
import com.darkbrook.library.gui.GuiHandler;
import com.darkbrook.library.item.ItemHandler;

public abstract class Scroll {
	
	private static int scrollCount = 0;
	private static final HashMap<String, Scroll> SCROLLS = new HashMap<String, Scroll>();
	private static final Queue<Scroll> SCROLLSQUEUE = new LinkedList<Scroll>();
	public static final HashMap<ElementalCheckersPlayer, Queue<Scroll>> SCROLLS_PLAYER = new HashMap<ElementalCheckersPlayer, Queue<Scroll>>();
	
	private ItemStack scroll;
	private ChatColor color;
	private ChatColor subColor;
	private String name;
	private String use;
	private int cost;
	private int actionPoints;
	private int cooldown;
	private int count;
	
	public Scroll(ChatColor color, ChatColor subColor, String name, String use, int cost, int actionPoints, int cooldown) {
		scrollCount++;
		count = scrollCount;
		SCROLLS.put(name, this);
		SCROLLSQUEUE.add(this);
		this.scroll = ItemHandler.setDisplayName(new ItemStack(Material.EMPTY_MAP), color + "" + ChatColor.BOLD + "" + name);
		this.color = color;
		this.subColor = subColor;
		this.name = name;
		this.use = use;
		this.cost = cost;
		this.actionPoints = actionPoints;
		this.cooldown = cooldown;
	}
	
	public int getCost() {
		return cost;
	}
	
	public ItemStack getDisplayLocked(ElementalCheckersPlayer player) {
		ItemStack stack = scroll.clone();
		stack = ItemHandler.appendLore(stack, subColor + use);
		stack = ItemHandler.appendLore(stack, ChatColor.AQUA + "Action Points: " + ChatColor.WHITE + actionPoints);
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "Cooldown: " + ChatColor.YELLOW + cooldown + " turns.");
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "Cost: " + ChatColor.YELLOW + cost + " Game Coins");
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "You Have: " + ChatColor.YELLOW + player.getCoins());
		return stack;
	}

	public ItemStack getDisplayUnlocked() {
		ItemStack stack = scroll.clone();
		stack = ItemHandler.appendLore(stack, subColor + use);
		stack = ItemHandler.appendLore(stack, ChatColor.AQUA + "Action Points: " + ChatColor.WHITE + actionPoints);
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "Cooldown: " + ChatColor.YELLOW + cooldown + " turns.");
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "Unlocked");
		return stack;
	}
	
	public ItemStack getDisplayGame() {
		ItemStack stack = scroll.clone();
		stack = ItemHandler.appendLore(stack, subColor + use);
		stack = ItemHandler.appendLore(stack, ChatColor.AQUA + "Action Points: " + ChatColor.WHITE + actionPoints);
		stack = ItemHandler.appendLore(stack, ChatColor.GOLD + "Cooldown: " + ChatColor.YELLOW + cooldown + " turns.");
		return stack;	}
	
	private ItemStack getCooldown(int cooldown) {
		return ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.PAPER), color + "" + ChatColor.BOLD + "" + name), ChatColor.GOLD + "Can be used in (" + cooldown + ") turns.");
	}
	
	public ItemStack subtractCooldown(ItemStack stack, int amount) {
		String lore = ItemHandler.getLoreFromLine(stack, 0);
		int cooldown = Integer.parseInt(lore.substring(lore.indexOf("(") + 1, lore.indexOf(")")));
		return cooldown - amount > 0 ? getCooldown(cooldown - amount) : getDisplayGame();
	}
	
	public void unlock(Player player) {
		DarkbrookIsland.playerdata.setValue(player.getUniqueId() + ".scrolls." + name.replace(" ", "").toLowerCase(), true);
	}
	
	public static boolean hasScroll(Player player, ItemStack stack) {
		return hasScroll(player, ChatColor.stripColor(ItemHandler.getDisplayName(stack)).replaceAll(" ", "").toLowerCase());
	}
	
	public static boolean hasScroll(Player player, String name) {
		return DarkbrookIsland.playerdata.containsKey(player.getUniqueId() + ".scrolls." + name);
	}
	
	public static Scroll getScroll(ItemStack stack) {
		String name = ChatColor.stripColor(ItemHandler.getDisplayName(stack));
		return SCROLLS.containsKey(name) ? SCROLLS.get(name) : null;
	}
	
	public static ItemStack cycleScrolls(ElementalCheckersPlayer player) {
		
		if(!SCROLLS_PLAYER.containsKey(player) || SCROLLS_PLAYER.get(player).isEmpty()) {
			Queue<Scroll> scrolls = new LinkedList<Scroll>();
			scrolls.addAll(SCROLLSQUEUE);
			SCROLLS_PLAYER.put(player, scrolls);
		}
		
		Scroll scroll = SCROLLS_PLAYER.get(player).poll();
		return hasScroll(player.player, scroll.name.replaceAll(" ", "").toLowerCase()) || scroll.cost == 0 ? ItemHandler.appendLore(scroll.getDisplayUnlocked(), ChatColor.DARK_GRAY + "Scroll (" + scroll.count + "/" + scrollCount + ")") : ItemHandler.appendLore(scroll.getDisplayLocked(player), ChatColor.DARK_GRAY + "Scroll (" + scroll.count + "/" + scrollCount + ")");
		
	}
		
	public void use(ElementalCheckersPlayer player) {
		if(!condition(player) || !player.removeActionPoints(actionPoints)) return;
		player.setGameItem(GuiHandler.getSlot(1, 3), getCooldown(cooldown));
		player.clearMoveOptions();
		onUse(player);
	}
	
	public abstract boolean condition(ElementalCheckersPlayer player);
	protected abstract void onUse(ElementalCheckersPlayer player);
	
}
