package com.darkbrook.island.mmo.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.library.misc.PacketHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.entity.EntityData;
import com.darkbrook.island.mmo.item.DamageDie;

import net.md_5.bungee.api.ChatColor;

public abstract class InstanceBattleBase implements Listener {
	
	protected static final HashMap<LivingEntity, InstanceBattleBase> INSTANCE_CONTESTANTS = new HashMap<LivingEntity, InstanceBattleBase>();
	protected List<EntityData> contestants = new ArrayList<EntityData>();
	public List<EntityData> contestantsPlayer = new ArrayList<EntityData>();
	protected List<EntityData> contestantsEntity = new ArrayList<EntityData>();
	protected List<EntityData> contestantsMarkedRemove = new ArrayList<EntityData>();
	protected HashMap<UUID, EntityData> contestantsUUID = new HashMap<UUID, EntityData>();
	private HashMap<String, Object> field = new HashMap<String, Object>();
	public EntityData contestant;
	protected EntityData contestantLast;
	protected String message;
	protected String messagePrefix = ChatColor.GRAY + "" + ChatColor.BOLD;
	protected int time;
	protected int turnTime;
	protected int position;
	protected boolean isInit;
	protected boolean isBattle;
	
	public static boolean hasInstanceBattle(LivingEntity entity) {
		return INSTANCE_CONTESTANTS.containsKey(entity);
	}
	
	public InstanceBattleBase(int turnTime) {
		this.turnTime = turnTime;
	}
	
	protected Object getField(String key) {
		return field.get(key);
	}
	
	protected void setField(String key, Object value) {
		field.put(key, value);
	}
	
	protected boolean hasField(String key) {
		return field.containsKey(key);
	}
	
	public void addEntity(LivingEntity entity, int maxhealth, int health, int defence, int damage, int team) {
		
		if(!hasInstanceBattle(entity)) {
			if(entity instanceof Player && ((Player) entity).getGameMode() != GameMode.SURVIVAL) return;

			EntityData ed = new EntityData(entity, entity.getName(), maxhealth, health, defence, damage, team);
			contestants.add(ed);
			contestantsUUID.put(entity.getUniqueId(), ed);
			if(entity instanceof Player) {
				contestantsPlayer.add(ed); 
				ed.entity.getWorld().playSound(ed.entity.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.0F);
				PacketHandler.sendTitleMessage((Player) ed.entity, messagePrefix + "Battle Started");
			} else {
				contestantsEntity.add(ed);
			}
			INSTANCE_CONTESTANTS.put(entity, this);
		}
		
	}
	
	public void removeEntity(UUID uuid) {
		contestantsMarkedRemove.add(getEntityData(uuid));
	}
	
	public EntityData getEntityData(UUID uuid) {
		return contestantsUUID.containsKey(uuid) ? contestantsUUID.get(uuid) : null;
	}
	
	public LivingEntity getEntity(UUID uuid) {
		return getEntityData(uuid) != null ? getEntityData(uuid).entity : null;
	}
	
	public void sendChatMessage(String message) {
		for(EntityData ed : contestantsPlayer) ((Player) ed.entity).sendMessage(message);
	}
	
	private void sendActionMessage(String message) {
		for(EntityData ed : contestantsPlayer) PacketHandler.sendActionMessage(((Player) ed.entity), messagePrefix + message);
	}
	
	public void setActionMessage(String message) {
		this.message = message;
	}
	
	private void sortTurns() {
		
		contestant = null;
		
		List<EntityData> contestantsSorted = new ArrayList<EntityData>();
		List<HashMap<Integer, EntityData>> rollsSorted = new ArrayList<HashMap<Integer, EntityData>>();
		List<Integer> rollsSortedAmount = new ArrayList<Integer>();
		
		for(int i = 0; i < 20; i++) {
			rollsSorted.add(new HashMap<Integer, EntityData>());
			rollsSortedAmount.add(0);
		}
		
		for(EntityData ed : contestants) {
			int amount = rollsSortedAmount.get(ed.lastroll - 1);
			rollsSorted.get(ed.lastroll - 1).put(amount, ed);
			rollsSortedAmount.set(ed.lastroll - 1, amount + 1);
		}
		
		int position = 0;

		sendChatMessage("");
		sendChatMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------==========<Initiative Order>==========----------");
		
		for(int i = 19; i >= 0; i--) {
			
			int amount = rollsSortedAmount.get(i);
			
			if(amount > 0) {
			
				for(int j = 0; j < amount; j++) {
					position++;
					EntityData ed = rollsSorted.get(i).get(j);
					sendChatMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Position: " + position + ", Name: " + ed.name + ", Initiative Roll: " + ed.lastroll + ".");
					contestantsSorted.add(ed);
					ed.lastroll = 0;
					if(ed.entity instanceof Player) DamageDie.clearRoll((Player) ed.entity);
				}
			
			}
			
		}
		
		sendChatMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------==========<Initiative Order>==========----------");
		sendChatMessage("");

		contestants = contestantsSorted;		
		
	}
	
	private void initiativeLoop() {
		
		setField("INITCURRENT", 0);
		setField("INITBREAK", 0);
		
		int i = UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				int initBreak = (int) getField("INITBREAK");
				setField("INITBREAK", initBreak > 1 ? initBreak - 1 : 0);
				
				if((int) getField("INITBREAK") == 0) {
					
					int initCurrent = (int) getField("INITCURRENT");
					
					if(initCurrent >= contestants.size()) {
						UpdateHandler.cancle((int) getField("INITLOOP"));
						sortTurns();
						isInit = true;
					} else {
						contestant = contestants.get(initCurrent);
						if(contestant.updateRoll()) {
							setActionMessage(contestant.name + " rolled a " + contestant.lastroll + ".");
							sendChatMessage(messagePrefix + contestant.name + " rolled a " + contestant.lastroll + ".");
							initCurrent++;
							setField("INITBREAK", 4);
						} else {
							contestant.canRoll();
							setActionMessage(contestant.name + " is rolling.");
						}
						setField("INITCURRENT", initCurrent);
					}
					
				}
				
			}
		
		}, 20, 20);
		
		setField("INITLOOP", i);
		
	}
	
	private boolean hasWinningTeam() {
		int team = -1;
		for(EntityData ed : contestants) {
			if(team == -1) team = ed.team; else if(team != ed.team) return false;
		}
		return true;
	}
		
 	public void init() {
		 		
		isInit = false;
		isBattle = false;
		initiative();
		initiativeLoop();
		
		int i = UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				sendActionMessage(message);
				onEntityMove();
								
				if(isInit) {

					if(hasWinningTeam()) isBattle = true;
										
					if(isBattle) {
						endbattle();
						sleep();
					} else {
						time = time > 1 ? time - 1 : turnTime;
						if(time == turnTime) updateturn();
						battle();
					}
					
				}
				
				try {
				
					for(EntityData ed : contestantsMarkedRemove) {
						contestantsUUID.remove(ed.entity.getUniqueId());
						contestants.remove(ed);
						if(ed.entity instanceof Player) contestantsPlayer.remove(ed); else contestantsEntity.remove(ed);
						INSTANCE_CONTESTANTS.remove(ed.entity);
					}
				
					contestantsMarkedRemove.clear();
				
				} catch (Exception e) {
					System.out.println("@RemoveRequest - Instance Battle List in use, re-removing.");
				}
				
			}
			
		}, 0, 20);
		
		setField("MAINLOOP", i);

	}
	
	public void sleep() {
		
		if(hasField("MAINLOOP")) UpdateHandler.cancle((int) getField("MAINLOOP")); 
		if(hasField("MUSICLOOP")) UpdateHandler.cancle((int) getField("MUSICLOOP")); 
		
		for(EntityData ed : contestants) contestantsMarkedRemove.add(ed);
		
		for(EntityData ed : contestantsMarkedRemove) {
			contestantsUUID.remove(ed.entity.getUniqueId());
			contestants.remove(ed);
			if(ed.entity instanceof Player) contestantsPlayer.remove(ed); else contestantsEntity.remove(ed);
			INSTANCE_CONTESTANTS.remove(ed.entity);
		}
		
		contestantsMarkedRemove.clear();
		
	}
	
	protected abstract void initiative();
	protected abstract void updateturn();
	protected abstract void battle();
	protected abstract void endbattle();
	
	private void slowEntity(LivingEntity entity) {
		entity.removePotionEffect(PotionEffectType.SLOW);
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 24, 100, false, false));
	}
	
	private void onEntityMove() {
		for(EntityData ed : contestantsEntity) if(contestant == null || !isInit || ed != contestant || ed.entity.getLocation().distance(ed.position) > ed.movement) slowEntity(ed.entity);		
	}
	
	private boolean moved(Location from, Location to) {
		return from.getX() != to.getX() || from.getZ() != to.getZ();
	}
	
	private void onPlayerMoveMask(PlayerMoveEvent event) {
		LivingEntity entity = getEntity(event.getPlayer().getUniqueId());
		if(contestant != null && entity != null && moved(event.getFrom(), event.getTo())) if(!isInit || entity != contestant.entity || event.getTo().distance(contestant.position) > contestant.movement) event.setCancelled(true);		
	}
	
	private void onEntityDamageEntityMask(EntityDamageByEntityEvent event) {
		
		LivingEntity damager = getEntity(event.getDamager().getUniqueId());
		LivingEntity entity = getEntity(event.getEntity().getUniqueId());

		if(contestant != null && damager != null) {
			
			if(isInit && damager == contestant.entity && contestant.hits > 0) {
				
				contestant.hits--;
				sendChatMessage(messagePrefix + contestant.name + " has " + (contestant.hits == 0 ? "no more hits left." : (contestant.hits == 1 ? "1 more hit left." : contestant.hits + " more hits left.")));
				event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0F, 2.0F);
				
				if(entity != null && !(entity instanceof Player)) {
					Location position = getEntityData(entity.getUniqueId()).position;
					entity.teleport(new Location(position.getWorld(), position.getX(), position.getY(), position.getZ(), entity.getLocation().getYaw(), entity.getLocation().getPitch()));
				}
				
				if(!(damager instanceof Player)) {
					damager.setVelocity(damager.getLocation().getDirection().multiply(-1));
				}
				
			} else {
				event.setCancelled(true);
			}
			
		}
		
	}
	
	private void onEntityDeathMask(EntityDeathEvent event) {
		LivingEntity entity = getEntity(event.getEntity().getUniqueId());
		if(entity != null) removeEntity(entity.getUniqueId());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		for(InstanceBattleBase ibb : InstanceBattle.INSTANCE_BATTLES) ibb.onPlayerMoveMask(event);
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		for(InstanceBattleBase ibb : InstanceBattle.INSTANCE_BATTLES) ibb.onEntityDamageEntityMask(event);
	}
	
 	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		for(InstanceBattleBase ibb : InstanceBattle.INSTANCE_BATTLES) ibb.onEntityDeathMask(event);
	}
	
}
