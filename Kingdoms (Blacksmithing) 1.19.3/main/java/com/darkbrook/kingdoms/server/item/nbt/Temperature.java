package com.darkbrook.kingdoms.server.item.nbt;

import static net.minecraft.item.ItemStack.DISPLAY_KEY;
import static net.minecraft.item.ItemStack.LORE_KEY;

import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;

import com.darkbrook.kingdoms.common.nbt.NbtListOptional;
import com.darkbrook.kingdoms.common.nbt.NbtOptional;
import com.darkbrook.kingdoms.common.util.Color;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class Temperature
{
	private static final String COOL_TIME_KEY = "CoolTime";
	private static final String APPROX_TEMP_KEY = "ApproxTemp";
	
	public static int get(@Nullable NbtCompound compound, World world)
	{		
		if (compound != null && compound.contains(COOL_TIME_KEY))
		{
			int temperature = (int) (compound.getLong(COOL_TIME_KEY) - world.getTime());
			updateApproxTemp(compound, temperature);

			if (temperature > 0)
				return temperature;
			
			compound.remove(COOL_TIME_KEY);
		}
		
		return 0;
	}
	
	public static void set(NbtCompound compound, World world, int temperature)
	{
		updateApproxTemp(compound, temperature);
		compound.putLong(COOL_TIME_KEY, world.getTime() + temperature);
	}
	
	private static void updateApproxTemp(NbtCompound compound, int temperature)
	{
		if (temperature < 100)
		{
			if (!compound.contains(APPROX_TEMP_KEY))
				return;
			
			compound.getCompound(DISPLAY_KEY).getList(LORE_KEY, NbtType.STRING).remove(0);
			compound.remove(APPROX_TEMP_KEY);
			return;
		}
		
		int approxTemp = (temperature / 100) * 100;
		
		if (!compound.contains(APPROX_TEMP_KEY))
			setApproxTemp(compound, approxTemp, NbtListOptional::addText);
		else if (compound.getInt(APPROX_TEMP_KEY) != approxTemp)
			setApproxTemp(compound, approxTemp, NbtListOptional::setText);
	}
	
	private static void setApproxTemp(NbtCompound compound, int approxTemp, TriConsumer<NbtListOptional, Integer, Text> action)
	{
		NbtListOptional lore = NbtOptional.of(compound).getOrCreateCompound(DISPLAY_KEY).getOrCreateList(LORE_KEY);
		Text text = Color.RED.on("≈%d°C", approxTemp);
		action.accept(lore, 0, text);
		compound.putInt(APPROX_TEMP_KEY, approxTemp);
	}
}
