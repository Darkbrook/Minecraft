package com.darkbrook.kingdoms.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

@Mixin(ArmorStandEntity.class)
abstract class ArmorStandEntityMixin
{
	@Unique
	private boolean interaction = true;
	
	@Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
	private void interactAt(PlayerEntity player, Vec3d hitPos, Hand hand, CallbackInfoReturnable<ActionResult> callback)
	{
		if (!interaction)
			callback.setReturnValue(ActionResult.FAIL);
	}
	
	public boolean isAttackable()
	{
		return interaction;
	}
	
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callback)
	{
		interaction = !nbt.getBoolean("NoInteraction");
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callback)
	{
		if (!interaction)
			nbt.putBoolean("NoInteraction", true);
	}
}
