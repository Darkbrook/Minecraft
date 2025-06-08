package com.darkbrook.kingdoms.common.item.nbt;

import static net.minecraft.block.entity.SkullBlockEntity.SKULL_OWNER_KEY;

import com.darkbrook.kingdoms.common.item.ItemStackData;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;

public class SkullOwnerData implements ItemStackData.Nbt
{
	private final NbtCompound skullOwner = new NbtCompound();
	
	public SkullOwnerData(String name)
	{
		SkullBlockEntity.loadProperties(new GameProfile(null, name), profile -> NbtHelper.writeGameProfile(skullOwner, 
				profile));
	}

	@Override
	public void writeNbt(NbtCompound compound)
	{
		compound.put(SKULL_OWNER_KEY, skullOwner);
	}
}
