package dev.darkbrook.wildfire.common.option;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

class StringOption extends Option<String>
{
	public StringOption(Configuration config, String modid, String category, String name, String defaultValue)
	{
		super(config, modid, category, name, defaultValue);
	}

	@Override
	protected String getPropertyValue(Property property)
	{
		return property.getString();
	}

	@Override
	protected void setPropertyValue(Property property, String value)
	{
		property.set(value);
	}

	@Override
	protected Property getProperty()
	{
		return config.get(categoryName, name, defaultValue);
	}
}