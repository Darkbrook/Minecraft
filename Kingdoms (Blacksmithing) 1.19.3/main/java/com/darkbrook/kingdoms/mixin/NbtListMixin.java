package com.darkbrook.kingdoms.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.darkbrook.kingdoms.common.nbt.NbtListOptional;

import net.minecraft.nbt.NbtList;

@Mixin(NbtList.class)
abstract class NbtListMixin implements NbtListOptional
{	
	@Unique @Override
	public NbtList asList()
	{
		return (NbtList) (Object) this;
	}
}
