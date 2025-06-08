package dev.darkbrook.wildfire.mixin.effect;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin
{
	@Inject(method = "renderPotionEffects", at = @At("HEAD"), cancellable = true)
	private void hidePotionEffects(ScaledResolution resolution, CallbackInfo callback)
	{
		if (Wildfire.getInstance().getOptions().hideHudStatusEffects())
			callback.cancel();
	}
}
