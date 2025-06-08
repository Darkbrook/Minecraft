package com.darkbrook.island.vanilla.claim.data;

import java.util.ArrayList;
import java.util.List;

import com.darkbrook.island.common.config.property.ConfigProperty.PropertyStringList;
import com.darkbrook.island.common.config.property.PropertyConfig;

public class ClaimProviderData extends PropertyConfig
{
	
	private PropertyStringList players;
	
	public ClaimProviderData(String provider) 
	{
		super("territory/provider," + provider + ".yml");
		initialize(players = new PropertyStringList("players"));
	}
	
	public void addProvider(String playerName)
	{
		List<String> providers = getProviders();
		providers.add(playerName);
		players.setPropertyValue(providers);
		save();
	}
	
	public void removeProvider(String playerName)
	{
		List<String> providers = getProviders();
		providers.remove(playerName);
		players.setPropertyValue(providers);
		save();
	}
	
	public List<String> getProviders()
	{
		return players.getPropertyValue(new ArrayList<String>());
	}
	
	public boolean hasProvider(String playerName)
	{
		return getProviders().contains(playerName);
	}

	@Override
	protected String getMasterKey() 
	{
		return "claim";
	}
	
}
