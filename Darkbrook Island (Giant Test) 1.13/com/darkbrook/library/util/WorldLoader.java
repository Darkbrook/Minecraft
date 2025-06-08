package com.darkbrook.library.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldLoader 
{

	private List<String> names;
	private ResourceLocation location;
	private String prefix;
	
	public WorldLoader(ResourceLocation location)
	{
		this.names = new ArrayList<String>();
		this.location = location;
		this.prefix = location.getFile().getName() + "/";
		
		loadWorlds();
	}
	
	public List<String> getNames()
	{
		return names;
	}
	
	public World loadExistingWorld(String name)
	{
		return names.contains(name) ? loadWorld(name) : null;
	}
	
	public String getDisplayName(String name) 
	{
		return name.replace(prefix, "");
	}
	
	public boolean hasWorld(String name)
	{
		return names.contains(name);
	}
	
	private World loadWorld(String name) 
	{
		if(!name.startsWith(prefix)) 
		{
			name = prefix + name;
		}
		
		return Bukkit.getWorld(name) == null ? Bukkit.createWorld(new WorldCreator(name)) : Bukkit.getWorld(name);
	}
	
	private void loadWorlds()
	{
		
		for(File file : location.getFile().listFiles())
		{
			String name = file.getName();
			
			names.add(name);
			loadWorld(name);
		}
		
	}
	
}
