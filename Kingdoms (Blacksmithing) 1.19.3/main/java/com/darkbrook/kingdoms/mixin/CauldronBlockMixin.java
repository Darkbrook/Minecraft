package com.darkbrook.kingdoms.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.darkbrook.kingdoms.KingdomsServer;
import com.darkbrook.kingdoms.server.block.forge.ForgeBlockEntity;
import com.darkbrook.kingdoms.server.block.table.TableBlock;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
abstract class CauldronBlockMixin implements BlockEntityProvider
{
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify)
	{
		TableBlock.onBlockAdded(state, world, pos, oldState);
	}

	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
	{
		TableBlock.onStateReplaced(state, world, pos, newState);
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit)
	{
		return TableBlock.onUse(world, pos, player, hand);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
	{
		return new ForgeBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type)
	{
		return TableBlock.getTicker(type, KingdomsServer.FORGE_BLOCK_ENTITY);
	}
}
