package com.darkbrook.apoc.entity;

import org.bukkit.Location;

import com.darkbrook.island.gameplay.visual.VisualRepository;

import net.minecraft.server.v1_13_R1.EntityInsentient;
import net.minecraft.server.v1_13_R1.PathfinderGoalLeapAtTarget;

public class PathfinderGoalLeapAndGrowl extends PathfinderGoalLeapAtTarget
{
	
	private EntityInsentient entity;

	public PathfinderGoalLeapAndGrowl(EntityInsentient entity, float c)
	{
		super(entity, c);
		this.entity = entity;
	}
	
	@Override
	public boolean a()
	{
		boolean a = super.a();
		
		if(a)
		{
			VisualRepository.growl.play(new Location(entity.getWorld().getWorld(), entity.locX, entity.locY, entity.locZ));
		}
		
		return a;
	}
	
}
