package com.darkbrook.library.gameplay.player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.darkbrook.library.util.helper.HashMapHelper;

public class PlayerMapping
{
	
	private static final Map<Class<? extends PlayerMapping>, Map<Player, PlayerMapping>> playerMapping = new HashMap<Class<? extends PlayerMapping>, Map<Player, PlayerMapping>>();
	
	@SuppressWarnings("unchecked")
	public static <T extends PlayerMapping> T getMapping(Class<T> clazz, Player player)
	{		
		try 
		{
			return (T) HashMapHelper.get(playerMapping, clazz, player, clazz.getDeclaredConstructor(Player.class).newInstance(player));
		} 
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected Player player;
	
	public PlayerMapping(Player player)
	{
		this.player = player;
	}

}
