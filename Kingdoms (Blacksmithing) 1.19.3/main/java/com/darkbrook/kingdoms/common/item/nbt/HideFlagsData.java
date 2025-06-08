package com.darkbrook.kingdoms.common.item.nbt;

import com.darkbrook.kingdoms.common.item.ItemStackData;

import net.minecraft.item.ItemStack.TooltipSection;
import net.minecraft.nbt.NbtCompound;

public class HideFlagsData implements ItemStackData.Nbt
{
	private final int flags;

	public HideFlagsData(TooltipSection... flags)
	{
		int cache = 0;
		
		for (TooltipSection flag : flags)
			cache |= flag.getFlag();
		
		this.flags = cache;
	}

	@Override
	public void writeNbt(NbtCompound compound)
	{
		compound.putInt("HideFlags", flags);
	}
}
