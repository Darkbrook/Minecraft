package dev.darkbrook.wildfire.common.option;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class OptionConfig
{
	private final Map<String, CategoryInfo> categoryInfoByName = new LinkedHashMap<>();
	private final String modid;
	private final Configuration config;
	private String categoryName = Configuration.CATEGORY_GENERAL;
	private boolean isCollapsed;
	
	public OptionConfig(String modid, Path path)
	{
		this.modid = modid;
		config = new Configuration(path.toFile(), true);
		// config.load() is called in Configuration() constructor;
		// otherwise it would have needed to be called here
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onConfigChangedEvent(OnConfigChangedEvent event)
	{
		if (event.getModID().equals(modid))
			save();
	}

	public void load()
	{
		config.load();
		removeOrphanProperties();
		// Property map values are reassigned every load, acquire new properties
		reloadProperties();
		// Makes sure properties are ordered by declaration
		reorderProperties();
	}
	
	protected void save()
	{
		config.save();
		// Updates cached values modified through menu/config access
		reloadPropertyValues();
	}

	protected void reorderProperties()
	{
		categoryInfoByName.values().forEach(CategoryInfo::reorderProperties);
	}
	
	public GuiScreen createConfigGui(GuiScreen parent)
	{
		load();
		
		List<IConfigElement> elements = categoryInfoByName.values().stream()
			.flatMap(categoryInfo -> 
			{
				String categoryName = categoryInfo.categoryName;
				ConfigElement element = new ConfigElement(config
					.getCategory(categoryName)
					.setLanguageKey(String.format("option.%s.%s", modid, categoryName)));
				
				return categoryInfo.isCollapsed 
					? Stream.of(element) 
					: element.getChildElements().stream();
			})
			.collect(Collectors.toList());
		
		return new GuiConfig(parent, elements, modid, false, false, 
			I18n.format(String.format("option.%s", modid)));
	}
	
	protected ConfigCategory addCategory(String categoryName)
	{
		return addCategory(categoryName, true);
	}
	
	protected ConfigCategory addCategory(String categoryName, boolean isCollapsed)
	{
		this.categoryName = categoryName;
		this.isCollapsed = isCollapsed;
		return config.getCategory(categoryName);
	}
	
	protected Option<Boolean> addOption(String name, Boolean defaultValue)
	{
		return addOption(name, defaultValue, BooleanOption::new);
	}
	
	protected Option<Integer> addOption(String name, Integer defaultValue)
	{
		return addOption(name, defaultValue, IntegerOption::new);
	}
	
	protected Option<Double> addOption(String name, Double defaultValue)
	{
		return addOption(name, defaultValue, DoubleOption::new);
	}
	
	protected Option<String> addOption(String name, String defaultValue)
	{
		return addOption(name, defaultValue, StringOption::new);
	}
	
	protected <E extends Enum<E>> Option<E> addOption(String name, E defaultValue)
	{
		return addOption(name, defaultValue, EnumOption::new);
	}
	
	protected <T> Option<T> addOption(String name, T defaultValue, OptionFactory<T> factory)
	{
		CategoryInfo info = categoryInfoByName.computeIfAbsent(categoryName,
				key -> new CategoryInfo(categoryName, isCollapsed));
		Option<T> option = factory.createOption(config, modid, categoryName, name, defaultValue);
		info.options.add(option);
		info.optionNames.add(name);
		return option;
	}

	private void removeOrphanProperties()
	{
		config.getCategoryNames().forEach(categoryName -> 
		{
			CategoryInfo info = categoryInfoByName.get(categoryName);
			
			if (info == null)
				config.removeCategory(config.getCategory(categoryName));
			else
				info.removeOrphanProperties();
		});
	}
	
	private void reloadProperties()
	{
		categoryInfoByName.values().forEach(CategoryInfo::reloadProperties);
	}
	
	private void reloadPropertyValues()
	{
		categoryInfoByName.values().forEach(CategoryInfo::reloadPropertyValues);
	}
	
	@FunctionalInterface
	protected static interface OptionFactory<T>
	{
		Option<T> createOption(Configuration config, String modid, String category, String name, T defaultValue);
	}
	
	private class CategoryInfo
	{
		private final List<Option<?>> options = new ArrayList<>();
		private final Set<String> optionNames = new HashSet<>();
		private final String categoryName;
		private final boolean isCollapsed;
		
		public CategoryInfo(String categoryName, boolean isCollapsed)
		{
			this.categoryName = categoryName;
			this.isCollapsed = isCollapsed;
		}
		
		public void reorderProperties()
		{
			config.setCategoryPropertyOrder(categoryName, 
				options.stream()
					.map(Option::getName).collect(Collectors.toList()));
		}
		
		public void removeOrphanProperties()
		{
			config.getCategory(categoryName).values()
				.removeIf(property -> !optionNames.contains(property.getName()));
		}
		
		public void reloadProperties()
		{
			options.forEach(Option::reloadProperty);
		}
		
		public void reloadPropertyValues()
		{
			options.forEach(Option::reloadPropertyValue);
		}
	}
}
