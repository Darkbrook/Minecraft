package com.darkbrook.island.bunkerworld;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.References;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.CompressedSound;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.library.misc.PacketHandler;

public enum GunType {
	
	M2HYRDE(new CompressedSound(Sound.ENTITY_FIREWORK_BLAST, 2.0F, 0.0F), new ItemStack(Material.WOOD_HOE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "M2 Hyde", AmmoTypes.SUB_MACHINE, 20, 4, 6, 8, 3, 2, true),
	THOMPSON(new CompressedSound(Sound.ENTITY_FIREWORK_BLAST, 2.0F, 2.0F), new ItemStack(Material.STONE_HOE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Thompson", AmmoTypes.SUB_MACHINE, 30, 2, 4, 6, 4, 2, true),
	M50REISING(new CompressedSound(Sound.ENTITY_FIREWORK_LARGE_BLAST, 2.0F, 2.0F), new ItemStack(Material.IRON_HOE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "M50 Reising", AmmoTypes.SUB_MACHINE, 12, 4, 8, 10, 3, 4, true),
	A5(new CompressedSound(Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 0.0F), new ItemStack(Material.WOOD_PICKAXE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Browning Automatic 5", AmmoTypes.SHOTGUN, 5, 20, 8, 16, 2, 1, false),
	SRINGFIELD(new CompressedSound(Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 2.0F, 2.0F), new ItemStack(Material.WOOD_AXE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Springfield", AmmoTypes.RIFLE, 5, 40, 10, 20, 4, 8, true),
	BAZOOKA(new CompressedSound(Sound.ENTITY_GHAST_SHOOT, 2.0F, 2.0F), new ItemStack(Material.WOOD_SPADE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Bazooka", AmmoTypes.ROCKET, 1, 0, 20, 40, 0, 0, false);
	
	private static final HashMap<Player, Integer> ZOOM = new HashMap<Player, Integer>();
	private CompressedSound sound;
	private ItemStack stack;
	private String name;
	private AmmoTypes ammo;
	private int magazineSize;
	private int cooldown;
	private int minDamamge;
	private int maxDamamge;
	private double velocity;
	private int zoom;
	private boolean hasMagazine;
	
	private GunType(CompressedSound sound, ItemStack stack, String name, AmmoTypes ammo, int magazineSize, int cooldown, int minDamamge, int maxDamamge, double velocity, int zoom, boolean hasMagazine) {
		this.sound = sound;
		this.stack = stack;
		this.name = name;
		this.ammo = ammo;
		this.magazineSize = magazineSize;
		this.cooldown = cooldown;
		this.minDamamge = minDamamge;
		this.maxDamamge = maxDamamge;
		this.velocity = velocity;
		this.zoom = zoom;
		this.hasMagazine = hasMagazine;
	}
	
	public int getDamage() {
		return MathHandler.getRandomNumber(minDamamge, maxDamamge);
	}
	
	public int getZoom() {
		return zoom;
	}
	
	public boolean hasMagazine() {
		return hasMagazine;
	}
	
	public ItemStack getItemStack() {
		return ItemHandler.addFlag(ItemHandler.appendLore(ItemHandler.appendLore(ItemHandler.setDisplayName(stack.clone(), name), ChatColor.GRAY + "Cooldown: (0/" + cooldown + ")"), ChatColor.GRAY + "Magazine: (" + magazineSize + "/" + magazineSize + ")"), ItemFlag.HIDE_ATTRIBUTES);
	}
	
	public ItemStack getAmmo() {
		return ammo.getItemStack();
	}
	
	public ItemStack getFullMagazine() {
		return getMagazine(magazineSize);
	}
	
	public ItemStack getMagazine(int size) {
		return ItemHandler.appendLore(ItemHandler.setDisplayName(new ItemStack(Material.POWERED_MINECART), name + " Magazine"), ChatColor.GRAY + "Rounds: (" + size + "/" + magazineSize + ")");
	}
	
	public int getMagazineRounds(ItemStack stack) {
		String lore = ItemHandler.getLoreFromLine(stack, 0);
		return Integer.parseInt(lore.substring(lore.indexOf("(") + 1, lore.indexOf("/")));
	}
	
	public ItemStack addAmmoToMagazine(ItemStack stack, int amount) {
		int current = getMagazineRounds(stack);
		if(current + amount > magazineSize) return stack;
		return getMagazine(current + amount);
	}
	
	public int getMagazineCurrent(ItemStack stack) {
		String lore = ItemHandler.getLoreFromLine(stack, 1);
		return Integer.parseInt(lore.substring(lore.indexOf("(") + 1, lore.indexOf("/")));
	}
	
	public ItemStack setAmmo(ItemStack stack, int amount) {
		return ItemHandler.setLore(stack, ChatColor.GRAY + "Magazine: (" + amount + "/" + magazineSize + ")", 1);
	}
	
	public ItemStack subtractAmmo(ItemStack stack, int amount) {
		int current = getMagazineCurrent(stack);
		if(current - amount < 0) return stack;
		return setAmmo(stack, current - amount);
	}
	
	public ItemStack addAmmo(ItemStack stack, int amount) {
		int current = getMagazineCurrent(stack);
		if(current + amount > magazineSize) return stack;
		return setAmmo(stack, current + amount);
	}
	
	public int getCooldownCurrent(ItemStack stack) {
		String lore = ItemHandler.getLoreFromLine(stack, 0);
		return Integer.parseInt(lore.substring(lore.indexOf("(") + 1, lore.indexOf("/")));
	}	
	
	public ItemStack subtractCooldown(ItemStack stack, int amount) {
		int current = getCooldownCurrent(stack);
		if(current - amount < 0) return stack;
		return ItemHandler.replaceLore(stack, ChatColor.GRAY + "Cooldown: (" + current + "/" + cooldown + ")", ChatColor.GRAY + "Cooldown: (" + (current - amount) + "/" + cooldown + ")");
	}
	
	public ItemStack resetCooldown(ItemStack stack) {
		int current = getCooldownCurrent(stack);
		return ItemHandler.replaceLore(stack, ChatColor.GRAY + "Cooldown: (" + current + "/" + cooldown + ")", ChatColor.GRAY + "Cooldown: (" + cooldown + "/" + cooldown + ")");
	}
	
	public void shoot(ItemStack stack, Player player) {
		if(!ItemHandler.compareDisplayNames(stack, name) || (getMagazineCurrent(stack) - 1) < 0 || getCooldownCurrent(stack) > 0) return;
		ammo.fire(player, name, velocity);
		sound.playSound(player.getLocation());
		player.getInventory().setItemInMainHand(subtractAmmo((getMagazineCurrent(stack) - 1) == 0 ? player.getInventory().getItemInMainHand() : resetCooldown(player.getInventory().getItemInMainHand()), 1));
	}

	public void removeZoom(Player player) {
		player.removePotionEffect(PotionEffectType.SLOW);
		ZOOM.put(player, 0);
	}
	
	public void giveZoom(Player player) {
		removeZoom(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, zoom - 1, false, false));
		ZOOM.put(player, zoom);
	}
	
	public void zoom(Player player) {
		if(ItemHandler.compareDisplayNames(player.getInventory().getItemInMainHand(), name) && zoom > 0) {
			if(!player.hasPotionEffect(PotionEffectType.SLOW)) giveZoom(player); else removeZoom(player);	
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 0.5F, 0.0F);
		}
	}

	@SuppressWarnings("deprecation")
	public void reload(InventoryClickEvent event, ItemStack gun, ItemStack ammo, Player player) {
		if(gun == null || ammo == null) return;
		
		if(ItemHandler.compareDisplayNames(gun, name)) {
		
			if(ammo.isSimilar(this.ammo.getItemStack()) && (getMagazineCurrent(gun) + 1) <= magazineSize) {
				event.setCurrentItem(addAmmo(gun, 1));
				event.setCursor(ItemHandler.subtractAmount(event.getCursor(), 1));
				event.getView().getPlayer().getWorld().playSound(event.getView().getPlayer().getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 0.5F, 0.0F);
				player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Reloaded Ammo (" + getMagazineCurrent(event.getCurrentItem()) + "/" + magazineSize + ")"));
				event.setCancelled(true);
			} else if(ItemHandler.compareDisplayNames(ammo, getMagazine(magazineSize))) {
				int ammoAmount = getMagazineCurrent(gun);
				event.setCurrentItem(setAmmo(gun, getMagazineRounds(ammo)));
				event.setCursor(getMagazine(ammoAmount));
				event.getView().getPlayer().getWorld().playSound(event.getView().getPlayer().getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 0.5F, 0.0F);
				player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Reloaded Ammo (" + getMagazineCurrent(event.getCurrentItem()) + "/" + magazineSize + ")"));
				event.setCancelled(true);
			}
		
		} else if(ItemHandler.compareDisplayNames(gun, getMagazine(magazineSize)) && ItemHandler.compareDisplayNames(ammo, this.ammo.getItemStack()) && (getMagazineRounds(gun) + 1) <= magazineSize) {
			event.setCurrentItem(addAmmoToMagazine(gun, 1));
			event.setCursor(ItemHandler.subtractAmount(event.getCursor(), 1));
			event.getView().getPlayer().getWorld().playSound(event.getView().getPlayer().getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 0.5F, 0.0F);
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Reloaded Ammo (" + getMagazineRounds(event.getCurrentItem()) + "/" + magazineSize + ")"));
			event.setCancelled(true);
		}
		
	}
	
	public String getProjectileName() {
		return ammo.getProjectileName(name);
	}

	private static String getFormatedText(String message) {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "<<<" + ChatColor.GRAY + message + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + ">>>";
	}
	
	public static void update(Player player) {
		
		ItemStack hand = player.getInventory().getItemInMainHand();
		GunType gun = null;
		
		for(GunType type : GunType.values()) {
			
			if(hand != null && hand.getType() == type.getItemStack().getType() && ItemHandler.compareDisplayNames(hand, type.name)) {
				gun = type;
				if(type.getCooldownCurrent(hand) > 0) player.getInventory().setItemInMainHand(type.subtractCooldown(hand, 1));
			}
				
		}

		if(gun == null || !ZOOM.containsKey(player) || ZOOM.get(player) == 0 || ZOOM.get(player) != gun.zoom) player.removePotionEffect(PotionEffectType.SLOW);
		
		if(gun != null) {
			PacketHandler.sendActionMessage(player, getFormatedText("(" + gun.getMagazineCurrent(hand) + "/" + gun.magazineSize + ") - (" + (gun.getMagazineCurrent(hand) > 0 ? (gun.getCooldownCurrent(hand) > 0 ? ChatColor.RED + "C" : ChatColor.GREEN + "R") : ChatColor.RED + "E") + ChatColor.GRAY + ")"));
		} else {
			PacketHandler.sendActionMessage(player, "");
		}
		
	}
	
	public static void shoot(PlayerInteractEvent event) {
		ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();
		if(event.getAction() != Action.RIGHT_CLICK_AIR || hand == null) return;
		for(GunType type : GunType.values()) type.shoot(hand, event.getPlayer());	
	}
	
	public static void zoom(PlayerInteractEvent event) {
		ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();
		if(event.getAction() != Action.LEFT_CLICK_AIR || hand == null) return;
		for(GunType type : GunType.values()) type.zoom(event.getPlayer());	
	}
	
	public static void damage(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Projectile)) return;
		for(GunType type : GunType.values()) if(event.getDamager().getCustomName().equals(type.getProjectileName())) event.setDamage(type.getDamage());		
	}
	
	public static void reload(InventoryClickEvent event) {
		if(event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) return;
		for(GunType type : GunType.values()) type.reload(event, event.getCurrentItem(), event.getCursor(), (Player) event.getView().getPlayer());
	}
		
	public static void antiPearl(PlayerTeleportEvent event) {
		if(event.getCause() != TeleportCause.ENDER_PEARL) return;
		event.setCancelled(true);
	}
	
}
