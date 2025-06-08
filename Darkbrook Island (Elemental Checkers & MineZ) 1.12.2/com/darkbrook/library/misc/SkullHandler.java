package com.darkbrook.library.misc;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkullHandler {
	
	public static String getEncodedValue(String in) {
		return new String(Base64.getEncoder().encode(in.getBytes()));
	}
	
	public static String getDecodedValue(String in) {
		return new String(Base64.getDecoder().decode(in.getBytes()));
	}

	public static ItemStack getSkull(String name, String link) {
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", getEncodedValue("{\"textures\":{\"SKIN\":{\"url\":\"" + link + "\"}}}")));
		
		try {
			Field field = meta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(meta, profile);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		meta.setDisplayName(name);
		
		skull.setItemMeta(meta);
		return skull;
		
	}
	
	public static ItemStack getSkullFromHash(String name, String link) {
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", link));
		
		try {
			Field field = meta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(meta, profile);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		meta.setDisplayName(name);
		
		skull.setItemMeta(meta);
		return skull;
		
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getSkullFromPlayerName(Player player) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player.getName());
		skull.setItemMeta(meta);
		return skull;
	}
	
}
