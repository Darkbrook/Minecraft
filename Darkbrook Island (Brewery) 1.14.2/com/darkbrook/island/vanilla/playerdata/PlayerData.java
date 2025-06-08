package com.darkbrook.island.vanilla.playerdata;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.darkbrook.island.common.config.AutoSaveConfig;
import com.darkbrook.island.common.config.property.ConfigProperty.PropertyFloat;
import com.darkbrook.island.common.config.property.ConfigProperty.PropertyLong;
import com.darkbrook.island.common.config.property.ConfigProperty.PropertyString;

public class PlayerData extends AutoSaveConfig
{
	
	private static PlayerData playerData;
	
	private PropertyString username;
	private PropertyFloat bloodAlcohol;
	private PropertyLong playtime;

	private Player player;
	
	public PlayerData() 
	{
		super("playerdata.yml");
		
		initialize(username = new PropertyString("username"));
		initialize(bloodAlcohol = new PropertyFloat("bloodAlcohol"));
		initialize(playtime = new PropertyLong("playtime"));
		
		PlayerData.playerData = this;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		setPlayer(event.getPlayer());
		setUsername();
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public String getUsername()
	{
		return username.getPropertyValue(player.getName());
	}
	
	public void setUsername()
	{
		username.setPropertyValue(player.getName());
	}
		
	public float getBloodAlcohol()
	{
		return bloodAlcohol.getPropertyValue(0.0f);
	}
	
	public void setBloodAlcohol(float bloodAlcohol)
	{
		this.bloodAlcohol.setPropertyValue(bloodAlcohol);
	}
	
	public long getPlaytime()
	{
		return playtime.getPropertyValue(0L);
	}
	
	public void setPlaytime(long playtime)
	{
		this.playtime.setPropertyValue(playtime);
	}
	
	@Override
	protected String getMasterKey() 
	{
		return player.getUniqueId().toString();
	}
	
	public static PlayerData getInstance()
	{
		return playerData;
	}

}
