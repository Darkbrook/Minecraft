package com.darkbrook.library.data.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.darkbrook.library.util.helper.HashMapHelper;

public abstract class ObjectDataParsedBase
{
	
	private Map<String, List<String>> arrayMapping;
	private Map<String, String> dataMapping;
	private List<String> headers;
	private String tempKey;
	private String identity;
	
	public ObjectDataParsedBase(String identity)
	{
		arrayMapping = new HashMap<String, List<String>>();
		dataMapping = new HashMap<String, String>();
		headers = new ArrayList<String>();
		
		this.identity = identity;
	}
	
	public void map(String line)
	{
				
		if(line.startsWith("  "))
		{
			addValue(tempKey, line.substring(2), true);
		}
		else if(line.startsWith(" "))
		{
			
			String[] data = line.substring(1).split(":", 2);
			
			String key   = data[0];
			String value = data[1];
					
			if(!value.isEmpty())
			{
				addValue(key, value, false);
			}
			else
			{
				tempKey = key;
			}
			
		}
		
	}
	
	public Object getValue(String key)
	{
		return dataMapping.containsKey(key) ? dataMapping.get(key) : getArrayValues(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fromHeader(String headers, T... values)
	{
		if(!headers.isEmpty())
		{
			
			String[] keys = ObjectDataInterpreter.interpretKeys(headers);
			
			for(String header : this.headers) 
			for(int i = 0; i < keys.length; i++) if(header.equals(keys[i]))
			{
				return values[i];
			}
			
		}
		
		return null;
	}
	 
	public List<String> getArrayValues(String key)
	{
		return arrayMapping.containsKey(key) ? arrayMapping.get(key) : new ArrayList<String>();
	}
	
	public Set<String> getArrayKeys()
	{
		return arrayMapping.keySet();
	}
	
	public Set<String> getDataKeys()
	{
		return dataMapping.keySet();
	}
		
	public String getDataValue(String key)
	{
		return dataMapping.get(key);
	}
	
	public String getIdentity()
	{
		return identity;
	}
	
	public String getVariableName()
	{
		return identity.split("\\.")[1];
	}

	public boolean hasArrayKey(String key)
	{
		return arrayMapping.containsKey(key);
	}
	
	public boolean hasHeader(String header)
	{
		return headers.contains(header);
	}
	
	protected void addHeaders(List<String> headers)
	{
		this.headers.addAll(headers);
	}

	private void addValue(String key, String value, boolean isArray)
	{
		
		if(isArray)
		{
			HashMapHelper.add(arrayMapping, key, value);
		}
		else
		{
			dataMapping.put(key, value);
		}
		
	}

}
