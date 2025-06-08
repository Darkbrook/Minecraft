package com.darkbrook.island;

import com.darkbrook.island.brewery.Brewery;
import com.darkbrook.island.common.gameplay.event.UpdateEventHandler;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.vanilla.claim.ClaimHandler;
import com.darkbrook.island.vanilla.claim.command.ClaimAddCommand;
import com.darkbrook.island.vanilla.claim.command.ClaimCommand;
import com.darkbrook.island.vanilla.claim.command.ClaimRemoveCommand;
import com.darkbrook.island.vanilla.claim.command.UnclaimCommand;
import com.darkbrook.island.vanilla.command.DebugCommand;
import com.darkbrook.island.vanilla.command.PlaytimeCommand;
import com.darkbrook.island.vanilla.command.WorldCommand;
import com.darkbrook.island.vanilla.handler.BookHandler;
import com.darkbrook.island.vanilla.handler.ExplosionHandler;
import com.darkbrook.island.vanilla.handler.ItemDespawnHandler;
import com.darkbrook.island.vanilla.handler.NetherHandler;
import com.darkbrook.island.vanilla.handler.PlayerHandler;
import com.darkbrook.island.vanilla.handler.SpawnHandler;
import com.darkbrook.island.vanilla.playerdata.PlayerData;
import com.darkbrook.island.vanilla.recipe.ZombieHideCookingRecipe;

public class DarkbrookIsland extends RegistryPlugin
{
	
	@Override
	public void onEnable(RegistryPlugin plugin) 
	{		
		plugin.initialize(new UpdateEventHandler());
		plugin.initialize(new PlayerData());
		plugin.initialize(new Brewery());
		plugin.initialize(new ClaimCommand(), new ClaimAddCommand(), new ClaimRemoveCommand(), new DebugCommand(), new PlaytimeCommand(), new UnclaimCommand(), new WorldCommand());
		plugin.initialize(new BookHandler(), new ClaimHandler(), new ExplosionHandler(), new ItemDespawnHandler(), new NetherHandler(), new PlayerHandler(), new SpawnHandler());
		plugin.initialize(new ZombieHideCookingRecipe());
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
	}

}
