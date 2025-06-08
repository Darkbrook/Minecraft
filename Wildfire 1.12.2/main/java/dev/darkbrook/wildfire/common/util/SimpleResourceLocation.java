package dev.darkbrook.wildfire.common.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class SimpleResourceLocation
{
	private SimpleResourceLocation() {}

	public static String of(IForgeRegistryEntry<?> entry)
	{
		return of(entry.getRegistryName());
	}

	public static String of(ResourceLocation location)
	{
		return location.getNamespace().equals("minecraft") 
				? location.getPath()
				: location.toString();
	}
}
