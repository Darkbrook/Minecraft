package com.darkbrook.kingdoms.server.item.nbt;

import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.NAME_KEY;

import org.jetbrains.annotations.Nullable;

import com.darkbrook.kingdoms.common.nbt.NbtOptional;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;

public class SmithingProgress
{
	private static final String SMITHING_PROGRESS_KEY = "SmithingProgress";

	public static float get(@Nullable NbtCompound compond)
	{
		return compond != null ? compond.getFloat(SMITHING_PROGRESS_KEY) : 0.0f;
	}
	
	public static void set(NbtCompound compond, float progress, MutableText fallbackDisplayName)
	{
		if (progress <= 0.0f)
			return;
		
		if (!compond.contains(SMITHING_PROGRESS_KEY))
		{
			NbtOptional display = NbtOptional.of(compond).getOrCreateCompound(DISPLAY_KEY);
			MutableText name = display.getText(NAME_KEY).orElse(fallbackDisplayName);
			name.append(" (Worked)");
			display.putText(NAME_KEY, name);
		}
		
		compond.putFloat(SMITHING_PROGRESS_KEY, progress);
	}
}
