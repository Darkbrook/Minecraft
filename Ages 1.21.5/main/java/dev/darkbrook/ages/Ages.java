package dev.darkbrook.ages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.darkbrook.ages.common.AgeProgressionManager;
import dev.darkbrook.ages.common.AgeRestrictionHandler;
import dev.darkbrook.ages.server.command.AgeCommand;
import dev.darkbrook.ages.server.command.AgesCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Ages implements ModInitializer
{
	public static final String MOD_ID = "ages";
	public static final Logger LOGGER = LoggerFactory.getLogger("Ages");
	
	private static Ages instance;
	
	private AgeProgressionManager manager;
	private AgeRestrictionHandler handler;
	
	@Override
	public void onInitialize()
	{
		instance = this;
		manager = new AgeProgressionManager();
		manager.load();
		handler = new AgeRestrictionHandler();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environtment) -> {
			AgeCommand.register(dispatcher);
			AgesCommand.register(dispatcher);
		});
	}
	
	public AgeProgressionManager getProgressionManager()
	{
		return manager;
	}
	
	public AgeRestrictionHandler getRestrictionHandler()
	{
		return handler;
	}
	
	public static Ages getInstance()
	{
		return instance;
	}
}