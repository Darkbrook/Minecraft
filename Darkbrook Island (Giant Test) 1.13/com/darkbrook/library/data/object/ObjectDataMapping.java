package com.darkbrook.library.data.object;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.darkbrook.library.util.ResourceLocation;
import com.darkbrook.library.util.helper.HashMapHelper;

public class ObjectDataMapping 
{
	
	private Map<String, ObjectDataParsedBase> dataMapping;
	private Map<String, List<String>> keyMapping;
	private List<Class<? extends ObjectDataParsedBase>> classes;
	private List<String> tempHeaders;
	private String[] keys;
	private ObjectDataParsedBase tempData;
	
	@SafeVarargs
	public ObjectDataMapping(String keys, Class<? extends ObjectDataParsedBase>... classes)
	{		
		this.keys = ObjectDataInterpreter.interpretKeys(keys);
		this.classes = Arrays.asList(classes);
		
		dataMapping = new HashMap<String, ObjectDataParsedBase>();
		keyMapping = new HashMap<String, List<String>>();
		tempHeaders = new ArrayList<String>();
	}
		
	public void load(ResourceLocation location)
	{		
		
		List<String> lines = new ArrayList<String>();
		
		try 
		{
			lines = Files.readAllLines(Paths.get(location.getPath()));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		for(String line : lines)
		{
			loadLine(line);
		}

	}
	
	@SuppressWarnings("unchecked")
	public <T extends ObjectDataParsedBase> List<T> getDataArray(Class<T> clazz)
	{
		List<T> list = new ArrayList<T>();
		List<String> keys = keyMapping.get(this.keys[classes.indexOf(clazz)]);
		
		if(keys != null) for(String key : keys)
		{
			list.add((T) dataMapping.get(key));
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ObjectDataParsedBase> T getData(Class<T> clazz, String key)
	{		
		return (T) dataMapping.get(keys[classes.indexOf(clazz)] + "." + key);
	}
	
	private void loadLine(String line)
	{
		
		try 
		{
			
			if(line.startsWith("#"))
			{
				tempHeaders.add(line.substring(1));
				return;
			}
			
			for(int i = 0; i < keys.length; i++) 
			{
				String key = keys[i];
				
				if(!line.startsWith(key))
				{
					continue;
				}
				
				tempData = classes.get(i).getDeclaredConstructor(String.class).newInstance(line);
				tempData.addHeaders(tempHeaders);
				
				dataMapping.put(tempData.getIdentity(), tempData);
				tempHeaders.clear();

				HashMapHelper.add(keyMapping, key, tempData.getIdentity());
				return;
			}
			
			if(!line.isEmpty() && tempData != null)
			{
				tempData.map(line);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
}
