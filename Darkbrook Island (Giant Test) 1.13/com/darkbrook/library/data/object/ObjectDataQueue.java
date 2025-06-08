package com.darkbrook.library.data.object;

import java.util.ArrayList;
import java.util.List;

public class ObjectDataQueue 
{

	private Object[] data;
	private int index;
	
	public ObjectDataQueue(Object[] data)
	{
		this.data = data;
	}

	public <T> List<T> toList()
	{
		return toList(data.length - index);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> toList(int length)
	{
		List<T> list = new ArrayList<T>();
		
		for(int i = 0; i < length; i++)
		{
			list.add((T) get());
		}
		
		return list;
	}
	
	public Object get()
	{
		return data[index++];
	}
	
	public ObjectDataQueue queue()
	{
		return data[index] instanceof ObjectDataQueue ? (ObjectDataQueue) get() : this;
	}

	public String s()
	{
		return (String) get();
	}
	
	public double d()
	{
		return (double) get();
	}
	
	public float f()
	{
		return (float) get();
	}
	
	public long l()
	{
		return (long) get();
	}
	
	public int i()
	{
		return (int) get();
	}
	
	public boolean b()
	{
		Object value = get();
		return value.getClass() == Boolean.class ? (boolean) value : value.equals("true");
	}
	
	public boolean hasNext()
	{
		return index < data.length - 1;
	}
	
}
