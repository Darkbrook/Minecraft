package com.darkbrook.island.common.config.property;

import java.util.HashMap;
import java.util.Map;

import com.darkbrook.island.common.config.YamlConfig;
import com.darkbrook.island.common.registry.RegistryPlugin;

public abstract class PropertyConfig extends YamlConfig
{

	private Map<String, ConfigProperty<?>> propertyMapping;
	
	public PropertyConfig(String name)
	{
		super(RegistryPlugin.getInstance(), name);
		propertyMapping = new HashMap<String, ConfigProperty<?>>();
	}
	
	@Override
	public boolean save()
	{
		propertyMapping.values().forEach(ConfigProperty::write);
		return super.save();
	}
		
	protected void initialize(ConfigProperty<?> property)
	{
		propertyMapping.put(property.getPropertyKey(this), property);
	}
	
	protected abstract String getMasterKey();
	
}
