package com.darkbrook.library.gameplay.table.table;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.darkbrook.island.gameplay.visual.VisualRepository;
import com.darkbrook.library.event.CancelableEvent;
import com.darkbrook.library.gameplay.player.CardinalDirection;
import com.darkbrook.library.gameplay.table.ArmorStandWrapper;
import com.darkbrook.library.gameplay.table.event.cancelable.TableLightEvent;
import com.darkbrook.library.util.helper.StringHelper;
import com.darkbrook.library.util.packet.PacketBlockChange;
import com.darkbrook.library.util.runnable.RunnableState;
import com.darkbrook.library.util.runnable.SingleRunnable;

public class Table
{
			
	private ArmorStandWrapper stand;
	private ItemStack stack;
	private Location[] lights;
	private Location location;
	private UUID ownerUuid;
	private String identity;

	public Table(ItemStack stack, Location location)
	{
		this.stack = stack;
		this.location = location;
		
		stand = new ArmorStandWrapper(null);
		lights = new Location[] { location.clone().add(0, -1, 0), location.clone().add(0, -2, 0) };
		identity = "Table:" + StringHelper.blockLocation(location);
	}

	public Table(ArmorStand stand)
	{
		this.stand = new ArmorStandWrapper(stand);

		String[] array = (identity = stand.getCustomName()).split(":");
		
		stack = stand.getItemInHand();
		location = StringHelper.location(stand.getWorld(), array[1]);
		lights = new Location[] { location.clone().add(0, -1, 0), location.clone().add(0, -2, 0) };
		ownerUuid = UUID.fromString(array[2]);
	}

	public void spawn(Player player, float offsetY)
	{

		if(!stand.isAlive())
		{			
			Location location = this.location.clone();
			TableDirection.fromDirection(CardinalDirection.fromYaw(player.getLocation().getYaw())).updateLocation(location, offsetY);

			stand.spawn(location);
			stand.setProperties(false, true, false, false, false, true, false, true, false);
			stand.setName(identity + ":" + (ownerUuid = player.getUniqueId()), false);
			stand.setPose(new Vector(180, 0, 0), new Vector(0, 0, 180), new Vector(0, 0, 90), new Vector(180, 0, 0), new Vector(180, 0, 0));

			SingleRunnable.execute(() -> stand.setItemInHand(stack), RunnableState.SYNC, 1);
		}
		
	}

	public void updateLighting(boolean isIlluminated)
	{
		
		if(isIlluminated)
		{
			
			TableLightEvent event;
			
			if(!CancelableEvent.isCanceled(event = new TableLightEvent(this, Material.SEA_LANTERN, 64)))
			for(Location light : lights)
			{
				PacketBlockChange packet = new PacketBlockChange(light, event.getMaterial());
				packet.send(location, event.getRadius());
			}
			
		}
		else
		{
			
			for(Location light : lights)
			{
				PacketBlockChange packet = new PacketBlockChange(light);
				SingleRunnable.execute(() -> packet.send(location, 64), RunnableState.SYNC, 2);				
			}

		}

	}

	public void dropItem(Player player)
	{
		
		if(player.getInventory().firstEmpty() != -1) 
		{
			player.getInventory().addItem(stack);
		}
		else
		{
			dropItem();
		}
		
	}
	
	public void dropItem()
	{
		location.getWorld().dropItem(location.clone().add(0.5, 1.2, 0.5), stack);
	}
	
	public void destroy()
	{
		stand.destroy();
		VisualRepository.tableInteract.play(location);
	}
	
	public ArmorStand getArmorStand()
	{
		return stand.getArmorStand();
	}
	
	public ItemStack getItemStack() 
	{
		return stack;
	}
	
	public void setItemStack(ItemStack stack) 
	{
		stand.setItemInHand(this.stack = stack);
	}
	
	public Location getLocation() 
	{
		return location;
	}
	
	public UUID getOwnerUuid()
	{
		return ownerUuid;
	}
	
}
