package com.darkbrook.library.util.helper.math;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VectorHelper
{
	
	public static void launchAway(Entity victim, Location location, Vector amplifier)
	{
		Vector direction = victim.getLocation().toVector().subtract(location.toVector()).normalize();
		direction.multiply(amplifier);
		victim.setVelocity(direction);
	}

}
