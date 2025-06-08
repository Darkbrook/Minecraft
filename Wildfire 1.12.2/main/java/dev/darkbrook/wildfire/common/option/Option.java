package dev.darkbrook.wildfire.common.option;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public abstract class Option<T> implements Supplier<T>
{
	protected final Configuration config;
	protected final String modid;
	protected final String categoryName;
	protected final String name;
	protected final T defaultValue;
	
	private Consumer<Property> constraints = property -> {};
	private Property property;
	private T value;

	public Option(Configuration config, String modid, String categoryName, String name, T defaultValue)
	{
		this.config = config;
		this.modid = modid;
		this.categoryName = categoryName;
		this.name = name;
		this.defaultValue = defaultValue;
		reloadProperty();
	}
	
	public Option<T> setConstraints(Consumer<Property> constraints)
	{
		this.constraints = constraints;
		return this;
	}
	
	@Override
	public T get()
	{
		return value;
	}

	public void set(T value)
	{
		this.value = value;
		setPropertyValue(property, value);
		config.save();
	}
	
	public String getName()
	{
		return name;
	}
	
	protected abstract T getPropertyValue(Property property);
	
	protected abstract void setPropertyValue(Property property, T value);
	
	protected abstract Property getProperty();
	
	void reloadProperty()
	{
		property = getProperty();
		property.setLanguageKey(String.format("option.%s.%s.%s", modid, categoryName, name));
		constraints.accept(property);
		reloadPropertyValue();
	}
	
	void reloadPropertyValue()
	{
		value = getPropertyValue(property);
	}
}
