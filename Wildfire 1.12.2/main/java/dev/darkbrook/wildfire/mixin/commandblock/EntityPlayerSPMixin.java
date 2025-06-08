package dev.darkbrook.wildfire.mixin.commandblock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.client.commandblock.GuiWildfireCommandBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntityCommandBlock;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin
{
	@Shadow
	private Minecraft mc;

	@Inject(method = "displayGuiCommandBlock", at = @At("HEAD"), cancellable = true)
	public void displayGuiWildfireCommandBlock(TileEntityCommandBlock block, CallbackInfo callback)
	{
		if (!Wildfire.getInstance().getOptions().customInterface())
			return;

		mc.displayGuiScreen(new GuiWildfireCommandBlock(block));
		callback.cancel();
	}
}
