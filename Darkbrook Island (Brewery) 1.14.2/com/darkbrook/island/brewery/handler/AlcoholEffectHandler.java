package com.darkbrook.island.brewery.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.darkbrook.island.brewery.util.BreweryItemStack;
import com.darkbrook.island.common.gameplay.event.PlayerUpdateEvent;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.vanilla.playerdata.PlayerData;

public class AlcoholEffectHandler implements IRegistryHandler
{
	
	private PlayerData playerData;
	
	public AlcoholEffectHandler()
	{
		playerData = PlayerData.getInstance();
	}
	
	@EventHandler
	public void onPlayerConsumeItem(PlayerItemConsumeEvent event)
	{
		BreweryItemStack stack = new BreweryItemStack(event.getItem());

		if(!stack.hasAlcohol())
		{
			return;
		}
				
		playerData.setPlayer(event.getPlayer());
		playerData.setBloodAlcohol(getBloodAlcohol(stack.getAlcoholByVolume(), playerData.getBloodAlcohol()));	
	}
	
	@EventHandler
	public void onPlayerUpdate(PlayerUpdateEvent event)
	{
		playerData.setPlayer(event.getPlayer());
		playerData.setBloodAlcohol(getBloodAlcohol(playerData.getBloodAlcohol()));
	}
	
	private float getBloodAlcohol(float beverageAbv, float bloodAlcohol)
	{
		return Math.min(bloodAlcohol + (beverageAbv * 0.3058f / 0.4f), 1.0f);
	}
	
	private float getBloodAlcohol(float bloodAlcohol)
	{
		return Math.max(bloodAlcohol - 0.00075f, 0.0f);
	}

}
