package com.darkbrook.library.data.object;

import com.darkbrook.library.chat.message.CustomMessage;

public abstract class ObjectDataInterpreter 
{
	
	public static String[] interpretKeys(String keys)
	{
		return keys != null ? (keys.contains("/") ? keys.split("/") : new String[] {keys}) : null;
	}
	
	public static Object interpretObject(String value, Object defaultValue, String[] keys, Object[] values)
	{
		if(value == null)
		{
			return defaultValue;
		}
		
		Object object = interpretObject(value, keys, values);		
		return defaultValue != null ? (object.getClass() == defaultValue.getClass() ? object : defaultValue) : object;
	}
	
	public static Object interpretObject(String value, String[] keys, Object[] values)
	{
		return interpretObject(keys != null && values != null ? new CustomMessage(value).parse(keys, values) : value);
	}
	
	public static Object interpretObject(String value)
	{
		return value.equals("true") || 
			   value.equals("false")           ? Boolean.parseBoolean(value) :
			   value.matches("-?\\d+")         ? Integer.parseInt(value)     : 
			  (value.matches("-?\\d+\\.\\d+F") ? Float.parseFloat(value)     : 
			  (value.matches("-?\\d+\\.\\d+D") ? Double.parseDouble(value)   : value));
	}

}
