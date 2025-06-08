package dev.darkbrook.wildfire.common.option;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

class IntegerOption extends Option<Integer>
{
	public IntegerOption(Configuration config, String modid, String category, String name, Integer defaultValue)
	{
		super(config, modid, category, name, defaultValue);
	}

	@Override
	protected Integer getPropertyValue(Property property)
	{
		return property.getInt();
	}

	@Override
	protected void setPropertyValue(Property property, Integer value)
	{
		property.set(value);
	}

	@Override
	protected Property getProperty()
	{
		return config.get(categoryName, name, defaultValue);
	}
}