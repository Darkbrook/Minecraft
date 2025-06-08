package com.darkbrook.island.mmo.itemstack;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackWriter extends ItemStackWrapper
{

	private ItemMeta meta;
	private boolean isHidingAttributes;
	private boolean isGlowing;
	
	public ItemStackWriter(Material material, int amount) 
	{
		super(material, amount);
		meta = stack.getItemMeta();
	}	
	
	public ItemStackWriter(Material material)
	{
		this(material, 1);
	}
	
	public void setDisplayName(String name)
	{
		meta.setDisplayName(name);
	}
	
	public void setLore(String... lore)
	{
		meta.setLore(Arrays.asList(lore));
	}
		
	public void addAttribute(Attribute attribute, String name, double amount, EquipmentSlot slot)
	{
		meta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), name, amount, Operation.ADD_NUMBER, slot));
	}
	
	public void addAttribute(Attribute attribute, String name, double amount)
	{
		meta.addAttributeModifier(attribute, new AttributeModifier(name, amount, Operation.ADD_NUMBER));
	}
	
	public void setHidingAttributes(boolean isHidingAttributes)
	{
		if(this.isHidingAttributes == isHidingAttributes)
		{
			return;
		}
		
		if(isHidingAttributes)
		{
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		else
		{
			meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		
		this.isHidingAttributes = isHidingAttributes;
	}
	
	public void setGlowing(boolean isGlowing)
	{
		if(this.isGlowing == isGlowing)
		{
			return;
		}
		
		if(isGlowing)
		{
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(Enchantment.DURABILITY, 1, false);
		}
		else
		{
			meta.removeEnchant(Enchantment.DURABILITY);
			meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		this.isGlowing = isGlowing;	
	}
	
	public ItemStack apply()
	{
		stack.setItemMeta(meta);
		return stack;
	}
	
}
