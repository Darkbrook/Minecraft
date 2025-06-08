package dev.darkbrook.wildfire.mixin.fullbright;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.client.option.WildfireOptions;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.math.MathHelper;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin
{
	@ModifyVariable(method = "updateLightmap", at = @At(value = "STORE"), ordinal = 3)
	private float overrideSkyLight(float skyLight)
	{
		WildfireOptions options = Wildfire.getInstance().getOptions();
		return options.useSkyLightFullbright() 
			? MathHelper.clamp(skyLight, options.getMinSkyLight(), options.getMaxSkyLight()) 
			: skyLight;
	}
}
