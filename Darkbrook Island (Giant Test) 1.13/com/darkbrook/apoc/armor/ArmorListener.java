package com.darkbrook.apoc.armor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.event.armor.PlayerEquipArmorEvent;
import com.darkbrook.library.event.armor.PlayerUnEquipArmorEvent;
import com.darkbrook.library.gameplay.itemstack.DarkbrookItemStack;
import com.darkbrook.library.plugin.registry.IRegistryValue;

public class ArmorListener implements Listener, IRegistryValue
{
	
	private Map<Player, int[]> resistanceMapping = new HashMap<Player, int[]>();
	
	@EventHandler
	public void onPlayerEquipArmor(PlayerEquipArmorEvent event)
	{
		int[] resistance = getResistance(event.getArmor());
		Player player = event.getPlayer();
		
		if(!resistanceMapping.containsKey(player))
		{
			resistanceMapping.put(player, resistance);
		}
		else for(int i = 0; i < resistance.length; i++)
		{
			resistanceMapping.get(player)[i] += resistance[i];
		}
		
		onResistanceUpdate(player);
	}
	
	@EventHandler
	public void onPlayerUnEquipArmor(PlayerUnEquipArmorEvent event)
	{
		int[] resistance = getResistance(event.getArmor());
		Player player = event.getPlayer();
		
		for(int i = 0; i < resistance.length; i++)
		{
			resistanceMapping.get(player)[i] -= resistance[i];
		}
		
		onResistanceUpdate(player);
	}
	
	private void onResistanceUpdate(Player player)
	{
		int[] resistance = resistanceMapping.get(player);
		Bukkit.broadcastMessage(player.getName() + ": " + resistance[0] + ", " + resistance[1] + ", " + resistance[2]);
	}
	
	private int[] getResistance(ItemStack stack)
	{
		List<String> lore = new DarkbrookItemStack(stack).openMeta().getLore();
		int[] resistance = new int[3];
		
		if(lore != null && ChatColor.stripColor(lore.get(1)).equals("Resistance:"))
		for(int i = 0; i < resistance.length; i++)
		{
			resistance[i] = Integer.parseInt(ChatColor.stripColor(lore.get(i + 2)).split("% ")[0]);		
		}
		
		return resistance;
	}

}
