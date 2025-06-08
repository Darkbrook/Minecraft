package dev.darkbrook.wildfire.common.option;

import java.util.stream.Stream;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

class EnumOption<E extends Enum<E>> extends Option<E>
{
	public EnumOption(Configuration config, String modid, String category, String name, E defaultValue)
	{
		super(config, modid, category, name, defaultValue);
	}

	@Override
	protected E getPropertyValue(Property property)
	{
		return Enum.valueOf(defaultValue.getDeclaringClass(), property.getString());
	}

	@Override
	protected void setPropertyValue(Property property, E value)
	{
		property.set(value.name());
	}

	@Override
	protected Property getProperty()
	{
		Class<E> enumType = defaultValue.getDeclaringClass();
		String[] enumValues = Stream.of(enumType.getEnumConstants())
				.map(Enum::name)
				.toArray(String[]::new);
		return config.get(categoryName, name, defaultValue.name())
				.setValidValues(enumValues);
	}
}