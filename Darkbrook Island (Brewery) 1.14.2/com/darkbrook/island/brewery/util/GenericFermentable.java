package com.darkbrook.island.brewery.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import com.darkbrook.island.brewery.registry.IFermentable;
import com.darkbrook.island.brewery.registry.IFermentableLiquid;
import com.darkbrook.island.common.util.helper.StringHelper;

public class GenericFermentable implements IFermentable
{

	private String beverageName;
	private String beverageLiquidKey;
	private String ingredientName;
	private Material source;
	private IFermentableLiquid liquid;
	private float beverageAbv;	
	private int fermentationDays;

	public GenericFermentable(String beverageName, Material source, IFermentableLiquid liquid, float beverageAbv, int fermentationDays)
	{
		this.beverageName = beverageName;
		this.ingredientName = StringHelper.buildCommonName(source.toString());
		this.source = source;
		this.liquid = liquid;
		this.beverageAbv = beverageAbv;
		this.fermentationDays = fermentationDays;
		
		beverageLiquidKey = beverageName.replace(" ", "") + "Liquid";
	}

	@Override
	public ItemStack generateProduct(ItemStack stack) 
	{
		BreweryItemStack breweryStack = new BreweryItemStack(stack);
		stack = breweryStack.getFermentedItemStack(6);//fermentationDays * 1200);

		if(stack != null)
		{
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "Fermented " + ingredientName);				
			stack.setItemMeta(meta);
		}
		else
		{
			stack = generateLiquid();
		}
	
		return stack;
	}

	@Override
	public ItemStack generateLiquid() 
	{
		BreweryItemStack breweryStack = new BreweryItemStack(new ItemStack(liquid.getMaterial()));
		ItemStack stack = breweryStack.getLiquidItemStack(beverageLiquidKey);
		
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(liquid.getDisplayColor() + beverageName);	
		
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public ItemStack generateBeverage() 
	{
		BreweryItemStack breweryStack = new BreweryItemStack(new ItemStack(Material.POTION));
		ItemStack stack = breweryStack.getBeverageItemStack(beverageName, beverageAbv);
		
		PotionMeta meta = (PotionMeta) stack.getItemMeta();
		
		meta.setDisplayName(ChatColor.WHITE + beverageName);
		meta.addCustomEffect(liquid.getPotionEffect(), true);
		meta.setColor(liquid.getColor());
		
		stack.setItemMeta(meta);
		return stack;
	}
	
	@Override
	public Material getSource()
	{
		return source;
	}

	@Override
	public boolean isFermentableLiquid(ItemStack stack) 
	{
		return stack != null && new BreweryItemStack(stack).isLiquid(beverageLiquidKey);
	}

}
