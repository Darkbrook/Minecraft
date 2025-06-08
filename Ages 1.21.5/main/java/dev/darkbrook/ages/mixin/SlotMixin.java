package dev.darkbrook.ages.mixin;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.darkbrook.ages.Ages;
import dev.darkbrook.ages.common.Action;
import dev.darkbrook.ages.common.Age;
import dev.darkbrook.ages.common.AgeProgressionManager;
import dev.darkbrook.ages.common.AgeRestrictionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;

@Mixin(Slot.class)
public abstract class SlotMixin
{
	@Unique
	private static final AgeProgressionManager MANAGER = Ages.getInstance().getProgressionManager();
	
	@Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
	private void canTakeItems(PlayerEntity player, CallbackInfoReturnable<Boolean> callback)
	{
		if ((Object) this instanceof CraftingResultSlot slot
				&& handleAgeRestrictions(player, slot))
			callback.setReturnValue(false);
	}
	
	@Shadow
	protected abstract ItemStack getStack();
	
	@Unique
	private boolean handleAgeRestrictions(PlayerEntity player, CraftingResultSlot slot)
	{
		if (!player.getGameMode().isSurvivalLike())
			return false;
		Item item = getStack().getItem();
		return MANAGER.getFutureUnlockAge(item)
			.map(age -> Triple.of(Action.CRAFT, item, age))
			.or(() -> {
				RecipeInputInventory inventory = ((CraftingResultSlotAccessor) slot).getInput();
				for (Age age : MANAGER.getFutureAges())
					for (ItemStack stack : inventory)
					{
						Item ingredient = stack.getItem();
						if (age.items().contains(ingredient))
							return Optional.of(Triple.of(Action.CRAFT_WITH, ingredient, age));
					}
				return Optional.empty();
			}).map(triple -> {
				player.currentScreenHandler.syncState();
				AgeRestrictionHandler.notifyAgeRestricted(triple.getLeft(), player, triple.getMiddle(), triple.getRight());
				return true;
			}).isPresent();
	}
}
