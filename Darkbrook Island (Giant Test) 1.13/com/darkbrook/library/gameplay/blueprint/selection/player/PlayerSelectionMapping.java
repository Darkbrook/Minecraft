package com.darkbrook.library.gameplay.blueprint.selection.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.darkbrook.library.chat.message.ChatMessageInfo;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.helper.HashMapHelper;

public class PlayerSelectionMapping implements Listener, IRegistryValue
{

	private static final Map<Player, PlayerSelection> selectionMapping = new HashMap<Player, PlayerSelection>();

	public static PlayerSelection getPlayerSelection(Player player)
	{
		return HashMapHelper.get(selectionMapping, player, new PlayerSelection(player));
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		
		Player player = event.getPlayer();
		int state;
		
		if((state = getConditionState(player, event.getHand(), event.getAction())) != -1)
		{
			PlayerSelection selection = getPlayerSelection(player);
			Location location = event.getClickedBlock().getLocation();

			selection.setPosition(location, state);

			ChatMessageInfo message = new ChatMessageInfo(new String[] 
			{
				"Position ", String.valueOf(state), 
				" set to " , String.valueOf(location.getBlockX()), 
				", "       , String.valueOf(location.getBlockY()), 
				", "       , String.valueOf(location.getBlockZ()),
				"."
			});
			
			message.sendMessage(player);
			event.setCancelled(true);
		}
		
	}
	
	public int getConditionState(Player player, EquipmentSlot slot, Action action)
	{
		boolean hasPreCondition = player.isOp() && player.getGameMode() == GameMode.CREATIVE && slot == EquipmentSlot.HAND && player.getInventory().getItemInMainHand().getType() == Material.SLIME_BALL;
		int state = -1;
		
		if(hasPreCondition) switch(action)
		{
			case RIGHT_CLICK_BLOCK: state++;
			case LEFT_CLICK_BLOCK: state++;
			default: break;
		}
		
		return state;
	}
	
}
