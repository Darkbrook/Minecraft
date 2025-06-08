package dev.darkbrook.wildfire.mixin.scoreboard;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin
{
	@ModifyVariable(method = "renderScoreboard", at = @At(value = "STORE", ordinal = 0))
	private String removeScoreboardPointsFromEntry(String entry)
	{
		return Wildfire.getInstance().getOptions().hideScoreboardPoints()
				? entry.substring(0, entry.lastIndexOf(":"))
				: entry;
	}
	
	@Redirect(method = "renderScoreboard", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I",
			ordinal = 1))
	private int hideScoreboardPoints(FontRenderer fontRenderer, String text, int x, int y, int color)
	{
		return Wildfire.getInstance().getOptions().hideScoreboardPoints()
				? 0
				: fontRenderer.drawString(text, x, y, color);
	}
}
