package com.darkbrook.library.data.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectDataKeyValue 
{
	
	public List<String> keys;
	public List<Object> values;
	private String[] keyArray;
	private Object[] valueArray;
	
	public ObjectDataKeyValue()
	{
		keys = new ArrayList<String>();
		values = new ArrayList<Object>();
	}
	
	public void addMapping(List<String> keys, List<Object> values)
	{
		this.keys.addAll(keys);
		this.values.addAll(values);
		
		keyArray = this.keys.toArray(new String[keys.size()]);
		valueArray = this.values.toArray();	
	}

	public void addMapping(String keys, Object... values)
	{	
		if(keys != null && values.length > 0) addMapping(Arrays.asList(ObjectDataInterpreter.interpretKeys(keys)), Arrays.asList(values));					
	}

	public void resetMapping()
	{
		keys.clear();
		values.clear();
		
		keyArray = null;
		valueArray = null;
	}
	
	public List<Object> getObjects(ObjectDataParsedBase data, String key)
	{
		List<Object> objects = new ArrayList<Object>();

		for(String value : data.getArrayValues(key))
		{
			objects.add(ObjectDataInterpreter.interpretObject(value, keyArray, valueArray));
		}
		
		return objects;
	}
	
	public Object getObject(ObjectDataParsedBase data, String key, Object defaultValue)
	{
		return ObjectDataInterpreter.interpretObject(data.getDataValue(key), defaultValue, keyArray, valueArray);
	}
	
}
