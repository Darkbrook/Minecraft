package com.darkbrook.island.mmo.combat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.library.misc.PacketHandler;
import com.darkbrook.island.mmo.entity.EntityData;

import net.md_5.bungee.api.ChatColor;

public class InstanceBattle extends InstanceBattleBase {
	
	public static final List<InstanceBattleBase> INSTANCE_BATTLES = new ArrayList<InstanceBattleBase>();
	
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new InstanceBattle(), plugin);
	}
	
	private void addInstanceBattle() {
		try {
			while(!INSTANCE_BATTLES.contains(this)) INSTANCE_BATTLES.add(this);
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("@AddRequest - Instance Battle List in use, re-queueing.");
		}
	}
	
	private void removeInstanceBattle() {
		try {
			while(INSTANCE_BATTLES.contains(this)) INSTANCE_BATTLES.remove(this);
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("@RemoveRequest - Instance Battle List in use, re-queueing.");
		}
	}
	
	public InstanceBattle() {
		super(4);
	}

	public void addLocalPlayer(LivingEntity entity, int distance, int team) {
		for(Player player : entity.getWorld().getPlayers()) if(player.getLocation().distance(entity.getLocation()) <= distance) addEntity(player, 0, 0, 0, 0, team);
	}
	
	public void addLocalEntities(LivingEntity entity, int distance, int team) {
		for(Entity entities : entity.getWorld().getEntities()) if(entities != entity && !(entities instanceof Player) && entities instanceof LivingEntity && entities.getLocation().distance(entity.getLocation()) <= distance) addEntity((LivingEntity) entities, 0, 0, 0, 0, team);
	}
	
	@Override
	protected void initiative() {
		addInstanceBattle();
		setActionMessage("Roll Initiative");
	}

	@Override
	protected void updateturn() {
		if(contestant != null) {
			contestant.position = contestant.entity.getLocation();
			contestantLast = contestant;
		}
		position = contestant == null || position >= (contestants.size() - 1) ? 0 : position + 1;
		contestant = contestants.get(position);
		if(contestant == contestantLast) {
			updateturn();
			return;
		}
		contestant.hits = 4;
		contestant.entity.getWorld().playSound(contestant.entity.getLocation(), Sound.BLOCK_LAVA_POP, 1.0F, 0.0F);
		contestant.entity.getWorld().spigot().playEffect(contestant.entity.getLocation(), Effect.LAVA_POP, 0, 0, 0.2F, 0.8F, 0.2F, 0, 100, 100);
	}
	
	@Override
	protected void battle() {
		setActionMessage("It's " + contestant.name + "s turn. (" + time + "s)");
	}

	@Override
	protected void endbattle() {
		setActionMessage("The Battle Has Ended");
		sendChatMessage("");
		sendChatMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------==========<Winners>==========----------");
		for(EntityData ed : contestants) {
			sendChatMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Name: " + ed.name);
			ed.entity.getWorld().playSound(ed.entity.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.0F);
			if(ed.entity instanceof Player) PacketHandler.sendTitleMessage((Player) ed.entity, messagePrefix + "Battle Won");
		}
		sendChatMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------==========<Winners>==========----------");
		sendChatMessage("");
		removeInstanceBattle();
	}

}
