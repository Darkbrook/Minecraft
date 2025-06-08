package com.darkbrook.island.common.util.helper;

public class TimeHelper 
{

	public static String[] stringArray(long value, int... steps)
	{
		String[] stringValues = new String[steps.length];
		long[] values = new long[steps.length];
		
		for(int i = 0; i < values.length; i++)
		{
			long temp = (value /= steps[i]);
			
			if((i + 1) < steps.length)
			{
				temp %= steps[i + 1]; 
			}
			
			values[i] = temp;
			stringValues[i] = (i == 0 || temp > 0 ? String.valueOf(temp) : null);
		}
		
		return stringValues;
	}
	
	public static long[] longArray(long value, int... steps)
	{
		long[] values = new long[steps.length];
		
		for(int i = 0; i < values.length; i++)
		{
			long temp = (value /= steps[i]);
			
			if((i + 1) < steps.length)
			{
				temp %= steps[i + 1]; 
			}
			
			values[i] = temp;
		}
		
		return values;
	}
	
}
