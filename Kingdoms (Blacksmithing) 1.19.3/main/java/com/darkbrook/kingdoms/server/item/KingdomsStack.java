package com.darkbrook.kingdoms.server.item;

import java.util.Optional;

import com.darkbrook.kingdoms.common.item.ItemStackConvertable;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public interface KingdomsStack extends ItemStackConvertable
{
	ItemStack asStack();
	
	Optional<KingdomsItem> getKingdomsItem();	
	
	float getSmithingProgress();
	
	void setSmithingProgress(float progress);
		
	int getTemperature(World world);
	
	void setTemperature(World world, int temperature);
	
	boolean isBroken();
	
	static void registerEvents()
	{
		ServerTickEvents.START_WORLD_TICK.register(world -> 
		{
			for (ServerPlayerEntity player : world.getPlayers())
				for (Slot slot : player.currentScreenHandler.slots)
					KingdomsStack.of(slot.getStack()).getTemperature(world);
		});
	}
	
	static KingdomsStack of(ItemStack stack)
	{
		return (KingdomsStack) (Object) stack;
	}
}
