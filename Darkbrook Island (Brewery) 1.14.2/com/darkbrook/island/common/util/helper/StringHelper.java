package com.darkbrook.island.common.util.helper;

public class StringHelper 
{

	public static String buildString(String... strings)
	{
		StringBuilder builder = new StringBuilder();

		for(String string : strings)
		{
			builder.append(string);
		}
		
		return builder.toString();	
	}

	public static String buildCommonName(String uncommonName)
	{
		StringBuilder builder = new StringBuilder();
		String[] sections = uncommonName.toLowerCase().split("_");
		
		for(int i = 0; i < sections.length; i++)
		{
			String section = sections[i];
			
			if(i != 0)
			{
				builder.append(" ");
			}
			
			builder.append(section.substring(0, 1).toUpperCase());
			builder.append(section.substring(1));
		}
		
		return builder.toString();
	}
	
}
