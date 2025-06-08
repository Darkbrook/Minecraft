package dev.darkbrook.ages.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.darkbrook.ages.Ages;
import dev.darkbrook.ages.common.Action;
import dev.darkbrook.ages.common.AgeRestrictionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(targets = "net.minecraft.screen.slot.ArmorSlot")
public abstract class ArmorSlotMixin
{
	@Unique
	private static final AgeRestrictionHandler HANDLER = Ages.getInstance().getRestrictionHandler();

	@Shadow @Final
	private LivingEntity entity;
	
	@Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
	private void canInsert(ItemStack stack, CallbackInfoReturnable<Boolean> callback)
	{
		if (entity instanceof PlayerEntity player 
				&& HANDLER.handleAgeRestriction(Action.USE, player, stack.getItem()))
			callback.setReturnValue(false);
	}
}
