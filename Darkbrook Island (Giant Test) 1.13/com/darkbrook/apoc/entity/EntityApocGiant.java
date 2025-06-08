package com.darkbrook.apoc.entity;

import net.minecraft.server.v1_13_R1.EntityGiantZombie;
import net.minecraft.server.v1_13_R1.EntityHuman;
import net.minecraft.server.v1_13_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_13_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_13_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_13_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_13_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_13_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_13_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_13_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_13_R1.World;

public class EntityApocGiant extends EntityGiantZombie
{

	public EntityApocGiant(World world)
	{
		super(world);
		
		goalSelector.a(0, new PathfinderGoalFloat(this));
		goalSelector.a(3, new PathfinderGoalLeapAndGrowl(this, 1.5F));
		goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 0.75, true));
		goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1));
		goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1));
		goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 16));
		goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		
		targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
		targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
	}
	
}