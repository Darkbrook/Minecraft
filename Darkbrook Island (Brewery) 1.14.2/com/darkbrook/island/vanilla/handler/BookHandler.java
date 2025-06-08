package com.darkbrook.island.vanilla.handler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.mmo.itemstack.ItemStackWriter;

public class BookHandler implements IRegistryHandler
{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
				
		Player player;
		ItemStack stack;
		
		if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND && isBook(stack = event.getItem()) && (player = event.getPlayer()).getCooldown(Material.BOOK) == 0)
		{
			if(player.getWorld().getEnvironment() == Environment.NORMAL)
			{
				ConjurationHelper.getInstance().teleport(player);
			}
			else
			{
				ConjurationHelper.getInstance().playInvalid(player);
			}

			stack.setAmount(stack.getAmount() - 1);
			player.setCooldown(Material.BOOK, 80);
		}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		if(event.getEntityType() == EntityType.ENDERMAN && Math.random() < 0.5f) event.getDrops().add(generateBook());
	}
			
	private boolean isBook(ItemStack stack)
	{
		ItemMeta meta;
		return stack != null && stack.hasItemMeta() && (meta = stack.getItemMeta()).hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Book of Conjuration");
	}
	
	public static ItemStack generateBook()
	{
		ItemStackWriter writer = new ItemStackWriter(Material.BOOK);
		writer.setDisplayName(ChatColor.YELLOW + "Book of Conjuration");
		writer.setLore(ChatColor.GRAY + "" + ChatColor.ITALIC + "Teleports the wielder to spawn upon consumption.");
		return writer.apply();
	}
	
}
