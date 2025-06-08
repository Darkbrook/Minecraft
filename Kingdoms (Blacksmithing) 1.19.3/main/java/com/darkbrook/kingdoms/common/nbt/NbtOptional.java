package com.darkbrook.kingdoms.common.nbt;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public interface NbtOptional
{
	static final NbtOptional EMPTY = of(new NbtCompound());

	NbtCompound asCompound();
	
	NbtOptional getCompound(String key);
	
	NbtOptional getOrCreateCompound(String key);
	
	NbtOptional putText(String key, Text text);
	
	NbtListOptional getList(String key);
	
	NbtListOptional getOrCreateList(String key);
	
	Optional<MutableText> getText(String key);
	
	Optional<String> getString(String key);

	OptionalInt getInt(String key);
	
	OptionalLong getLong(String key);

	static NbtOptional ofNullable(@Nullable NbtCompound compound)
	{
		return Optional.ofNullable(compound).map(NbtOptional::of).orElse(EMPTY);
	}
	
	static NbtOptional of(NbtCompound compound)
	{
		return (NbtOptional) compound;
	}
}
