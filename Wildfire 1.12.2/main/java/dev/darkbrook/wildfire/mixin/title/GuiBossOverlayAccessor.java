package dev.darkbrook.wildfire.mixin.title;

import java.util.Map;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.GuiBossOverlay;

@Mixin(GuiBossOverlay.class)
public interface GuiBossOverlayAccessor
{
	@Accessor
	Map<UUID, BossInfoClient> getMapBossInfos();
}
