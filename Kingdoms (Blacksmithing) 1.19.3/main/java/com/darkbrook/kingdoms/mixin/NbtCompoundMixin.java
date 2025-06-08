package com.darkbrook.kingdoms.mixin;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.darkbrook.kingdoms.common.nbt.NbtListOptional;
import com.darkbrook.kingdoms.common.nbt.NbtOptional;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLong;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

@Mixin(NbtCompound.class)
abstract class NbtCompoundMixin implements NbtOptional
{
	@Unique @Override
	public NbtCompound asCompound()
	{
		return (NbtCompound) (Object) this;
	}
	
	@Unique @Override
	public NbtOptional getCompound(String key)
	{
		return get(key, NbtCompound.TYPE).map(NbtOptional::of).orElse(NbtOptional.EMPTY);
	}
	
	@Unique @Override
	public NbtOptional getOrCreateCompound(String key)
	{
		return NbtOptional.of(getOrCreate(key, NbtCompound.TYPE, NbtCompound::new));
	}
	
	@Unique @Override
	public NbtOptional putText(String key, Text text)
	{
		putString(key, Text.Serializer.toJson(text));
		return this;
	}
	
	@Unique @Override
	public NbtListOptional getList(String key)
	{
		return get(key, NbtList.TYPE).map(NbtListOptional::of).orElse(NbtListOptional.EMPTY);
	}
	
	@Unique @Override
	public NbtListOptional getOrCreateList(String key)
	{
		return NbtListOptional.of(getOrCreate(key, NbtList.TYPE, NbtList::new));
	}
	
	@Unique @Override
	public Optional<String> getString(String key)
	{
		return get(key, NbtString.TYPE).map(NbtString::asString);
	}
	
	@Unique @Override
	public OptionalInt getInt(String key)
	{
		return get(key, NbtInt.TYPE).map(value -> OptionalInt.of(value.intValue())).get();
	}
	
	@Unique @Override
	public OptionalLong getLong(String key)
	{
		return get(key, NbtLong.TYPE).map(value -> OptionalLong.of(value.longValue())).get();
	}
	
	@Unique @Override
	public Optional<MutableText> getText(String key)
	{
		return getString(key).map(Text.Serializer::fromJson);
	}
	
	@Unique
	private <T extends NbtElement> T getOrCreate(String key, NbtType<T> type, Supplier<T> defaultValue)
	{
		return get(key, type).orElseGet(() ->
		{
			T element = defaultValue.get();
			put(key, element);
			return element;
		});
	}
	
	@Unique @SuppressWarnings("unchecked")
	private <T extends NbtElement> Optional<T> get(String key, NbtType<T> type)
	{
		return Optional.ofNullable(get(key)).filter(element -> element.getNbtType() == type)
				.map(element -> (T) element);
	}
	
	@Shadow
	protected abstract NbtElement get(String key);
	
	@Shadow
	protected abstract NbtElement put(String key, NbtElement element);
	
	@Shadow
	protected abstract void putString(String key, String value);
}
