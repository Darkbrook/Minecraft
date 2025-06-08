package com.darkbrook.kingdoms.mixin;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.KingdomsItems;
import com.darkbrook.kingdoms.server.item.KingdomsStack;
import com.darkbrook.kingdoms.server.item.nbt.DurabilityData;
import com.darkbrook.kingdoms.server.item.nbt.SmithingProgress;
import com.darkbrook.kingdoms.server.item.nbt.Temperature;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@Mixin(ItemStack.class)
abstract class ItemStackMixin implements KingdomsStack
{
	@Shadow @Final
	private Item item;
	@Shadow
	private NbtCompound nbt;
	
	@Unique @Override
	public ItemStack asStack()
	{
		return (ItemStack) (Object) this;
	}
	
	@Unique @Override
	public Optional<KingdomsItem> getKingdomsItem()
	{
		return KingdomsItems.ofNullable(nbt);
	}
	
	@Unique @Override
	public float getSmithingProgress()
	{
		return SmithingProgress.get(nbt);
	}

	@Unique @Override
	public void setSmithingProgress(float progress)
	{
		SmithingProgress.set(getOrCreateNbt(), progress, Text.translatable(item.getTranslationKey(asStack())));
	}
	
	@Unique @Override
	public int getTemperature(World world)
	{
		return Temperature.get(nbt, world);
	}

	@Unique @Override
	public void setTemperature(World world, int temperature)
	{
		Temperature.set(getOrCreateNbt(), world, temperature);
	}
	
	@Unique @Override
	public boolean isBroken()
	{
		return DurabilityData.get(nbt) <= 0;
	}

	@Inject(method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z",
			at = @At(value = "HEAD", shift = Shift.BY, by = 3), cancellable = true)
	private void damageCustom(int amount, Random random, @Nullable ServerPlayerEntity player,
			CallbackInfoReturnable<Boolean> callback)
	{
		if (DurabilityData.exists(nbt))
			callback.setReturnValue(DurabilityData.damage(nbt, amount, asStack().getMaxDamage()));
	}

	@Shadow
	protected abstract NbtCompound getOrCreateNbt();
}
