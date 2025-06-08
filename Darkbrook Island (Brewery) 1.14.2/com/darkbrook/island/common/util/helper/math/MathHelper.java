package com.darkbrook.island.common.util.helper.math;

import java.util.Random;

public class MathHelper 
{
	
	private static final Random RANDOM = new Random();

	public static int random(int length) 
	{
		return RANDOM.nextInt(length);
	}
	
}
