package com.darkbrook.kingdoms.server.block.table;

import com.google.common.base.Function;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TableBlock
{
	public static void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState)
	{
		if (!state.isOf(oldState.getBlock()))
			accept(world.getBlockEntity(pos), TableBlockEntity::add);
	}

	public static void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState)
	{
		if (!state.isOf(newState.getBlock()) && accept(world.getBlockEntity(pos), TableBlockEntity::remove))
			world.removeBlockEntity(pos);
	}

	public static ActionResult onUse(World world, BlockPos pos, PlayerEntity player, Hand hand)
	{
		return apply(world.getBlockEntity(pos), table -> table.interact(player, hand), ActionResult.FAIL);
	}
	
	public static <T extends BlockEntity, E extends TableBlockEntity<E>> BlockEntityTicker<T> getTicker(
			BlockEntityType<T> givenType, BlockEntityType<E> expectedType)
	{
		return givenType == expectedType ? (world, pos, state, entity) -> ((TableBlockEntity<?>) entity).tick() : null;
	}
	
	private static boolean accept(BlockEntity entity, Consumer<TableBlockEntity<?>> consumer)
	{		
		return apply(entity, table -> 
		{
			consumer.accept(table); 
			return true;
		}, false);
	}

	private static <R> R apply(BlockEntity entity, Function<TableBlockEntity<?>, R> function, R fallback)
	{
		return entity instanceof TableBlockEntity ? function.apply((TableBlockEntity<?>) entity) : fallback;
	}
}
