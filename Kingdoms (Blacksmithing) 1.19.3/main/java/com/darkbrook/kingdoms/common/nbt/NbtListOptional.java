package com.darkbrook.kingdoms.common.nbt;

import net.minecraft.nbt.NbtList;

public interface NbtListOptional extends NbtTextList
{
	static NbtListOptional EMPTY = of(new NbtList());

	NbtList asList();
	
	static NbtListOptional of(NbtList list)
	{
		return (NbtListOptional) list;
	}
}
