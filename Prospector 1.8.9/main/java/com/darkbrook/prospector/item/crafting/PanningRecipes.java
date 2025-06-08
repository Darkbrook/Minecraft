package com.darkbrook.prospector.item.crafting;

import java.util.Map;

import com.darkbrook.common.math.IntRange;
import com.darkbrook.prospector.init.ProspectorItems;
import com.darkbrook.prospector.item.ItemGoldPan;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

public class PanningRecipes
{
	private static final PanningRecipes instance = new PanningRecipes();

	private final Map<ItemGoldPan, PanningRecipe> recipeMap = Maps.newHashMap();

	public static PanningRecipes getInstance()
	{
		return instance;
	}
	
	private PanningRecipes()
	{
		addRecipe((ItemGoldPan) ProspectorItems.gold_pan_black_sand, 2, 6);
		addRecipe((ItemGoldPan) ProspectorItems.gold_pan_pyrite_dust, 3, 9);
	}
	
	public void addRecipe(ItemGoldPan input, int amountMin, int amountMax)
	{
		recipeMap.put(input, new PanningRecipe(IntRange.of(amountMin, amountMax)));
	}
	
	public PanningRecipe getRecipe(ItemStack stack)
	{
		return stack != null ? recipeMap.get(stack.getItem()) : null;
	}
	
	public static class PanningRecipe
	{
		private final IntRange amount;

		private PanningRecipe(IntRange amount) 
		{
			this.amount = amount;
		}
		
		public IntRange getAmount()
		{
			return amount;
		}
	}
}
