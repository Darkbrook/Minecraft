package com.darkbrook.library.util.helper;

import org.bukkit.Location;
import org.bukkit.World;

public class StringHelper 
{

	public static Location location(World world, String location)
	{
		String[] array = location.split(",");
		return new Location(world, Double.parseDouble(array[0]), Double.parseDouble(array[1]), Double.parseDouble(array[2]));
	}
	
	public static String build(String... strings)
	{
		StringBuilder builder = new StringBuilder();

		for(String string : strings)
		{
			builder.append(string);
		}
		
		return builder.toString();
	}
	
	public static String underscore(String value)
	{
		StringBuilder builder = new StringBuilder();
		value = value.replace(" ", "_");
		
		for(int i = 0; i < value.length(); i++)
		{
			char character = value.charAt(i);
			
			if(i != 0 && Character.isUpperCase(character))
			{
				builder.append("_");
			}
			
			builder.append(character);
		}
		
		return builder.toString();
	}
	
	public static String blockLocation(Location location)
	{
		return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
	}
	
	public static String proper(String value) 
	{
		return value != null && !value.isEmpty() ? (value.length() > 1 ? value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase() : value.toUpperCase()) : value;
	}
	
	public static String proper(Enum<?> value) 
	{
		return StringHelper.proper(value.toString());
	}
	
	public static String trim(String value, int trim) 
	{
		return value.substring(0, value.length() - trim);
	}	
	
}
