package com.darkbrook.library.gameplay.table.table;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.event.CancelableEvent;
import com.darkbrook.library.event.tick.async.AsyncEntityTickCycleEvent;
import com.darkbrook.library.gameplay.itemstack.DarkbrookItemStack;
import com.darkbrook.library.gameplay.table.event.TableEntityDeathEvent;
import com.darkbrook.library.gameplay.table.event.TableHitEvent;
import com.darkbrook.library.gameplay.table.event.cancelable.TableBreakEvent;
import com.darkbrook.library.gameplay.table.event.cancelable.TableBreakReason;
import com.darkbrook.library.gameplay.table.event.cancelable.TableCreateEvent;
import com.darkbrook.library.plugin.registry.IRegistryValue;

public class TableMapping implements Listener, IRegistryValue
{
	
	private static final Map<ArmorStand, Table> tableMapping = new HashMap<ArmorStand, Table>();

	@EventHandler
	public void onEntityFind(AsyncEntityTickCycleEvent event)
	{
		
		Entity entity = event.getEntity();
		Table table;
		
		if(entity instanceof ArmorStand && (table = load((ArmorStand) entity)) != null)
		{
			table.updateLighting(true);
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		
		if(event.getBlockFace() == BlockFace.UP && event.getHand() == EquipmentSlot.HAND)
		{

			if(event.getAction() == Action.RIGHT_CLICK_BLOCK) 
			{
				
				Player player = event.getPlayer();
				
				ItemStack stack = player.getInventory().getItemInMainHand();
				Location location = event.getClickedBlock().getLocation();
				
				Table table = load(location);
				
				if(table == null && stack.getType() != Material.AIR)
				{
					
					if(stack.getType().isBlock())
					{
						return;
					}
					
					ItemStack tableStack = stack.clone();
					tableStack.setAmount(1);
					
					TableCreateEvent cancelable;

					if(!CancelableEvent.isCanceled(cancelable = new TableCreateEvent(player, location.getBlock(), tableStack)))
					{		
						DarkbrookItemStack dstack = new DarkbrookItemStack(stack);
						player.getInventory().setItemInMainHand(dstack.subtract(1));
						
						table = new Table(cancelable.getItemStack(), location);
						table.spawn(player, cancelable.getOffsetY());
						table.updateLighting(true);

						tableMapping.put(table.getArmorStand(), table);
						event.setCancelled(true);
					}

				}
				else if(table != null && stack.getType() == Material.AIR)
				{
					
					if(!CancelableEvent.isCanceled(new TableBreakEvent(event.getPlayer(), table, TableBreakReason.ITEM_PICKUP)))
					{
						table.destroy();
						table.dropItem(player);
						table.updateLighting(false);
						tableMapping.remove(table.getArmorStand());
						event.setCancelled(true);
					}
					
				}
				
			}
			else if(event.getAction() == Action.LEFT_CLICK_BLOCK) 
			{

				Table table;
				
				if((table = load(event.getClickedBlock().getLocation())) != null)
				{
					new TableHitEvent(table, event.getPlayer()).register();
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
				
		if(!event.isCancelled())
		{
		
			Table table;
			Location location = event.getBlock().getLocation().clone();
			
			if((table = load(location)) != null)
			{
								
				if(!CancelableEvent.isCanceled(new TableBreakEvent(event.getPlayer(), table, TableBreakReason.BLOCK_BREAK)))
				{
					table.destroy();
					table.dropItem();
					table.updateLighting(false);
					tableMapping.remove(table.getArmorStand());
				}
				else
				{
					event.setCancelled(true);
				}
				
			}
			else if((table = load(location.add(0, 1, 0))) != null || (table = load(location.add(0, 1, 0))) != null)
			{
				event.setCancelled(true);
			}

		}
		
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event)
	{
		
		Entity entity = event.getRightClicked();

		if(!event.isCancelled() && entity instanceof ArmorStand && load((ArmorStand) entity) != null)
		{
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		
		Entity entity = event.getEntity();
		
		if(!event.isCancelled() && entity instanceof ArmorStand && load((ArmorStand) entity) != null)
		{
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		
		Entity entity = event.getEntity();
		Table table;
		
		if(entity instanceof ArmorStand && (table = load((ArmorStand) event.getEntity())) != null)
		{
			new TableEntityDeathEvent(table).register();
			
			table.dropItem();
			table.updateLighting(false);
			
			tableMapping.remove(table.getArmorStand());
		}
		
	}
	
	private static Table load(ArmorStand stand)
	{
		return load(stand, null);
	}
	
	private static Table load(ArmorStand stand, Location location)
	{
		
		if(!tableMapping.containsKey(stand))
		{			
			String name = stand.getCustomName();
			
			if(name != null && name.startsWith("Table"))
			{
				Table table = new Table(stand);
				tableMapping.put(stand, table);
				return table;				
			}
			else
			{
				return null;
			}

		}
		else
		{
			Table table = tableMapping.get(stand);
			return location == null || table.getLocation().distance(location) == 0 ? table : null;
		}
		
	}
	
	private static Table load(Location location)
	{
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 2, 2, 2);
		Table table;
		
		for(Entity entity : entities) if(entity instanceof ArmorStand && (table = load((ArmorStand) entity, location)) != null)
		{
			return table;
		}
		
		return null;
	}
	
}
