package dev.darkbrook.wildfire.common.option;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

class DoubleOption extends Option<Double>
{
	public DoubleOption(Configuration config, String modid, String category, String name, Double defaultValue)
	{
		super(config, modid, category, name, defaultValue);
	}

	@Override
	protected Double getPropertyValue(Property property)
	{
		return property.getDouble();
	}

	@Override
	protected void setPropertyValue(Property property, Double value)
	{
		property.set(value);
	}

	@Override
	protected Property getProperty()
	{
		return config.get(categoryName, name, defaultValue);
	}
}