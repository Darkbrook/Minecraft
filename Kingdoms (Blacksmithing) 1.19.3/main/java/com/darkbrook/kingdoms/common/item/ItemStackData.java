package com.darkbrook.kingdoms.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public interface ItemStackData
{
	void write(ItemStack stack);

	interface Nbt extends ItemStackData
	{
		@Override
		default void write(ItemStack stack)
		{
			writeNbt(stack.getOrCreateNbt());
		}
		
		void writeNbt(NbtCompound compound);
	}
}
