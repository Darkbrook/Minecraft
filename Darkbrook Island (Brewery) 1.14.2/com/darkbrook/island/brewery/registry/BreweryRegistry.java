package com.darkbrook.island.brewery.registry;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.darkbrook.island.common.gameplay.event.WorldUpdateEvent;
import com.darkbrook.island.common.gameplay.visual.VisualSound;
import com.darkbrook.island.common.registry.RegistryManager;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.common.registry.visual.VisualRegistry;

public class BreweryRegistry extends RegistryManager<IFermentable> implements IRegistryHandler
{

	private VisualRegistry visual;
	
	public BreweryRegistry() 
	{
		super(IFermentable.class);
		
		visual = new VisualRegistry();
		visual.initalize(new VisualSound(Sound.ITEM_BOTTLE_FILL, 1.0f, 1.0f));
	}
	
	@EventHandler
	public void onWorldUpdate(WorldUpdateEvent event)
	{
		for(Chunk chunk : event.getWorld().getLoadedChunks()) 
		for(BlockState state : chunk.getTileEntities()) 
		if(state.getType() == Material.BARREL) 
		{
			onContainerUpdate(((Barrel) state).getInventory());
		}
		
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) 
	{
		if(values.stream().anyMatch(value -> value.isFermentableLiquid(event.getCurrentItem()))) event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryMoveItem(InventoryMoveItemEvent event) 
	{		
		if(values.stream().anyMatch(value -> value.isFermentableLiquid(event.getItem()))) event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) 
	{
		onFermentableAction(event.getBlock(), true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{		
		
		Block block;
		
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND || (block = event.getClickedBlock()).getType() != Material.BARREL)
		{
			return;	
		}
		
		Player player = event.getPlayer();
		ItemStack stack = player.getInventory().getItemInMainHand();
		IFermentable fermentable;
		
		if(stack.getType() == Material.GLASS_BOTTLE && (fermentable = onFermentableAction(block, false)) != null)
		{
			PlayerInventory inventory = player.getInventory();
			stack.setAmount(stack.getAmount() - 1);
			
			if(inventory.firstEmpty() != -1)
			{
				inventory.addItem(fermentable.generateBeverage());
			}
			else
			{
				player.getWorld().dropItem(player.getLocation(), fermentable.generateBeverage());
			}
			
			visual.playGlobal(player);
			event.setCancelled(true);
		}
		
	}

	public void onContainerUpdate(Inventory inventory) 
	{

		ItemStack[] contents = inventory.getContents();
		ItemStack stack;
		
		for(int i = 0; i < contents.length; i++) 
		for(IFermentable fermentable : values)
		if((stack = contents[i]) != null && stack.getType() == fermentable.getSource() && stack.getAmount() == 64)
		{
			inventory.setItem(i, fermentable.generateProduct(stack));
		}
		
	}

	public IFermentable onFermentableAction(Block block, boolean isContinuous) 
	{
		BlockState state = block.getState();
		
		if(state.getType() != Material.BARREL)
		{
			return null;
		}
		
		Inventory inventory = ((Barrel) state).getInventory();
		ItemStack[] contents = inventory.getContents();
				
		for(int i = contents.length - 1; i >= 0; i--) 
		for(IFermentable fermentable : values)
		if(fermentable.isFermentableLiquid(contents[i]))
		{
			
			inventory.setItem(i, null);
			
			if(!isContinuous)
			{
				return fermentable;
			}
			
		}

		return null;
	}

}
