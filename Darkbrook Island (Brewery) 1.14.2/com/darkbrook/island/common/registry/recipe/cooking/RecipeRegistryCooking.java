package com.darkbrook.island.common.registry.recipe.cooking;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.SmokingRecipe;

import com.darkbrook.island.common.registry.RegistryManager;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;

public class RecipeRegistryCooking extends RegistryManager<IRegistryCookingRecipe> implements IRegistryAction
{

	public RecipeRegistryCooking() 
	{
		super(IRegistryCookingRecipe.class);
	}

	@Override
	public void onEnable(RegistryPlugin plugin) 
	{		
		values.forEach(value  -> onRecipeRegister(plugin, value));
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
	}
	
	private void onRecipeRegister(RegistryPlugin plugin, IRegistryCookingRecipe recipe)
	{
		
		NamespacedKey key = new NamespacedKey(plugin, recipe.getIdentity());

		Bukkit.addRecipe(new FurnaceRecipe(key, recipe.getResult(), recipe.getSource(), recipe.getExperience(), 200));
		
		if(recipe.getUseMethod() == RecipeUseMethod.SMOKING)
		{
			Bukkit.addRecipe(new CampfireRecipe(key, recipe.getResult(), recipe.getSource(), 0.0f, 600));
			Bukkit.addRecipe(new SmokingRecipe(key, recipe.getResult(), recipe.getSource(), recipe.getExperience(), 100));
		}
		else if(recipe.getUseMethod() == RecipeUseMethod.BLASTING)
		{
			Bukkit.addRecipe(new BlastingRecipe(key, recipe.getResult(), recipe.getSource(), recipe.getExperience(), 100));
		}

	}

}
