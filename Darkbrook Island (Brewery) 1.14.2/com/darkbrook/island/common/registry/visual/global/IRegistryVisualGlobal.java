package com.darkbrook.island.common.registry.visual.global;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.visual.local.IRegistryVisualLocal;

public interface IRegistryVisualGlobal extends IRegistryVisualLocal
{
	public void playGlobal(Player player);
	
	public void playGlobal(Location location);
}
