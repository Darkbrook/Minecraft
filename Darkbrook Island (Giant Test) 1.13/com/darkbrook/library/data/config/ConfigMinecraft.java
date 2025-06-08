package com.darkbrook.library.data.config;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.DarkbrookIsland;
import com.darkbrook.library.data.object.ObjectDataQueue;
import com.darkbrook.library.gameplay.itemstack.DarkbrookItemStack;
import com.darkbrook.library.util.ResourceLocation;

public class ConfigMinecraft extends Config 
{

	public ConfigMinecraft(String name)
	{
		super(name);
	}
	
	public ConfigMinecraft(ResourceLocation location)
	{
		super(location);
	}
	
	public ItemStack getItemStack(String key)
	{
		ObjectDataQueue data = getData(key, "type/amount/dura/name");
		ObjectDataQueue lore = getDataArray(key, "lore");
		
		DarkbrookItemStack dstack = new DarkbrookItemStack(data.s(), data.i(), data.i());
		String name = lore.s();
		
		dstack.openMeta();
		
		if(name != null)
		{
			dstack.setName(name);
		}
		
		if(lore != null)
		{
			dstack.setLore(lore.toList());
		}
		
		return dstack.applyMeta();
	}
	
	public void setItemStack(String key, ItemStack stack)
	{
		
		DarkbrookItemStack dstack = new DarkbrookItemStack(stack).openMeta();
		
		setData(new Object[]
		{
			dstack.getTypeName(),
			dstack.getAmount(),
			dstack.getDurability(),
			dstack.getName()
		},
		key, "type/amount/dura/name");
		
		List<String> lore = dstack.getLore();

		if(lore != null)
		{
			setDataArray(lore.toArray(), key, "lore");
		}
		
	}

	public Location getLocation(String key)
	{
		ObjectDataQueue data = getData(key, "world/x/y/z/yaw/pitch");
		return new Location(DarkbrookIsland.getWorldLoader().loadExistingWorld(data.s()), data.d(), data.d(), data.d(), data.f(), data.f());
	}
	
	public void setLocation(String key, Location location) 
	{
		setData(new Object[]
		{
			location.getWorld().getName(),
			location.getX(),
			location.getY(),
			location.getZ(),
			location.getYaw(),
			location.getPitch(),
		}, 
		key, "world/x/y/z/yaw/pitch");
	}
	
	public void mapLocation(String prefix, Location location)
	{
		setValue(getLocationKey(prefix, location), "");
	}
	
	public boolean hasLocation(String prefix, Location location)
	{
		return hasValue(prefix + "." + getLocationKey(prefix, location));
	}
	
	private String getLocationKey(String prefix, Location location)
	{
		int hash = location.toString().hashCode();
		
		if(prefix == null)
		{
			prefix = "";
		}
		else
		{
			prefix += ".";
		}
		
		return prefix + "location." + hash;
	}

}
