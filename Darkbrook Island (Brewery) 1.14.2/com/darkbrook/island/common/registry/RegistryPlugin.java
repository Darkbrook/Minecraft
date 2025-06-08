package com.darkbrook.island.common.registry;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.darkbrook.island.common.registry.action.ActionRegistry;
import com.darkbrook.island.common.registry.action.IRegistryAction;
import com.darkbrook.island.common.registry.action.IRegistryInstantAction;
import com.darkbrook.island.common.registry.command.CommandRegistry;
import com.darkbrook.island.common.registry.command.IRegistryCommand;
import com.darkbrook.island.common.registry.handler.HandlerRegistry;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.common.registry.recipe.RecipeRegistry;

public abstract class RegistryPlugin extends JavaPlugin implements IRegistryAction
{

	private static RegistryPlugin plugin;
	
	private static HandlerRegistry handlerRegistry;
	private static CommandRegistry commandRegistry;
	private static RecipeRegistry recipeRegistry;
	private static ActionRegistry actionRegistry;
	
	@Override
	public void onEnable()
	{	
		plugin = this;
		
		handlerRegistry = new HandlerRegistry();
		commandRegistry = new CommandRegistry();
		recipeRegistry = new RecipeRegistry();
		actionRegistry = new ActionRegistry();
		
		onEnable(this);
		
		actionRegistry.initalize(handlerRegistry);
		actionRegistry.initalize(commandRegistry);
		actionRegistry.initalize(recipeRegistry);
		actionRegistry.onEnable(this);
	}
	
	@Override
	public void onDisable()
	{
		onDisable(this);
		actionRegistry.onDisable(this);
	}
	
	public void initialize(IRegistryValue... values)
	{
		for(IRegistryValue value : values) initialize(value);
	}
	
	public void initialize(IRegistryValue value)
	{
		if(value instanceof IRegistryInstantAction)
		{
			((IRegistryInstantAction) value).onInitialize(this);
		}
		
		handlerRegistry.initalize(value);
		commandRegistry.initalize(value);
		recipeRegistry.initalize(value);
		actionRegistry.initalize(value);
	}
	
	public void register(IRegistryHandler handler)
	{
		getServer().getPluginManager().registerEvents(handler, this);
	}
	
	public void register(IRegistryCommand command)
	{
		getCommand(command.getPrefix()).setExecutor(commandRegistry);
	}
	
	public void unregister(IRegistryHandler handler)
	{
		HandlerList.unregisterAll(handler);
	}
	
	public void unregister(IRegistryCommand command)
	{
		getCommand(command.getPrefix()).setExecutor(null);
	}
	
	public static RegistryPlugin getInstance()
	{
		return plugin;
	}

}
