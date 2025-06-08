package com.darkbrook.library.chat.message;

import com.darkbrook.library.data.object.ObjectDataInterpreter;

public class CustomMessage 
{

	private static final String[] colorKeys = new String[]
	{
		"black"    , "dark_blue"  , "dark_green", "dark_aqua", 
		"dark_red" , "dark_purple", "gold"      , "gray",
		"dark_gray", "blue"       , "green"     , "aqua", 
		"red"      , "purple"     , "yellow"    , "white", 
		"magic"    , "bold"		  , "strike"    , "underline", 
		"italic"   , "reset"
	};
			
	private static final String[] colorValues = new String[]
	{
		"\u00A70", "\u00A71", "\u00A72", "\u00A73", "\u00A74", "\u00A75", "\u00A76", "\u00A77", "\u00A78", 
		"\u00A79", "\u00A7a", "\u00A7b", "\u00A7c", "\u00A7d", "\u00A7e", "\u00A7f", "\u00A7k", "\u00A7l", 
		"\u00A7m", "\u00A7n", "\u00A7o", "\u00A7r"
	};
	
	private String message;
	
	public CustomMessage(String... messages)
	{
		
		if(messages.length > 1)
		{
			StringBuilder builder = new StringBuilder();
			
			for(String message : messages)
			{
				builder.append(message);
			}
			
			message = builder.toString();
		}
		else
		{
			message = messages.length == 1 ? messages[0] : null;
		}
	
		if(message != null)
		{
			parse(colorKeys, colorValues);
		}
		
	}
	
	@Override
	public String toString()
	{
		return message;
	}
	
	public String parse(String keys, String values)
	{
		return parse(ObjectDataInterpreter.interpretKeys(keys), ObjectDataInterpreter.interpretKeys(values));
	}
	
	public String parse(String[] keys, Object[] values)
	{
		for(int i = 0; i < keys.length; i++)
		{
			message = message.replace("{$" + keys[i] + "}", String.valueOf(values[i]));
		}
		
		return message;
	}
	
}
