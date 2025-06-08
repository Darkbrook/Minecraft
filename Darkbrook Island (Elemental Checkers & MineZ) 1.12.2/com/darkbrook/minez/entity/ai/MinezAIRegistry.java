package com.darkbrook.minez.entity.ai;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.World;

public class MinezAIRegistry {
	
	@SuppressWarnings("deprecation")
	public static void registerAI(Class<? extends Entity> clazz, EntityType type) {
		EntityTypes.b.a(type.getTypeId(), new MinecraftKey(clazz.getName().toLowerCase()), clazz);
	}
	
	public static void spawn(Entity entity, Location location) {
		World world = ((CraftWorld) location.getWorld()).getHandle();
		entity.setPosition(location.getX(), location.getY(), location.getZ());
		world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}
	
}
