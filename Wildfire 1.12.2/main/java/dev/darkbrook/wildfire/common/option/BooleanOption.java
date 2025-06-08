package dev.darkbrook.wildfire.common.option;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

class BooleanOption extends Option<Boolean>
{
	public BooleanOption(Configuration config, String modid, String category, String name, Boolean defaultValue)
	{
		super(config, modid, category, name, defaultValue);
	}

	@Override
	protected Boolean getPropertyValue(Property property)
	{
		return property.getBoolean();
	}

	@Override
	protected void setPropertyValue(Property property, Boolean value)
	{
		property.set(value);
	}

	@Override
	protected Property getProperty()
	{
		return config.get(categoryName, name, defaultValue);
	}
}