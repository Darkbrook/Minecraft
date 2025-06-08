package dev.darkbrook.wildfire.mixin.title;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.client.option.WildfireOptions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;

@Mixin(GuiIngameForge.class)
public abstract class GuiIngameForgeMixin
{
	@Redirect(method = "renderTitle", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void translateTitle(float x, float y, float z)
    {
		WildfireOptions options = Wildfire.getInstance().getOptions();
		if (options.getTitlePosition() == WildfireOptions.TitlePosition.TOP)
			y = calculateTitleOffset() * options.getTitleScale();
		GlStateManager.translate(x, y, z);
    }

	@Redirect(method = "renderTitle", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
	private void scaleTitle(float x, float y, float z)
	{
		float titleScale = Wildfire.getInstance().getOptions().getTitleScale();
		GlStateManager.scale(x * titleScale, y * titleScale, z * titleScale);
	}

	@Unique
	private int calculateTitleOffset()
	{
		GuiIngameForge gui = (GuiIngameForge) (Object) this;
		int bossBars = ((GuiBossOverlayAccessor) gui.getBossOverlay())
				.getMapBossInfos().size();
		if (bossBars == 0)
			return 64;
		// Up to 6 boss bars are visible on screen at once
		return Math.min(bossBars, 6) * (gui.getFontRenderer().FONT_HEIGHT + 10) * 2 + 60;		
	}
}
