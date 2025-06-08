package com.darkbrook.library.gameplay.itemstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.darkbrook.library.chat.message.CustomMessage;

public abstract class ItemStackMetaWrapper extends ItemStackWrapper 
{

	private ItemMeta meta;

	public ItemStackMetaWrapper(ItemStack stack)
	{
		super(stack);
	}

	public ItemStackMetaWrapper(String typeName, int amount, int durability)
	{
		super(typeName, amount, durability);
	}
	
	public ItemStackMetaWrapper openMeta()
	{
		if(meta == null)
		{
			(meta = stack.getItemMeta()).addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}

		return this;
	}
	
	public ItemStack applyMeta()
	{
		stack.setItemMeta(meta);
		return stack;
	}
	
	public void addEnchantment(Enchantment enchantment, int level, boolean isVisible) 
	{
		if(!isVisible)
		{
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		meta.addEnchant(enchantment, level, isVisible);
	}
	
	public void addItemFlags(ItemFlag... flag) 
	{
		meta.addItemFlags(flag);
	}
	
	public void removeItemFlags(ItemFlag... flag) 
	{
		meta.removeItemFlags(flag);
	}
	
	public List<String> getLore() 
	{
		return meta.hasLore() ? meta.getLore() : null;
	}

	public void setLore(List<String> lore)
	{
		for(int i = 0; i < lore.size(); i++)
		{
			lore.set(i, new CustomMessage(lore.get(i)).toString());
		}
		
		meta.setLore(lore);
	}
	
	public void setLore(String... lore)
	{
		setLore(Arrays.asList(lore));
	}
	
	public void addLore(String... lore)
	{
		List<String> list = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
		list.addAll(Arrays.asList(lore));
		setLore(list);
	}
	
	public String getName()
	{
		return meta.hasDisplayName() ? meta.getDisplayName() : getBaseName();
	}
	
	public void setName(String name)
	{
		meta.setDisplayName(new CustomMessage(name).toString());
	}	
	
}
