package com.darkbrook.island.vanilla.handler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.darkbrook.island.common.gameplay.visual.VisualSound;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.common.registry.visual.VisualRegistry;
import com.darkbrook.island.common.util.helper.math.MathHelper;
import com.darkbrook.island.common.util.packet.PacketBlockChange;

public class ExplosionHandler implements IRegistryHandler
{
	
	private VisualRegistry visual;
	
	public ExplosionHandler()
	{
		visual = new VisualRegistry();
		visual.initalize(new VisualSound(Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f));
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event)
	{
		event.blockList().forEach(this::onBlockHide);
		event.setCancelled(true);
	}
	
	private void onBlockHide(Block block)
	{
		new PacketBlockChange(block.getLocation(), block.getType().isSolid() ? Material.BARRIER : Material.AIR).send();
		Bukkit.getScheduler().scheduleSyncDelayedTask(RegistryPlugin.getInstance(), () -> onBlockShow(block), (MathHelper.random(20) * 2) + 80);
	}
	
	private void onBlockShow(Block block)
	{		
		new PacketBlockChange(block.getLocation()).send();
		visual.playGlobal(block.getLocation());
	}

}
