package com.darkbrook.kingdoms.common.io;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class SortedProperties extends Properties
{
	private static final long serialVersionUID = -795670490075034768L;

	public SortedProperties() {}
	
	public SortedProperties(Properties defaults)
	{
		super(defaults);
	}
	
	@Override
	public Set<Map.Entry<Object, Object>> entrySet()
	{
		Set<Map.Entry<Object, Object>> sortedSet = new TreeSet<>((o1, o2) -> o1.getKey().toString().compareTo(o2
				.getKey().toString()));
		
		sortedSet.addAll(super.entrySet());
		
		return sortedSet;
	}
	
	@Override
	public synchronized Enumeration<Object> keys()
	{
		return Collections.enumeration(keySet());
	}
	
	@Override
	public Set<Object> keySet()
	{
		return new TreeSet<>(super.keySet());
	}
}
