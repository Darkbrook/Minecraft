package com.darkbrook.common.math;

import java.util.Random;

import net.minecraft.util.MathHelper;

public class IntRange
{
	public static IntRange of(int min, int max)
	{
		return new IntRange(min, max);
	}
	
	private final int min;
	private final int max;

	private IntRange(int min, int max)
	{
		this.min = min;
		this.max = max;
	}
	
	public int nextInt(Random random)
	{
		return MathHelper.getRandomIntegerInRange(random, min, max);
	}
	
	public int getMin()
	{
		return min;
	}
	
	public int getMax()
	{
		return max;
	}
}
