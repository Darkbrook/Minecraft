package com.darkbrook.kingdoms.server.item;

import com.darkbrook.kingdoms.server.item.mmo.ArmorSlot;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ArmorSet<T>
{
	public static final ArmorSet<Item> LEATHER = new ArmorSet<>(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);
	public static final ArmorSet<Item> CHAINMAIL = new ArmorSet<>(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS);
	public static final ArmorSet<Item> IRON = new ArmorSet<>(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
	public static final ArmorSet<Item> GOLDEN = new ArmorSet<>(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
	public static final ArmorSet<Item> DIAMOND = new ArmorSet<>(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
	public static final ArmorSet<Item> NETHERITE = new ArmorSet<>(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);
	
	protected final T helmet;
	protected final T chestplate;
	protected final T leggings;
	protected final T boots;
	
	public ArmorSet(T helmet, T chestplate, T leggings, T boots)
	{
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	public T getHelmet()
	{
		return helmet;
	}

	public T getChestplate()
	{
		return chestplate;
	}

	public T getLeggings()
	{
		return leggings;
	}

	public T getBoots()
	{
		return boots;
	}
	
	public T get(ArmorSlot slot)
	{
		return switch (slot)
		{
			case HELMET -> helmet;
			case CHESTPLATE -> chestplate;
			case LEGGINGS -> leggings;
			case BOOTS -> boots;
		};
	}
}
