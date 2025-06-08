package com.darkbrook.island.library.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Player;

import com.darkbrook.island.References;
import com.darkbrook.island.library.config.Config;
import com.darkbrook.island.library.config.WorldConfig;
import com.darkbrook.island.library.misc.PacketHandler;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_10_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_10_R1.PlayerInteractManager;

public class Npc extends EntityPlayer {
	
	public static final HashMap<Player, ArrayList<Npc>> NPC_CLIENT_LOADED = new HashMap<Player, ArrayList<Npc>>();
	public static final HashMap<Player, HashMap<Integer, Npc>> NPC_CLIENT = new HashMap<Player, HashMap<Integer, Npc>>();
	public static final HashMap<Player, HashMap<Integer, Boolean>> NPC_VISIBILITY = new HashMap<Player, HashMap<Integer, Boolean>>();
	public static final int VIEW_RANGE = 128;
	
	public Location location;
	public int id;
	
	public static Config getConfig(World world) {
		return WorldConfig.getConfig(world, "npcdata");
	}
	
	public Npc(World world, String name) {
		super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) world).getHandle(), new GameProfile(UUID.randomUUID(), name),  new PlayerInteractManager(((CraftWorld) world).getHandle()));
	}
	
	public Npc(Player player, int id) {
		super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) player.getWorld()).getHandle(), new GameProfile(UUID.randomUUID(), getConfig(player.getWorld()).getString(id + ".name")),  new PlayerInteractManager(((CraftWorld) player.getWorld()).getHandle()));
	}
	
	private void updateHidePackets(Player player) {
		PacketHandler.sendPacket(player, new PacketPlayOutEntityDestroy(this.getId()));
	}
	
	private void updateSpawnPackets(Player player, byte headrotation) {
		PacketHandler.sendPacket(player, new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, this));
		PacketHandler.sendPacket(player, new PacketPlayOutNamedEntitySpawn(this));
		PacketHandler.sendPacket(player, new PacketPlayOutEntityHeadRotation(this, headrotation));		
		PacketHandler.sendPacket(player, new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, this));
	}
	
	public void unload(Player player) {
		
		updateHidePackets(player);
		
		ArrayList<Npc> npcs = NPC_CLIENT_LOADED.get(player);
		npcs.remove(this);
		NPC_CLIENT_LOADED.put(player, npcs);
		
		HashMap<Integer, Boolean> NPC_VISBILITY_MAPPING = NPC_VISIBILITY.containsKey(player) ? NPC_VISIBILITY.get(player) : new HashMap<Integer, Boolean>();
		NPC_VISBILITY_MAPPING.put(id, false);
		NPC_VISIBILITY.put(player, NPC_VISBILITY_MAPPING);
		
	}
		
	public void load(Player player, int id) {
		
		Config config = getConfig(player.getWorld());
		this.location = config.getLocation(id + ".location");
		this.teleportTo(location, false);
		byte headrotation = (byte) (config.getInt(id + ".yawmask") * 256.0F / 360.0F);
		this.id = id;
		updateSpawnPackets(player, headrotation);
		
		ArrayList<Npc> npcs = NPC_CLIENT_LOADED.containsKey(player) ? NPC_CLIENT_LOADED.get(player) : new ArrayList<Npc>();
		npcs.add(this);
		NPC_CLIENT_LOADED.put(player, npcs);
		
		HashMap<Integer, Npc> NPC_CLIENT_MAPPING = NPC_CLIENT.containsKey(player) ? NPC_CLIENT.get(player) : new HashMap<Integer, Npc>();
		NPC_CLIENT_MAPPING.put(id, this);
		NPC_CLIENT.put(player, NPC_CLIENT_MAPPING);
		
		HashMap<Integer, Boolean> NPC_VISBILITY_MAPPING = NPC_VISIBILITY.containsKey(player) ? NPC_VISIBILITY.get(player) : new HashMap<Integer, Boolean>();
		NPC_VISBILITY_MAPPING.put(id, true);
		NPC_VISIBILITY.put(player, NPC_VISBILITY_MAPPING);
		
	}
	
	public void remove() {
		
		Config config = getConfig(location.getWorld());
		for(Player player : Bukkit.getServer().getOnlinePlayers()) unload(player);
		config.set("" + id, null);
		config.set("count", config.getInt("count") > 1 ? (config.getInt("count") - 1) : null); 
		if(!config.contains("count")) return;
		
		for(int i = id + 1; i <= config.getInt("count"); i++) {
			config.set((i - 1) + ".name", config.getString(i + ".name"));
			config.setLocation((i - 1) + ".location", config.getLocation(i + ".location"));
			config.set((i - 1) + ".yawmask", config.getInt(i + ".yawmask"));
		}
		
		config.set("" + config.getInt("count"), null);
				
	}
	
	public void spawn(Location location) {
		Config config = getConfig(location.getWorld());
		byte yaw = (byte) location.getYaw();
		config.set("count", config.contains("count") ? config.getInt("count") + 1 : 1);
		int id = config.getInt("count") - 1;
		config.set(id + ".name", this.displayName);
		config.setLocation(id + ".location", location);
		config.set(id + ".yawmask", yaw);
		this.location = location;
		this.id = id;
	}
	
	public void spawn(Player player) {
		spawn(player.getLocation());
		load(player, (getConfig(player.getWorld()).getInt("count") - 1));
	}
	
	public static void loadNpc(Player player, Config config, int id) {
		
		//Load
		if(player.getLocation().distance(config.getLocation(id + ".location")) <= VIEW_RANGE) {

			if(!NPC_VISIBILITY.containsKey(player) || !NPC_VISIBILITY.get(player).containsKey(id) || !NPC_VISIBILITY.get(player).get(id)) {
				Npc npc = new Npc(player, id); 
				npc.load(player, id);
			}
			
		//Unload
		} else {
			
			if(!NPC_VISIBILITY.containsKey(player) || !NPC_VISIBILITY.get(player).containsKey(id) || NPC_VISIBILITY.get(player).get(id)) {
				
				if(NPC_CLIENT.containsKey(player) && NPC_CLIENT.get(player).containsKey(id)) {
					Npc npc = NPC_CLIENT.get(player).get(id);
					npc.unload(player);	
				}
				
			}
			
		}
		
	}
	
	public static void unloadNpc(Player player) {
		if(NPC_CLIENT_LOADED.containsKey(player) && NPC_CLIENT_LOADED.get(player) != null && !NPC_CLIENT_LOADED.get(player).isEmpty()) {
			ArrayList<Npc> markUnloaded = new ArrayList<Npc>();
			for(Npc npc : NPC_CLIENT_LOADED.get(player)) markUnloaded.add(npc);
			for(Npc npc : markUnloaded) npc.unload(player);
		}

	}
	
	public static void unloadNps() {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) unloadNpc(player);
	}
	
	public static void loadNpcs(Player player) {
		Config config = getConfig(player.getWorld());
		if(!config.contains("count")) return;
		for(int i = 0; i < config.getInt("count"); i++) loadNpc(player, config, i);
	}
	
	public static Npc getClosestNpc(Player player) {
		
		if(!NPC_CLIENT_LOADED.containsKey(player) || NPC_CLIENT_LOADED.get(player).isEmpty()) return null;
		
		Npc closest = null;
		double distance = VIEW_RANGE;
				
		for(Npc npc : NPC_CLIENT_LOADED.get(player)) {
			
			if(npc.location.distance(player.getLocation()) < distance) {
				closest = npc;
				distance = npc.location.distance(player.getLocation());
			}
			
		}
		
		return closest;
		
	}
	
	public static void reloadNpcs(Player player) {
		unloadNpc(player);
		loadNpcs(player);
	}
	
	public static void removeClosestNpc(Player player) {
				
		Npc npc = getClosestNpc(player);
		
		if(npc != null) {
			player.sendMessage(References.message + "Npc " + npc.displayName + " removed.");
			npc.remove();
			for(Player players : Bukkit.getServer().getOnlinePlayers()) {
				if(player.getLocation().distance(players.getLocation()) <= VIEW_RANGE) reloadNpcs(players);
			}
		} else {
			player.sendMessage(References.error + "There are no loaded npcs that can be removed.");
		}
		
	}
	
}
