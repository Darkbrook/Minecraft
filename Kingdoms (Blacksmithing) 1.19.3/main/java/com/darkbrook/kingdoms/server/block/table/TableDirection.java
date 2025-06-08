package com.darkbrook.kingdoms.server.block.table;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;

public enum TableDirection
{
	NORTH(Axis.X, AxisDirection.NEGATIVE, 270.0f),
	SOUTH(Axis.X, AxisDirection.POSITIVE, 90.0f),
	WEST(Axis.Z, AxisDirection.POSITIVE, 180.0f),
	EAST(Axis.Z, AxisDirection.NEGATIVE, 0.0f);

	private final Axis axis;
	private final AxisDirection direction;
	private final float rotation;
	
	TableDirection(Axis axis, AxisDirection direction, float rotation)
	{
		this.axis = axis;
		this.direction = direction;
		this.rotation = rotation;
	}
	
	public int getOffset(Axis axis)
	{
		return axis == this.axis ? direction.offset() : 0;
	}

	public float getRotation()
	{
		return rotation;
	}

	public static TableDirection of(Direction direction)
	{
		return switch (direction)
		{
			case NORTH -> NORTH;
			case SOUTH -> SOUTH;
			case WEST -> WEST;
			case EAST -> EAST;
			default -> throw new IllegalStateException("Unable to get table direction of " + direction);
		};
	}
}
