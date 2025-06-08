package com.darkbrook.library.util.helper;

import java.lang.reflect.Field;

import net.minecraft.server.v1_13_R1.Item;

public class ItemHelper
{

	public static void setMaxStackSize(Item item, int size)
	{
		try
		{
			Field field = Item.class.getDeclaredField("maxStackSize");
			field.setAccessible(true);
			
			field.setInt(item, size);
			field.setAccessible(false);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

	}
	
}
