package com.darkbrook.library.gameplay.player;

public enum CardinalDirection 
{
	
	NORTH, SOUTH, EAST, WEST;
	
	public static CardinalDirection fromYaw(float yaw)
	{
		if(yaw < 0.0F) 
		{
			yaw += 360.0F;
		}
		
		if(yaw >= 315 || yaw < 45) 
		{
			return CardinalDirection.SOUTH;
		} 
		else if(yaw < 135) 
		{
			return CardinalDirection.WEST;
		} 
		else if(yaw < 225) 
		{
			return CardinalDirection.NORTH;
		} 
		else if(yaw < 315) 
		{
			return CardinalDirection.EAST;
		}
		
		return CardinalDirection.NORTH;
	}
	
}
