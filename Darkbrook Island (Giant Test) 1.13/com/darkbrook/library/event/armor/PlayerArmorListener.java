package com.darkbrook.library.event.armor;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.event.tick.async.AsyncPlayerTickEvent;
import com.darkbrook.library.plugin.DarkbrookPlugin;
import com.darkbrook.library.plugin.registry.IRegistryValue;

public class PlayerArmorListener implements Listener, IRegistryValue
{
	
	private Map<Player, ItemStack[]> armorMapping = new HashMap<Player, ItemStack[]>();
	
	@EventHandler
	public void onAsyncPlayerTick(AsyncPlayerTickEvent event)
	{
		
		Player player = event.getPlayer();
		ItemStack[] contents = player.getInventory().getArmorContents();
		
		if(!armorMapping.containsKey(player))
		{
			for(ItemStack stack : contents) if(stack != null)
			{
				DarkbrookPlugin.executeEventStatic(new PlayerEquipArmorEvent(player, stack, ArmorType.getArmorType(stack)));
			}
			
			armorMapping.put(player, contents);
		}
		else
		{
			
			ItemStack[] contentsLast = armorMapping.get(player);
			ItemStack stack;
			
			boolean isUpdated = false;
			
			for(int i = 0; i < contents.length; i++) if(!isSimilar(stack = contents[i], contentsLast[i]))
			{
				if(stack != null)
				{
					DarkbrookPlugin.executeEventStatic(new PlayerEquipArmorEvent(player, stack, ArmorType.getArmorType(stack)));
				}
				else
				{
					DarkbrookPlugin.executeEventStatic(new PlayerUnEquipArmorEvent(player, stack = contentsLast[i], ArmorType.getArmorType(stack)));
				}
				
				isUpdated = true;
			}
			
			if(isUpdated)
			{
				armorMapping.put(player, contents);
			}

		}
		
	}
	
	private boolean isSimilar(ItemStack now, ItemStack last)
	{
		return (now != null ? now.toString() : "").equals(last != null ? last.toString() : "");
	}
	
}
