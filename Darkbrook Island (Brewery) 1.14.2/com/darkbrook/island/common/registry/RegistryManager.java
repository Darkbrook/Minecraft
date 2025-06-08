package com.darkbrook.island.common.registry;

import java.util.ArrayList;
import java.util.List;

public abstract class RegistryManager<T extends IRegistryValue> implements IRegistryManager
{
	
	protected final List<T> values;
	private Class<T> type;
	
	public RegistryManager(Class<T> type)
	{
		values = new ArrayList<T>();
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initalize(IRegistryValue value)
	{
		if(type.isInstance(value)) values.add((T) value);
	}
	
}
