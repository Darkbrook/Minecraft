package dev.darkbrook.wildfire.mixin.label;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.client.renderer.entity.Render;

@Mixin(Render.class)
public abstract class RenderMixin
{
	@ModifyVariable(method = "renderLivingLabel", at = @At("STORE"), ordinal = 2)
	private float getLabelHeight(float height)
	{
		return height - (1.0f - Wildfire.getInstance().getOptions().getLabelScale()) / 4.0f;
	}
}
