package com.darkbrook.library.command.basic.world;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.library.gui.DefaultGuiInterface;
import com.darkbrook.library.item.ItemHandler;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.library.misc.WorldHandler;

public class GuiTypeWorld extends DefaultGuiInterface {

	@Override
	public void onInventoryClick(InventoryClickEvent event, Player player, ClickType clickType, ItemStack itemClicked, ItemStack itemCursor, int slot, boolean isTopInventory) {
		
		event.setCancelled(true);
		
		if(!isTopInventory || itemClicked == null) return;
		
		World world = WorldLoader.getWorld(ChatColor.stripColor(ItemHandler.getDisplayName(itemClicked)));
		String worldName = world.getName().toLowerCase();
		String playerWorldName = player.getWorld().getName().toLowerCase();
		
		if(worldName.equals(playerWorldName)) return;
		
		WorldHandler.setupWorld(world);
		
		UpdateHandler.delay(new UpdateListener(){

			@Override
			public void onUpdate() {
		 		player.closeInventory();				
			}
			
		}, 2);
		
		UpdateHandler.delay(new UpdateListener(){

			@Override
			public void onUpdate() {
				player.teleport(world.getSpawnLocation());
				FormatMessage.info(player, "Teleported to " + WorldLoader.getDisplayWorldName(world.getName()));				
			}
			
		}, 8);
		
	}

}
