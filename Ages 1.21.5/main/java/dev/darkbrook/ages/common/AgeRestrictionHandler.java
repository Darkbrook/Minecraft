package dev.darkbrook.ages.common;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableSet;

import dev.darkbrook.ages.Ages;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AgeRestrictionHandler
{
	private static final Set<RegistryEntry<EntityAttribute>> ATTACK_ATTRIBUTES 
		= ImmutableSet.of(
			EntityAttributes.ATTACK_DAMAGE, 
			EntityAttributes.ATTACK_SPEED, 
			EntityAttributes.ATTACK_KNOCKBACK,
			EntityAttributes.SWEEPING_DAMAGE_RATIO);
	
	private static final AgeProgressionManager MANAGER = Ages.getInstance().getProgressionManager();
	
	public AgeRestrictionHandler()
	{
		AttackEntityCallback.EVENT.register(this::onAttackEntity);
		PlayerBlockBreakEvents.BEFORE.register(this::beforeBreakBlock);
		UseBlockCallback.EVENT.register(this::onUseBlock);
		UseItemCallback.EVENT.register(this::onUseItem);
	}
	
	public boolean handleAgeRestriction(Action action, PlayerEntity player, Item item)
	{
		return handleAgeRestriction(action, player, item, MANAGER::getFutureUnlockAge);
	}
	
	private ActionResult onAttackEntity(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult)
	{
		ItemStack stack = player.getStackInHand(hand);
		return isWeapon(stack) && handleAgeRestriction(Action.USE, player, stack.getItem(), MANAGER::getFutureUnlockAge)
			? ActionResult.FAIL : ActionResult.PASS;
	}
	
	private ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult)
	{
		Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
		if (!player.isSneaking() && handleAgeRestriction(Action.USE, player, block, MANAGER::getFutureUnlockAge))
			return ActionResult.FAIL;
	
		Item item = player.getStackInHand(hand).getItem();
		return item instanceof BlockItem && handleAgeRestriction(Action.PLACE, player, item, MANAGER::getFutureUnlockAge)
			? ActionResult.FAIL : ActionResult.PASS;
	}
	
	private boolean beforeBreakBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity)
	{
		ItemStack stack = player.getMainHandStack();
		return (!stack.isSuitableFor(state) || !handleAgeRestriction(Action.USE, player, stack.getItem(), MANAGER::getFutureUnlockAge))
			&& !handleAgeRestriction(Action.MINE, player, state.getBlock(), MANAGER::getFutureUnlockAge);
	}
	
	private ActionResult onUseItem(PlayerEntity player, World world, Hand hand)
	{
		Item item = player.getStackInHand(hand).getItem();
		return handleAgeRestriction(Action.USE, player, item, MANAGER::getFutureUnlockAge)
			? ActionResult.FAIL : ActionResult.PASS;
	}
	
	public static void notifyAgeRestricted(Action action, PlayerEntity player, Item item, Age age)
	{
		player.sendMessage(Text.literal("Cannot " + action.getDescriptor() + " ")
				.styled(style -> style.withColor(Formatting.RED))
			.append(item.getDefaultStack().toHoverableText())
			.append(" until ")
			.append(age.displayName()), false);
		player.playSoundToPlayer(SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.MASTER, 1.0f, 1.0f);
	}
	
	private static boolean isWeapon(ItemStack stack)
	{
		return Optional.ofNullable(stack.getComponents())
			.map(map -> map.get(DataComponentTypes.ATTRIBUTE_MODIFIERS))
			.map(AttributeModifiersComponent::modifiers)
			.orElse(List.of())
				.stream()
				.anyMatch(entry -> ATTACK_ATTRIBUTES.contains(entry.attribute()));
	}
	
	private static <T extends ItemConvertible> boolean handleAgeRestriction(Action action, PlayerEntity player, 
		T item, Function<T, Optional<Age>> getFutureUnlockAge)
	{
		if (!player.getGameMode().isSurvivalLike())
			return false;
		
		Optional<Age> optional = getFutureUnlockAge.apply(item);
		if (optional.isEmpty())
			return false;
		
		player.currentScreenHandler.syncState();
		notifyAgeRestricted(action, player, item.asItem(), optional.get());
		return true;
	}
}
