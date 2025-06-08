package dev.darkbrook.ages.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.slot.CraftingResultSlot;

@Mixin(CraftingResultSlot.class)
public interface CraftingResultSlotAccessor
{
	@Accessor
	RecipeInputInventory getInput();
}
