package com.darkbrook.apoc.entity;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.darkbrook.island.gameplay.visual.VisualRepository;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.helper.EntityHelper;
import com.darkbrook.library.util.helper.math.MathHelper;
import com.darkbrook.library.util.helper.math.Vector2i;
import com.darkbrook.library.util.helper.math.VectorHelper;
import com.darkbrook.library.util.packet.PacketBlockChange;
import com.darkbrook.library.util.runnable.RunnableState;
import com.darkbrook.library.util.runnable.SingleRunnable;

public class EntityApocGiantListener implements Listener, IRegistryValue
{
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		
		Entity damager = event.getDamager();
		
		if(damager instanceof Giant)
		{
			
			Location location = damager.getLocation();
			Entity victim = event.getEntity();
			
			if(location.distance(victim.getLocation()) > 4)
			{
				event.setCancelled(true);
			}
			else
			{
				VectorHelper.launchAway(victim, location, new Vector(8, 1, 8));
				event.setDamage(10);
			}
			
		}
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		
		Entity victim = event.getEntity();
		
		if(victim instanceof Giant) switch(event.getCause())
		{
			case PROJECTILE    : event.setDamage(event.getDamage() / 8.0); break;
			case ENTITY_ATTACK : event.setDamage(event.getDamage() / 2.0); break;
			case FALL		   : VisualRepository.groundCrash.play(victim.getLocation()); SingleRunnable.execute(() -> damageGround(victim.getLocation().clone().add(0, -1, 0)), RunnableState.ASYNC, 6);
			default            : event.setCancelled(true); break;
		}
		
	}
	
	private void damageGround(Location location)
	{
		
		int x = location.getBlockX();
		int z = location.getBlockZ();
		
		for(int i = 0; i < 32; i++)
		{
			
			List<Vector2i> positions = MathHelper.bresenham(x, z, MathHelper.randomSignedInt(64) + x, MathHelper.randomSignedInt(64) + z);
			
			for(int p = 0; p < positions.size(); p++)
			{
				Vector2i position = positions.get(p);
				Location block = new Location(location.getWorld(), position.x, location.getBlockY(), position.y);
			
				if(Math.random() > 0.8)
				{
					VisualRepository.groundCrack.play(block, p * 3);
				}
								
				PacketBlockChange packet = new PacketBlockChange(block);
				
				SingleRunnable.execute(() -> new PacketBlockChange(block, Material.GRASS_PATH).send(location, 128), RunnableState.ASYNC, p * 2);
				SingleRunnable.execute(() -> packet.send(location, 128), RunnableState.ASYNC, p * 4);
				SingleRunnable.execute(() -> packet.send(location, 128), RunnableState.ASYNC, p * 8);
			}
			
		}
		
		EntityHelper.radial(Player.class, location, 64).forEach(victim -> 
		{
			double distance = MathHelper.clamp(victim.getLocation().distance(location), 8, 32);
			double damage   = Math.pow((32 - distance) / 8.0d, 2);
			 
			int force = (int) Math.round(damage);
			victim.damage(damage);
			
			SingleRunnable.execute(() -> victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, force * 20, 0, true, false)), RunnableState.SYNC, 1);
			VectorHelper.launchAway(victim, location, new Vector(force, 1, force));
		});
		
	}
	
}
