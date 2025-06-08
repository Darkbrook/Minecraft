package dev.darkbrook.wildfire;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.darkbrook.wildfire.client.command.CommandStack;
import dev.darkbrook.wildfire.client.commandblock.CommandBlockHandler;
import dev.darkbrook.wildfire.client.option.MaxStackSizeConfig;
import dev.darkbrook.wildfire.client.option.WildfireOptions;
import dev.darkbrook.wildfire.client.option.WildfireOptionsGuiFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Wildfire.MODID, clientSideOnly = true, guiFactory = WildfireOptionsGuiFactory.CANONICAL_NAME)
public final class Wildfire
{
    public static final String MODID = "wildfire";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    
    @Mod.Instance
    private static Wildfire instance;
    
    private WildfireOptions options;
    private MaxStackSizeConfig maxStackSizeConfig;
    
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		Path configDir = event.getModConfigurationDirectory().toPath();
		options = new WildfireOptions(configDir.resolve(MODID + ".cfg"));
		maxStackSizeConfig = new MaxStackSizeConfig(configDir.resolve(MODID).resolve("max_stack_sizes.json"));
		maxStackSizeConfig.load();
		new CommandStack();
		new CommandBlockHandler();
	}
	
	public WildfireOptions getOptions()
	{
		return options;
	}
	
	public MaxStackSizeConfig getMaxStackSizeConfig()
	{
		return maxStackSizeConfig;
	}
	
	public static Wildfire getInstance()
	{
		return instance;
	}
}
