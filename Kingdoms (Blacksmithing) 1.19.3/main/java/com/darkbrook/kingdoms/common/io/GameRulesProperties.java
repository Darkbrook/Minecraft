package com.darkbrook.kingdoms.common.io;

import java.nio.file.Path;

import com.darkbrook.kingdoms.mixin.GameRulesTypeAccessor;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.GameRules.IntRule;
import net.minecraft.world.GameRules.Key;
import net.minecraft.world.GameRules.Type;
import net.minecraft.world.GameRules.Visitor;

public class GameRulesProperties extends PropertiesFile
{
	private static GameRulesProperties instance;
	
	private final PropertiesManager defaults = new PropertiesManager();
	
	private GameRulesProperties(Path path)
	{
		super(path, "Minecraft gamerules");
		properties = new SortedProperties(defaults.getProperties());
	}
	
	public static void createInstance(Path path)
	{
		instance = new GameRulesProperties(path);
		GameRules.accept(instance.new PropertyVisitor());
		instance.load();
	}
	
	public static void reload()
	{
		instance.load();
	}
	
	@Override
	public void load()
	{
		properties.clear();
		properties.putAll(defaults.getProperties());
		super.load();
		store();
	}
	
	private class PropertyVisitor implements Visitor
	{
		@Override
		public void visitBoolean(Key<BooleanRule> key, Type<BooleanRule> type)
		{				
			defaults.setProperty(key, type.createRule().get());
			
			((GameRulesTypeAccessor) type).<BooleanRule>setRuleFactory(t -> new BooleanRule(t, getBoolean(key)));
		}

		@Override
		public void visitInt(Key<IntRule> key, Type<IntRule> type)
		{
			defaults.setProperty(key, type.createRule().get());

			((GameRulesTypeAccessor) type).<IntRule>setRuleFactory(t -> new IntRule(t, getInt(key)));
		}
	}
}
