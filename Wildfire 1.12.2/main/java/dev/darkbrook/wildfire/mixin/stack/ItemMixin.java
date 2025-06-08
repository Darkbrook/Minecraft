package dev.darkbrook.wildfire.mixin.stack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.common.mixin.StackSizeOverride;
import dev.darkbrook.wildfire.common.util.SimpleResourceLocation;
import net.minecraft.item.Item;

@Mixin(Item.class)
public abstract class ItemMixin implements StackSizeOverride
{
	@Shadow
	private int maxStackSize;
	@Unique
	private int customMaxStackSize;

	@Unique @Override
	public boolean setCustomMaxStackSize(int size)
	{
		if (size < 1 || size > 64)
			throw new IllegalArgumentException("Stack size must be between 1 and 64");
		
		Item item = (Item) (Object) this;
		
		if (size == customMaxStackSize)
		{
			Wildfire.LOGGER.debug("Max stack size for {} already set to {}", SimpleResourceLocation.of(item), size);
			return false;
		}
		
		if (size != maxStackSize)
		{
			AFFECTED_ITEMS.add(item);
			customMaxStackSize = size;
			Wildfire.LOGGER.debug("Modified max stack size for {}: {} -> {}", SimpleResourceLocation.of(item), maxStackSize, customMaxStackSize);
		}
		else if (customMaxStackSize == 0)
		{
			Wildfire.LOGGER.debug("Max stack size for {} already set to {}", SimpleResourceLocation.of(item), maxStackSize);
			return false;
		}
		else
		{
			unsetCustomMaxStackSize();
		}
		
		return true;
	}
	
	@Unique @Override
	public void unsetCustomMaxStackSize()
	{
		Item item = (Item) (Object) this;
		AFFECTED_ITEMS.remove(item);
		Wildfire.LOGGER.debug("Restored default max stack size for {}: {} -> {}", SimpleResourceLocation.of(item), customMaxStackSize, maxStackSize);
		customMaxStackSize = 0;
	}
	
	@Inject(method = "getItemStackLimit", at = @At("HEAD"), cancellable = true)
	private void getCustomMaxStackSize(CallbackInfoReturnable<Integer> callback)
	{
		if (Wildfire.getInstance().getOptions().useCustomStackSizes() && customMaxStackSize > 0)
			callback.setReturnValue(customMaxStackSize);	
	}
}
