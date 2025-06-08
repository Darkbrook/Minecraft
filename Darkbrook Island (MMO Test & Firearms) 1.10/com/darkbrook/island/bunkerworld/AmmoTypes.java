package com.darkbrook.island.bunkerworld;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.CompressedSound;
import com.darkbrook.island.library.misc.LocationHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public enum AmmoTypes {

	SUB_MACHINE(Snowball.class,  EntityType.SNOWBALL, AmmoCatagory.REGULAR, new ItemStack(Material.INK_SACK, 1, (byte) 11), ChatColor.GOLD + "" + ChatColor.BOLD + ".45 ACP", ChatColor.YELLOW + "Used in sub-machine guns."),
	SHOTGUN(Snowball.class, EntityType.SNOWBALL, AmmoCatagory.SHOTGUN, new ItemStack(Material.MELON_SEEDS), ChatColor.DARK_RED + "" + ChatColor.BOLD + "Buckshot", ChatColor.RED + "Used in shotguns."),
	RIFLE(EnderPearl.class, EntityType.ENDER_PEARL, AmmoCatagory.RIFLE, new ItemStack(Material.GHAST_TEAR), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + ".30-06", ChatColor.GRAY + "Used in rifles."),
	ROCKET(WitherSkull.class, EntityType.WITHER_SKULL, AmmoCatagory.REGULAR, new ItemStack(Material.BLAZE_ROD), ChatColor.GOLD + "" + ChatColor.BOLD + "Rocket", ChatColor.YELLOW + "Used in bazookas.");
	
	private static final double SPREAD = 1.5;
	private Class<? extends Projectile> projectile;
	private EntityType type;
	private AmmoCatagory pattern;
	private ItemStack stack;
	private String name;
	private String lore;
	
	private AmmoTypes(Class<? extends Projectile> projectile, EntityType type, AmmoCatagory pattern, ItemStack stack, String name, String lore) {
		this.projectile = projectile;
		this.type = type;
		this.pattern = pattern;
		this.stack = stack;
		this.name = name;
		this.lore = lore;
	}
	
	public ItemStack getItemStack() {
		ItemStack stack = ItemHandler.setDisplayName(this.stack.clone(), name);
		stack = ItemHandler.appendLore(stack, lore);
		return stack;
	}
	
	public String getProjectileName(String gunName) {
		return ChatColor.stripColor(name.replace(" ", "").toLowerCase() + gunName.replace(" ", "").toLowerCase());
	}
	
	public Class<? extends Projectile> getProjectileClass() {
		return projectile;
	}
	
	private Projectile spawnProjectile(Player player, String gunName, double velocityMod) {
		Projectile projectile = player.launchProjectile(getProjectileClass(), player.getLocation().getDirection().normalize().multiply(velocityMod));
		projectile.setCustomName(getProjectileName(gunName));
		projectile.setCustomNameVisible(false);
		return projectile;
	}
	
	private double getRandomOffset() {
		return Math.random() * SPREAD - (SPREAD / 2);
	}
	
	private Projectile spawnProjectileShotgun(Vector velocity, Location location, String gunName) {
		Projectile projectile = (Projectile) location.getWorld().spawnEntity(LocationHandler.getLocationOffset(location, getRandomOffset(), getRandomOffset(), getRandomOffset()), type);
		projectile.setCustomName(getProjectileName(gunName));
		projectile.setCustomNameVisible(false);
		projectile.setVelocity(velocity);
		return projectile;
	}
	
	private void playDelayedSound(CompressedSound sound, Player player, int delay) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				sound.playSound(player.getLocation());
			}
			
		}, delay);
		
	}
	
	public void fire(Player player, String gunName, double velocityMod) {
		
		switch(pattern) {
			case SHOTGUN: 
				
				Projectile projectile = spawnProjectile(player, gunName, velocityMod);
				
				UpdateHandler.delay(new UpdateListener() {

					@Override
					public void onUpdate() {
						for(int i = 0; i < 16; i++) spawnProjectileShotgun(projectile.getVelocity(), projectile.getLocation(), gunName);
					}
					
				}, 2);

				break;
			case RIFLE:
				
				spawnProjectile(player, gunName, velocityMod);
				
				playDelayedSound(new CompressedSound(Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 2.0F, 2.0F), player, 6);
				playDelayedSound(new CompressedSound(Sound.BLOCK_IRON_TRAPDOOR_OPEN, 2.0F, 2.0F), player, 12);
				playDelayedSound(new CompressedSound(Sound.BLOCK_IRON_DOOR_CLOSE, 2.0F, 2.0F), player, 20);
				playDelayedSound(new CompressedSound(Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 2.0F, 2.0F), player, 28);
				playDelayedSound(new CompressedSound(Sound.BLOCK_IRON_TRAPDOOR_OPEN, 2.0F, 2.0F), player, 34);

				
				break;
			default: 
				spawnProjectile(player, gunName, velocityMod);
				break;
		}
		
	}
		
}
