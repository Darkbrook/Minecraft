package com.darkbrook.kingdoms.common.util;

import java.util.concurrent.ThreadLocalRandom;

public class Random
{
	@SafeVarargs
	public static <T> T choose(T... values)
	{
		return values[nextInt(values.length)];
	}
	
	public static int between(int min, int max)
	{
		return nextInt(max - min + 1) + min;
	}
	
	public static int nextInt(int bounds)
	{
		return ThreadLocalRandom.current().nextInt(bounds);
	}
}
