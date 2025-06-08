package dev.darkbrook.wildfire.mixin.label;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin
{
	@Redirect(method = "drawNameplate", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
	private static void scaleNameplate(float x, float y, float z)
	{
		float labelScale = Wildfire.getInstance().getOptions().getLabelScale();
        GlStateManager.scale(x * labelScale, y * labelScale, z * labelScale);
	}
}
