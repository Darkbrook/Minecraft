package com.darkbrook.prospector.item.crafting;

import java.util.Map;

import com.darkbrook.common.math.IntRange;
import com.darkbrook.prospector.init.ProspectorBlocks;
import com.darkbrook.prospector.init.ProspectorItems;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SluicingRecipes
{
	private static final SluicingRecipes instance = new SluicingRecipes();

	private final Map<Item, SluicingRecipe> recipeMap = Maps.newHashMap();
	
	public static SluicingRecipes getInstance()
	{
		return instance;
	}
	
	private SluicingRecipes()
	{
		addRecipe(ProspectorBlocks.black_sand, 8, 24, 320);
		addRecipe(ProspectorItems.pyrite_dust, 1, 3, 40);
	}
	
	public void addRecipe(Block input, int amountMin, int amountMax, int sluiceTime)
	{
		addRecipe(Item.getItemFromBlock(input), amountMin, amountMax, sluiceTime);
	}
	
	public void addRecipe(Item input, int amountMin, int amountMax, int sluiceTime)
	{
		recipeMap.put(input, new SluicingRecipe(IntRange.of(amountMin, amountMax), sluiceTime));
	}
	
	public SluicingRecipe getRecipe(ItemStack stack)
	{
		return stack != null ? recipeMap.get(stack.getItem()) : null;
	}
	
	public static class SluicingRecipe
	{
		private final IntRange amount;
		private final int sluiceTime;
		
		private SluicingRecipe(IntRange amount, int sluiceTime) 
		{
			this.amount = amount;
			this.sluiceTime = sluiceTime;
		}

		public IntRange getAmount()
		{
			return amount;
		}

		public int getSluiceTime()
		{
			return sluiceTime;
		}		
	}
}
