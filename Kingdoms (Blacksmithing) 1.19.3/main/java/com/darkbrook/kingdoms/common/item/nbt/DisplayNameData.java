package com.darkbrook.kingdoms.common.item.nbt;

import com.darkbrook.kingdoms.common.item.ItemStackData;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class DisplayNameData implements ItemStackData
{
	private final Text displayName;

	public DisplayNameData(Text displayName)
	{
		this.displayName = displayName;
	}

	@Override
	public void write(ItemStack stack)
	{
		stack.setCustomName(displayName);
	}
}
