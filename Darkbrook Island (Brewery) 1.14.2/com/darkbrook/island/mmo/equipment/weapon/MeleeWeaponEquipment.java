package com.darkbrook.island.mmo.equipment.weapon;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.mmo.itemstack.ItemStackWriter;

public class MeleeWeaponEquipment 
{
	
	private Material[] weaponItemMaterials;
	private String[] weaponItemNames;
	private MeleeWeaponMaterial weaponMaterial;
	private int abilityMinimum;
	private int abilityMaximum;
	
	public MeleeWeaponEquipment(String[] weaponItemNames, MeleeWeaponMaterial weaponMaterial, int abilityMinimum, int abilityMaximum) 
	{
		weaponItemMaterials = new Material[]
		{
			Material.valueOf(weaponMaterial.getVanillaName() + "_HOE"),
			Material.valueOf(weaponMaterial.getVanillaName() + "_SWORD"),
			Material.valueOf(weaponMaterial.getVanillaName() + "_AXE"),
			Material.valueOf(weaponMaterial.getVanillaName() + "_SHOVEL"),
		};
		
		this.weaponItemNames = weaponItemNames;
		this.weaponMaterial = weaponMaterial;
		this.abilityMinimum = abilityMinimum;
		this.abilityMaximum = abilityMaximum;
	}
	
	public ItemStack generate(MeleeWeaponType type)
	{
		return generateEquipment(type, new Random().nextInt(abilityMaximum - abilityMinimum + 1) + abilityMinimum);
	}
	
	public ItemStack generateMax(MeleeWeaponType type) 
	{
		return generateEquipment(type, abilityMaximum);
	}

	public MeleeWeaponMaterial getMaterial()
	{
		return weaponMaterial;
	}
	
	private ItemStack generateEquipment(MeleeWeaponType type, int specialValue)
	{		
		ItemStackWriter writer = new ItemStackWriter(weaponItemMaterials[type.ordinal()]);

		writer.setDisplayName(weaponMaterial.getDisplayName() + " " + weaponItemNames[type.ordinal()]);
		writer.setLore(new String[]
		{
			String.format("%sCurshing: %s%d%%", ChatColor.GRAY, ChatColor.DARK_PURPLE, Math.round(specialValue * type.getCrushing())), 
			String.format("%sPiercing: %s%d%%", ChatColor.GRAY, ChatColor.DARK_PURPLE, Math.round(specialValue * type.getPiercing()))	
		});
		
		writer.setHidingAttributes(true);
		writer.addAttribute(Attribute.GENERIC_ATTACK_SPEED, "generic.attackSpeed", 999999.0d);
		writer.addAttribute(Attribute.GENERIC_ATTACK_DAMAGE, "generic.attackDamage", 0.0d);
		writer.setGlowing(weaponMaterial == MeleeWeaponMaterial.HARDENED_IRON);

		return writer.apply();
	}

}
