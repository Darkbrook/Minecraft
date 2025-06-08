package com.darkbrook.library.util.helper.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathHelper 
{
	
	private static final Random RANDOM = new Random();
	
	public static List<Vector2i> bresenham(int x0, int y0, int x1, int y1) 
	{     
        List<Vector2i> line = new ArrayList<Vector2i>();
        
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
 
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1; 
 
        int difference = dx - dy;
 
        while (true)  
        {
        	
            line.add(new Vector2i(x0, y0));
            
            if (x0 == x1 && y0 == y1) 
            {
            	break;
            }
 
            int difference2 = difference * 2;
            
            if (difference2 > -dy) 
            {
            	difference -= dy;
                x0 += sx;
            }
 
            if (difference2 < dx) 
            {
            	difference += dx;
                y0 += sy;
            }
            
        }      
        
        return line;
    }
	
	public static <T> T random(List<T> values, List<Integer> ratios)
	{
		int[] increments = new int[values.size()];
		int total = 0;
		
		for(int ratio : ratios)
		{
			total += ratio;
		}
		
		int chance = randomInt(total) + 1;
		total = 0;
		
		for(int i = 0; i < increments.length; i++)
		if(chance <= (increments[i] = total += ratios.get(i)))
		{
			return values.get(i);
		}
		
		return null;
	}
	
	public static double clamp(double value, double min, double max)
	{
		return Math.min(max, Math.max(min, value));
	}
	
	public static int parseUnsignedInt(String value)
	{
		
		try 
		{
			return Integer.parseInt(value);
		} 
		catch (NumberFormatException e) 
		{
			return -1;
		}
		
	}
	
	public static double percent(double value, double total, double roundAmount) 
	{
		return MathHelper.round(MathHelper.percent(value, total), roundAmount);
	}
	
	public static double percent(double value, double total) 
	{
		return MathHelper.divide(value, total) * 100.0D;
	}
	
	public static double divide(double value, double total, double roundAmount) 
	{
		return MathHelper.round(MathHelper.divide(value, total), roundAmount);
	}
	
	public static double divide(double value, double total) 
	{
		return value / total;
	}
	
	public static double round(double value, double roundAmount) 
	{
		return Math.round(value * roundAmount) / roundAmount;
	}
	
	public static int level(int value, int level)
	{
		return (int) MathHelper.divide(value, level) + (value % level != 0 ? 1 : 0);
	}
	
	public static float randomFloat() 
	{
		return RANDOM.nextFloat();
	}
	
	public static int randomInt(int length) 
	{
		return RANDOM.nextInt(length);
	}
	
	public static int randomSignedInt(int length) 
	{
		return RANDOM.nextInt(length) - length / 2;
	}
	
	public static boolean randomBoolean() 
	{
		return RANDOM.nextBoolean();
	}

}
