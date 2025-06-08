package com.darkbrook.kingdoms.server.block.table;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface TableBehavior<T extends TableBlockEntity<T>>
{
	ActionResult interact(T table, PlayerEntity player, ItemStack stack);
}
