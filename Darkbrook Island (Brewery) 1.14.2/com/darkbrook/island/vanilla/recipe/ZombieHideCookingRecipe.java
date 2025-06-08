package com.darkbrook.island.vanilla.recipe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.common.registry.recipe.cooking.IRegistryCookingRecipe;
import com.darkbrook.island.common.registry.recipe.cooking.RecipeUseMethod;
import com.darkbrook.island.mmo.itemstack.ItemStackWriter;

public class ZombieHideCookingRecipe implements IRegistryCookingRecipe
{

	@Override
	public ItemStack getResult() 
	{
		ItemStackWriter writer = new ItemStackWriter(Material.RABBIT_HIDE);
		writer.setDisplayName(ChatColor.WHITE + "Zombie Hide");
		return writer.apply();
	}

	@Override
	public Material getSource() 
	{
		return Material.ROTTEN_FLESH;
	}

	@Override
	public String getIdentity() 
	{
		return "zombie_hide";
	}

	@Override
	public float getExperience() 
	{
		return 0.1f;
	}

	@Override
	public RecipeUseMethod getUseMethod() 
	{
		return RecipeUseMethod.SMOKING;
	}

}
