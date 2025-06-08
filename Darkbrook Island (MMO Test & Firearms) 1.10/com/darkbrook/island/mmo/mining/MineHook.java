package com.darkbrook.island.mmo.mining;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.experience.Experience;
import com.darkbrook.island.mmo.misc.ToolMaterial;
import com.darkbrook.island.mmo.misc.ToolType;

public class MineHook implements Listener {

	public static final ArrayList<Location> ADD = new ArrayList<Location>();
	public static final ArrayList<Location> REMOVE = new ArrayList<Location>();
	public static final ArrayList<Location> BLOCKS = new ArrayList<Location>();
	public static final HashMap<Location, BlockData> BLOCKDATA = new HashMap<Location, BlockData>();
	
	public static void load(Plugin plugin) {
		
		plugin.getServer().getPluginManager().registerEvents(new MineHook(), plugin);
		
		UpdateHandler.repeat(new UpdateListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onUpdate() {
				
				BLOCKS.addAll(ADD);
				ADD.clear();
				
				for(Location location : BLOCKS) {
					
					if(BLOCKDATA.get(location).cooldown > 0) {
						BLOCKDATA.get(location).cooldown -= 1;
					} else {
						location.getBlock().setType(BLOCKDATA.get(location).material);
						location.getBlock().setData((byte) BLOCKDATA.get(location).data);
						BLOCKDATA.remove(location);
						REMOVE.add(location);
					}
										
				}
				
				BLOCKS.removeAll(REMOVE);
				REMOVE.clear();
				
			}
			
		}, 20, 20);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void unload() {
		
		BLOCKS.addAll(ADD);
		ADD.clear();
		
		for(Location location : BLOCKS) {
			location.getBlock().setType(BLOCKDATA.get(location).material);
			location.getBlock().setData((byte) BLOCKDATA.get(location).data);
			BLOCKDATA.remove(location);
			REMOVE.add(location);					
		}
		
		BLOCKS.removeAll(REMOVE);
		REMOVE.clear();
		
	}
	
	@SuppressWarnings("deprecation")
	public void setOreTimer(Location location, int cooldown) {
		ADD.add(location);
		BLOCKDATA.put(location, new BlockData(location.getBlock().getType(), location.getBlock().getData(), cooldown));
		location.getBlock().setType(Material.GRAVEL);
	}
	
	public boolean registerBlockDrop(BlockBreakEvent event, ToolType tool, ToolMaterial toolMaterial, Material material, ItemStack stack, float chance, int min, int max) {
		
		Player player = event.getPlayer();
		ItemStack toolStack = player.getInventory().getItemInMainHand();
		float f = MathHandler.RANDOM.nextFloat();
		
		if(event.getBlock().getType() == material && toolStack.getType().toString().contains(tool.name()) && toolMaterial.isValid(player.getInventory().getItemInMainHand()) && f <= chance) {
			int amount = MathHandler.RANDOM.nextInt(max + 1 - min) + min;
			for(int i = 0; i < amount; i++) ItemHandler.addItem(player, stack);
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 0.5F, 2.0F);
			return true;
		}
		
		return false;
				
	}
	
	public void registerBlockReturn(BlockBreakEvent event, int minCooldown, int maxCooldown, int minXp, int maxXp) {
		setOreTimer(event.getBlock().getLocation(), MathHandler.RANDOM.nextInt(maxCooldown + 1 - minCooldown) + minCooldown);
		int experience = MathHandler.RANDOM.nextInt(maxXp + 1 - minXp) + minXp;
		Experience.add(event.getPlayer(), experience);
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
				
		if(registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.WOOD, Material.COAL_ORE, GameRegistry.stone_fragment, 1.0F, 1, 2)) {
			registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.WOOD, Material.COAL_ORE, GameRegistry.coal_fragment, 0.4F, 1, 2);
			registerBlockReturn(event, 30, 60, 1, 2);
		} else if(registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.STONE, Material.IRON_ORE, GameRegistry.stone_fragment, 1.0F, 1, 4)) {
			registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.STONE, Material.IRON_ORE, GameRegistry.iron_fragment, 0.4F, 1, 2);
			registerBlockReturn(event, 60, 120, 2, 4);
		} else if(registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.IRON, Material.GOLD_ORE, GameRegistry.stone_fragment, 1.0F, 2, 8)) {
			registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.IRON, Material.GOLD_ORE, GameRegistry.gold_fragment, 0.2F, 1, 1);
			registerBlockReturn(event, 120, 240, 4, 8);
		} else if(registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.IRON, Material.DIAMOND_ORE, GameRegistry.stone_fragment, 1.0F, 2, 8)) {
			registerBlockDrop(event, ToolType.PICKAXE, ToolMaterial.IRON, Material.DIAMOND_ORE, GameRegistry.crystal_fragment, 0.1F, 1, 1);
			registerBlockReturn(event, 240, 480, 8, 16);
		} else {
			if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);	
		}
		
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);	
	}
	
}
