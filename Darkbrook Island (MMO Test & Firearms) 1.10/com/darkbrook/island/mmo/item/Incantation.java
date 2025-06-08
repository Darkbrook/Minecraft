package com.darkbrook.island.mmo.item;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.library.item.ItemHandler;

public class Incantation {
	
	public static boolean hasIncantation(ItemStack item, String incantation) {
		return item.hasItemMeta() && item.getItemMeta().hasLore() && ItemHandler.hasLoreFromLine(item, incantation);
	}
	
	@SuppressWarnings("deprecation")
	public static void apply(InventoryClickEvent event, String appliedToType, ItemStack incantationItem, String incantation) {
		ItemStack cursor = event.getCursor();
		ItemStack item = event.getCurrentItem();
		if(item == null || cursor == null) return;
		if(event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) return;
		if(!ItemHandler.compareDisplayNames(cursor, incantationItem) || !item.getType().name().contains(appliedToType) || !hasIncantation(item, ChatColor.GRAY + "INC: Empty")) return;
		Player player = (Player) event.getWhoClicked();
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHORUS_FLOWER_DEATH, 1.0F, 2.0F);
		player.getWorld().spigot().playEffect(player.getLocation(), Effect.WITCH_MAGIC, 0, 0, 0.5F, 1.0F, 0.5F, 0, 100, 100);
		event.setCursor(null);
		ItemStack applied = ItemHandler.replaceLore(item, ChatColor.GRAY + "INC: Empty", incantation);
		applied.addEnchantment(Enchantment.DURABILITY, 3);
		ItemHandler.addFlag(applied, ItemFlag.HIDE_ENCHANTS);
		event.setCurrentItem(applied);
		event.setCancelled(true);
		player.updateInventory();
	}

}
