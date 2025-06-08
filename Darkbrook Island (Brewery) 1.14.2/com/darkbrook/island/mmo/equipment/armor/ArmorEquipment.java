package com.darkbrook.island.mmo.equipment.armor;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.mmo.itemstack.ItemStackWriter;


public class ArmorEquipment
{
	
	private Material[] armorItemMaterials;
	private String[] armorItemNames;
	private ArmorMaterial armorMaterial;
	private int armorMinimum;
	private int armorMaximum;
		
	public ArmorEquipment(String[] armorItemNames, ArmorMaterial armorMaterial, int armorMinimum, int armorMaximum) 
	{
		armorItemMaterials = new Material[]
		{
			Material.valueOf(armorMaterial.getVanillaName() + "_HELMET"),
			Material.valueOf(armorMaterial.getVanillaName() + "_CHESTPLATE"),
			Material.valueOf(armorMaterial.getVanillaName() + "_LEGGINGS"),
			Material.valueOf(armorMaterial.getVanillaName() + "_BOOTS"),
		};
		
		this.armorItemNames = armorItemNames;
		this.armorMaterial = armorMaterial;
		this.armorMinimum = armorMinimum;
		this.armorMaximum = armorMaximum;
	}

	public ItemStack generate(ArmorType type) 
	{
		return generateEquipment(type, new Random().nextInt(armorMaximum - armorMinimum + 1) + armorMinimum);
	}

	public ItemStack generateMax(ArmorType type) 
	{
		return generateEquipment(type, armorMaximum);
	}

	public ArmorMaterial getMaterial()
	{
		return armorMaterial;
	}
	
	private ItemStack generateEquipment(ArmorType type, int armorValue)
	{		
		ItemStackWriter writer = new ItemStackWriter(armorItemMaterials[type.ordinal()]);

		writer.setDisplayName(armorMaterial.getDisplayName() + " " + armorItemNames[type.ordinal()]);
		writer.setLore(String.format("%s%s Armor: %s%d%%", ChatColor.GRAY, armorMaterial.getWeightName(), ChatColor.DARK_PURPLE, armorValue));
		
		writer.setHidingAttributes(true);
		writer.addAttribute(Attribute.GENERIC_ARMOR, "generic.armor", armorValue / 5.0d, type.getEquipmentSlot());
		writer.setGlowing(armorMaterial == ArmorMaterial.HARDENED_IRON);

		return writer.apply();
	}
	
}
