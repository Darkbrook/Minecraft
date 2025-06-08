package dev.darkbrook.wildfire.client.command;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.common.command.ClientCommandBase;
import dev.darkbrook.wildfire.common.mixin.StackSizeOverride;
import dev.darkbrook.wildfire.common.util.SimpleResourceLocation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandStack extends ClientCommandBase
{
	private static final Wildfire WILDFIRE = Wildfire.getInstance();
	
    @Override
	public String getName()
	{
		return "stack";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/stack <item> <size>";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
        if (args.length != 2)
            throw new WrongUsageException(getUsage(sender));
        
    	Item item = CommandBase.getItemByText(sender, args[0]);
    	int size = CommandBase.parseInt(args[1], 1, 64);
    	
    	if (((StackSizeOverride) item).setCustomMaxStackSize(size))
    		WILDFIRE.getMaxStackSizeConfig().save();
    	
    	sender.sendMessage(new TextComponentString("Set max stack size for " + SimpleResourceLocation.of(item) + " to " + size));
	}
	
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
    	return args.length == 1 
    		? CommandBase.getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()
				.stream()
				.map(SimpleResourceLocation::of)
				.collect(Collectors.toList()))
    		: Collections.emptyList();
    }
}
