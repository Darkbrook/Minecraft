package com.darkbrook.island.vanilla.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.darkbrook.island.common.registry.command.CommandUser;
import com.darkbrook.island.common.registry.command.IRegistryCommand;
import com.darkbrook.island.mmo.equipment.armor.ArmorMaterial;
import com.darkbrook.island.mmo.equipment.armor.ArmorRegistry;
import com.darkbrook.island.mmo.equipment.armor.ArmorType;
import com.darkbrook.island.mmo.equipment.weapon.MeleeWeaponMaterial;
import com.darkbrook.island.mmo.equipment.weapon.MeleeWeaponRegistry;
import com.darkbrook.island.mmo.equipment.weapon.MeleeWeaponType;
import com.darkbrook.island.vanilla.handler.BookHandler;

public class DebugCommand implements IRegistryCommand
{

	@Override
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length)
	{
		return user.isSyntax(user.isPlayer() && length == 0, arguments, (u, a, l) -> onPlayerCommand((Player) u, a, l));
	}
	
	public boolean onPlayerCommand(Player player, String[] arguments, int length) 
	{
		Inventory inventory = Bukkit.createInventory(null, 54, "Debug Inventory");
		inventory.clear();
		
		addArmor(inventory);
		addMeleeWeapons(inventory);
		
		inventory.addItem(BookHandler.generateBook());
		((Player) player).openInventory(inventory);
		return true;
	}

	@Override
	public String getPrefix() 
	{
		return "debug";
	}
	
	private void addArmor(Inventory inventory)
	{
		
		int row = 0;
		int column = 0;

		for(ArmorMaterial material : ArmorMaterial.values()) 
		{
			column = 0;

			for(ArmorType type : ArmorType.values())
			{
				inventory.setItem(row * 9 + column++, ArmorRegistry.getArmor(material).generateMax(type));
			}
			
			row++;
		}
		
	}
	
	private void addMeleeWeapons(Inventory inventory)
	{
		
		int row = 0;
		int column = 0;
		
		for(MeleeWeaponMaterial material : MeleeWeaponMaterial.values())
		{
			column = 0;
			
			for(MeleeWeaponType type : MeleeWeaponType.values())
			{
				inventory.setItem(row * 9 + 5 + column++, MeleeWeaponRegistry.getWeapon(material).generateMax(type));
			}
			
			row++;
		}
		
	}

}
