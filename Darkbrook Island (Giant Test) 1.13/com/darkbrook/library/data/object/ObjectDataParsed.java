package com.darkbrook.library.data.object;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectDataParsed<T> extends ObjectDataParsedBase
{

	private ObjectDataKeyValue keyValue;
	private T cachedValue;
	private boolean isStatic;
	
	public ObjectDataParsed(String identity) 
	{
		super(identity);
		keyValue = new ObjectDataKeyValue();
		isStatic = true;
	}
	
	public ObjectDataQueue getDataOrArray(String keys)
	{
		String[] keyArray = ObjectDataInterpreter.interpretKeys(keys);
		Object[] data = new Object[keyArray.length];

		for(int i = 0; i < data.length; i++)
		{
			
			String key = keyArray[i];
			
			if(hasArrayKey(key))
			{
				data[i] = new ObjectDataQueue(keyValue.getObjects(this, key).toArray());
			}
			else
			{
				data[i] = keyValue.getObject(this, key, null);
			}
			
		}
		
		return new ObjectDataQueue(data);
	}
	
	public ObjectDataQueue getDataArray(String keys)
	{
		List<Object> objects = new ArrayList<Object>();
		
		for(String key : ObjectDataInterpreter.interpretKeys(keys))
		{
			objects.addAll(keyValue.getObjects(this, key));
		}
		
		return !objects.isEmpty() ? new ObjectDataQueue(objects.toArray()) : null;
	}
	
	public ObjectDataQueue getData(String keys, Object... defaultValues)
	{
		String[] keyArray = ObjectDataInterpreter.interpretKeys(keys);
		Object[] data = new Object[keyArray.length];
		
		for(int i = 0; i < data.length; i++)
		{
			data[i] = keyValue.getObject(this, keyArray[i], defaultValues[i]);
		}
		
		return new ObjectDataQueue(data);
	}
	
	public void addMapping(List<String> keys, List<Object> values)
	{
		keyValue.addMapping(keys, values);
		isStatic = false;
	}

	public void addMapping(String keys, Object... values)
	{
		keyValue.addMapping(keys, values);
		isStatic = false;
	}
	
	public T getParsedValue()
	{
		if(cachedValue == null || !isStatic)
		{
			cachedValue = onParsedValueLoad();
			keyValue.resetMapping();
			isStatic = true;
		}
		
		return cachedValue;
	}
	
	protected abstract T onParsedValueLoad();

}
