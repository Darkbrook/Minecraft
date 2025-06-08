package com.darkbrook.island.mmo.entity;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.References;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.combat.InstanceBattle;
import com.darkbrook.island.mmo.combat.InstanceBattleBase;

public class EntityHook implements Listener {
	
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new EntityHook(), plugin);
	}
	
	private ItemStack getSkull(ItemStack[] contents) {
		ItemStack skull = GameRegistry.chestskull.clone();
		skull = ItemHandler.setLoggedContents(skull, contents, References.lootdata);
		return skull;
	}
	
	private ItemStack[] getPreferedContents(LivingEntity entity) {
		
		double leather = 0.0D;
		double iron = 0.0D;
		double scale = 1.0D;

		final double leatherMin = 0.50D;
		final double chainMin = 0.15D;
		final double ironMin = 0.25D;
		
		final float leatherDropChance = 0.08F;
		final float chainDropChance = 0.02F;
		final float ironDropChance = 0.005F;

		switch(entity.getType().name()) {
			case "GIANT": scale = 3.0D; break;
			default: scale = 1.0D; break;
		}
		
		if(entity.getEquipment().getHelmet() != null) {
		
			switch(entity.getEquipment().getHelmet().getType().name().toUpperCase()) {
			
				case "LEATHER_HELMET": 
					if(MathHandler.RANDOM.nextFloat() > leatherDropChance) leather += leatherMin; else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getHelmet()); 
				break;
				
				case "CHAINMAIL_HELMET": 
					if(MathHandler.RANDOM.nextFloat() > chainDropChance) iron += chainMin; else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getHelmet()); 
				break;
				
				case "IRON_HELMET": 
					if(MathHandler.RANDOM.nextFloat() > ironDropChance) iron += ironMin; else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getHelmet());
				break;
				
			}
		
		}		
		
		if(entity.getEquipment().getChestplate() != null) {
			
			switch(entity.getEquipment().getChestplate().getType().name().toUpperCase()) {
			
				case "LEATHER_CHESTPLATE": 
					if(MathHandler.RANDOM.nextFloat() > leatherDropChance) leather += (leatherMin * 6); else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getChestplate()); 
				break;
				
				case "CHAINMAIL_CHESTPLATE": 
					if(MathHandler.RANDOM.nextFloat() > chainDropChance) iron += (chainMin * 6); else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getChestplate()); 
				break;
				
				case "IRON_CHESTPLATE": 
					if(MathHandler.RANDOM.nextFloat() > ironDropChance) iron += (ironMin * 6); else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getChestplate()); 
				break;
				
			}
		
		}
		
		if(entity.getEquipment().getLeggings() != null) {
			
			switch(entity.getEquipment().getLeggings().getType().name().toUpperCase()) {
			
				case "LEATHER_LEGGINGS": 
					if(MathHandler.RANDOM.nextFloat() > leatherDropChance) leather += (leatherMin * 3); else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getLeggings());
				break;
				
				case "CHAINMAIL_LEGGINGS": 
					if(MathHandler.RANDOM.nextFloat() > chainDropChance) iron += (chainMin * 3); else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getLeggings());
				break;
				
				case "IRON_LEGGINGS": 
					if(MathHandler.RANDOM.nextFloat() > ironDropChance) iron += (ironMin * 3); else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getLeggings());
				break;
				
			}
		
		}
		
		if(entity.getEquipment().getBoots() != null) {
			switch(entity.getEquipment().getBoots().getType().name().toUpperCase()) {
			
				case "LEATHER_BOOTS": 
					if(MathHandler.RANDOM.nextFloat() > leatherDropChance) leather += leatherMin; else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getBoots());
				break;
				
				case "CHAINMAIL_BOOTS": 
					if(MathHandler.RANDOM.nextFloat() > leatherDropChance) iron += chainMin; else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getBoots());
				break;
				
				case "IRON_BOOTS": 
					if(MathHandler.RANDOM.nextFloat() > leatherDropChance) iron += ironMin; else entity.getWorld().dropItemNaturally(entity.getLocation(), entity.getEquipment().getBoots());
				break;
				
			}
		
		}
		
		leather *= scale;
		iron *= scale;
				
		
		ItemStack leatherstack = GameRegistry.hide_strip.clone();
		leatherstack.setAmount((int) Math.round(leather));
				
		ItemStack ironstack = GameRegistry.iron_fragment.clone();
		ironstack.setAmount((int) Math.round(iron));
		
		ItemStack[] contents = {leather > 0 ? leatherstack: null, iron > 0 ? ironstack : null};
		return contents[0] == null && contents[1] == null ? null : contents;
	
	}
	
	public void getLoot(ItemStack stack, Player player) {
		ItemStack[] contents = ItemHandler.getLoggedContents(stack, 2, References.lootdata);
		if(contents == null) return;
		for(ItemStack s : contents) if(s != null) ItemHandler.addItem(player, s);
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		if(!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity) {
			ItemStack[] contents = getPreferedContents((LivingEntity) event.getEntity());
			if(contents != null) event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), getSkull(contents));
		}
		
		if(event.getEntity() instanceof Slime) event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), MathHandler.RANDOM.nextFloat() > 0.01F ? GameRegistry.slime_core : GameRegistry.enchanted_slime_core);		
			
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
	
		if(event.getClickedInventory() != null && event.getClickedInventory() instanceof PlayerInventory) {
						
			if(event.getClick() == ClickType.RIGHT && event.getCurrentItem() != null && ItemHandler.compareDisplayNames(event.getCurrentItem(), GameRegistry.chestskull)) {
				event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 2.0F, 2.0F);
				getLoot(event.getCurrentItem(), (Player) event.getWhoClicked());
				event.setCurrentItem(null);
				event.setCancelled(true);
			}
			
		}
		
	}
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		
		if(event.getEntity() instanceof Skeleton) {
			Skeleton skeleton = (Skeleton) event.getEntity();
			skeleton.getEquipment().setHelmet(GameRegistry.MENU_ARMOR_VALUES.get(4).getActual());
			skeleton.getEquipment().setChestplate(GameRegistry.MENU_ARMOR_VALUES.get(5).getActual());
			skeleton.getEquipment().setLeggings(GameRegistry.MENU_ARMOR_VALUES.get(6).getActual());
			skeleton.getEquipment().setBoots(GameRegistry.MENU_ARMOR_VALUES.get(7).getActual());
			skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.WOOD_SWORD));
			skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1, false, false));
		} 
		
	}
	
	@EventHandler
	public void onSlimeSplit(SlimeSplitEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
				
		boolean isPlayerDamager = event.getDamager() instanceof Player;
		boolean isPlayerEntity = event.getEntity() instanceof Player;
				
		if(isPlayerDamager && !isPlayerEntity) {
			
			Player player = (Player) event.getDamager();
			if(!InstanceBattleBase.hasInstanceBattle(player) && player.getGameMode() == GameMode.SURVIVAL) {
				InstanceBattle ib = new InstanceBattle();
				ib.addLocalPlayer(player, 64, 0);
				ib.addLocalEntities(player, 64, 1);
				ib.init();
				event.setCancelled(true);
			}
			
		} else if(!isPlayerDamager && isPlayerEntity) {
			
			Player player = (Player) event.getEntity();
			if(!InstanceBattleBase.hasInstanceBattle(player) && player.getGameMode() == GameMode.SURVIVAL) {
				InstanceBattle ib = new InstanceBattle();
				ib.addLocalPlayer(player, 64, 0);
				ib.addLocalEntities(player, 64, 1);
				ib.init();
				event.setCancelled(true);
			}
			
		}

	}
	
}
