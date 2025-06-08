package com.darkbrook.library.entity;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

public class PacketHandler {
	
	public static void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendActionMessage(Player player, String message) {
		sendPacket(player, new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message + "\"}"), ChatMessageType.GAME_INFO));
	}
	
	public static void sendTitleMessage(Player player, String message) {
		sendPacket(player, new PacketPlayOutTitle(EnumTitleAction.RESET, ChatSerializer.a("{\"text\":\"" + message + "\"}")));
		sendPacket(player, new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + message + "\"}")));
	}
	
	public static void sendSubTitleMessage(Player player, String message) {
		sendPacket(player, new PacketPlayOutTitle(EnumTitleAction.SUBTITLE ,ChatSerializer.a("{\"text\":\"" + message + "\"}")));
	}
	
}
