package com.darkbrook.library.gameplay.table;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class ArmorStandWrapper
{

	private ArmorStand stand;

	public ArmorStandWrapper(ArmorStand stand)
	{
		this.stand = stand;
	}
	
	public void spawn(Location location)
	{
		stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
	}
	
	public void destroy()
	{
		stand.remove();
	}
	
	public void setPose(Vector head, Vector rightArm, Vector leftArm, Vector rightLeg, Vector leftLeg) 
	{
		stand.setHeadPose(toEulerAngle(head));
		stand.setRightArmPose(toEulerAngle(rightArm));
		stand.setLeftArmPose(toEulerAngle(leftArm));
		stand.setRightLegPose(toEulerAngle(rightLeg));
		stand.setLeftLegPose(toEulerAngle(leftLeg));
	}
	
	public void setItemInHand(ItemStack stack)
	{
		stand.setItemInHand(stack);
	}
	
	public void setName(String name, boolean isVisible)
	{
		stand.setCustomName(name);
		stand.setCustomNameVisible(isVisible);
	}
	
	public void setProperties(boolean hasAi, boolean hasArms, boolean hasBasePlate, boolean canPickupItems, boolean isCollidable, boolean isInvulnerable, boolean hasGravity, boolean hasMarker, boolean isVisible)
	{
		stand.setAI(hasAi);
		stand.setArms(hasArms);
		stand.setBasePlate(hasBasePlate);
		stand.setCanPickupItems(canPickupItems);
		stand.setCollidable(isCollidable);
		stand.setInvulnerable(isInvulnerable);
		stand.setGravity(hasGravity);
		stand.setMarker(hasMarker);
		stand.setVisible(isVisible);
	}
	
	public ArmorStand getArmorStand()
	{
		return stand;
	}
	
	public boolean isAlive()
	{
		return stand != null && !stand.isDead();
	}
	
	private EulerAngle toEulerAngle(Vector angle) 
	{
		return new EulerAngle(Math.toRadians(angle.getX()), Math.toRadians(angle.getY()), Math.toRadians(angle.getZ()));
	}
	
}
