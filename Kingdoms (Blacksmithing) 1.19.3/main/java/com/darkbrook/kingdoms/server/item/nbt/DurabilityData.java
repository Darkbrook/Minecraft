package com.darkbrook.kingdoms.server.item.nbt;

import static net.minecraft.item.ItemStack.DAMAGE_KEY;

import org.jetbrains.annotations.Nullable;

import com.darkbrook.kingdoms.common.item.ItemStackData;
import com.darkbrook.kingdoms.common.nbt.NbtOptional;

import net.minecraft.nbt.NbtCompound;

public class DurabilityData implements ItemStackData.Nbt
{
	private static final String DURABILITY_KEY = "Durability";
	private static final String VALUE_KEY = "value";
	private static final String MAX_KEY = "max";

	private final int durability;
	
	public DurabilityData(int durability)
	{
		this.durability = durability;
	}
	
	@Override
	public void writeNbt(NbtCompound compound)
	{
		compound = NbtOptional.of(compound).getOrCreateCompound(DURABILITY_KEY).asCompound();
		compound.putInt(VALUE_KEY, durability);
		compound.putInt(MAX_KEY, durability);
	}
	
	public static boolean exists(@Nullable NbtCompound compound)
	{
		return compound != null && compound.contains(DURABILITY_KEY);
	}
	
	public static boolean damage(NbtCompound compound, int amount, int maxDamage)
	{
		NbtCompound durability = compound.getCompound(DURABILITY_KEY);
		int value = Math.max(durability.getInt(VALUE_KEY) - amount, 0);
		durability.putInt(VALUE_KEY, value);
		compound.putInt(DAMAGE_KEY, Math.round((1.0f - (float) value / durability.getInt(MAX_KEY)) * maxDamage));
		return value == 0;
	}
	
	public static int get(@Nullable NbtCompound compound)
	{
		return NbtOptional.ofNullable(compound).getCompound(DURABILITY_KEY).getInt(VALUE_KEY).orElse(0);
	}
}
