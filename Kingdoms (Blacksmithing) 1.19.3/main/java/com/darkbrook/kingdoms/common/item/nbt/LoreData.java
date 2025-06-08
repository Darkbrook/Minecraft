package com.darkbrook.kingdoms.common.item.nbt;

import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;

import com.darkbrook.kingdoms.common.item.ItemStackData;
import com.darkbrook.kingdoms.common.nbt.NbtListOptional;
import com.darkbrook.kingdoms.common.nbt.NbtOptional;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class LoreData implements ItemStackData.Nbt
{
	private final Text[] lore;
	
	public LoreData(Text... lore)
	{
		this.lore = lore;
	}

	@Override
	public void writeNbt(NbtCompound compound)
	{
		NbtListOptional lore = NbtOptional.of(compound).getOrCreateCompound(DISPLAY_KEY).getOrCreateList(LORE_KEY);
		
		for (Text text : this.lore)
			lore.addText(text);
	}
}
