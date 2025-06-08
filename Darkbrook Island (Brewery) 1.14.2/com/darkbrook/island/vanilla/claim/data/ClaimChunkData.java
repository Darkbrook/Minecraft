package com.darkbrook.island.vanilla.claim.data;

import org.bukkit.Chunk;

import com.darkbrook.island.common.config.property.ConfigProperty.PropertyString;
import com.darkbrook.island.common.config.property.PropertyConfig;

public class ClaimChunkData extends PropertyConfig
{
	
	private PropertyString provider;
	private int chunkX;
	private int chunkZ;
	
	public ClaimChunkData(Chunk chunk)
	{
		super("territory/chunk," + chunk.getX() + "," + chunk.getZ() + ".yml");
		initialize(provider = new PropertyString("provider"));
		
		this.chunkX = chunk.getX();
		this.chunkZ = chunk.getZ();
	}
	
	public String getProvider()
	{
		return provider.getPropertyValue(null);
	}
	
	public void setProvider(String provider)
	{
		this.provider.setPropertyValue(provider);
	}
	
	public int getChunkX()
	{
		return chunkX;
	}
	
	public int getChunkZ()
	{
		return chunkZ;
	}
	
	@Override
	protected String getMasterKey() 
	{
		return "claim";
	}
	
}
