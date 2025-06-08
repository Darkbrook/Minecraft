package com.darkbrook.library.util.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapHelper
{
	
	public static <K, V> void add(Map<K, List<V>> mapping, K key, V value)
	{
		List<V> list;
		
		if(!mapping.containsKey(key))
		{
			mapping.put(key, list = new ArrayList<V>());
		}
		else
		{
			list = mapping.get(key);
		}
		
		list.add(value);
	}
	
	public static <MK, K, V> Map<K,V> get(Map<MK, Map<K, V>> mapping, MK masterKey)
	{
		Map<K, V> subMapping;
				
		if(!mapping.containsKey(masterKey))
		{
			mapping.put(masterKey, subMapping = new HashMap<K, V>());			
		}
		else
		{
			subMapping = mapping.get(masterKey);
		}
		
		return subMapping;
	}
	
	public static <MK, K, V> V get(Map<MK, Map<K, V>> mapping, MK masterKey, K key, V value)
	{
		return get(get(mapping, masterKey), key, value);
	}

	public static <K, V> V get(Map<K, V> mapping, K key, V value)
	{
		V valueOut;
		
		if(!mapping.containsKey(key))
		{
			mapping.put(key, valueOut = value);
		}
		else
		{
			valueOut = mapping.get(key);
		}
		
		return valueOut;
	}
	
}
