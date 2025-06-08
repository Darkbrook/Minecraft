package dev.darkbrook.wildfire.mixin.commandblock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;

@Mixin(AbstractClientPlayer.class)
public interface AbstractClientPlayerInvoker
{
	@Invoker
	NetworkPlayerInfo invokeGetPlayerInfo();	
}
