package com.darkbrook.minez.entity.ai;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import net.minecraft.server.v1_12_R1.EntityGiantZombie;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStroll;

public class MinezGiantAI extends EntityGiantZombie {
	
	public MinezGiantAI(World world) {
		this(((CraftWorld) world).getHandle());	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MinezGiantAI(net.minecraft.server.v1_12_R1.World world) {
		
		super(world);
				
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 0.7D, false));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.9D));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.9D));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 0.5F));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
		
	}
	
}
