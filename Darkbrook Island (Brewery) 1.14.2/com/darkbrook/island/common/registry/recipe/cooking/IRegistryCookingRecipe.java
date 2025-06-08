package com.darkbrook.island.common.registry.recipe.cooking;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.common.registry.recipe.IRegistryRecipe;

public interface IRegistryCookingRecipe extends IRegistryRecipe
{
	public ItemStack getResult();
	
	public Material getSource();
	
	public String getIdentity();
	
	public float getExperience();
	
	public RecipeUseMethod getUseMethod();
}
