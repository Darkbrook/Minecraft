package dev.darkbrook.wildfire.common.mixin;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;

public interface StackSizeOverride
{
	static final Set<Item> AFFECTED_ITEMS = new HashSet<>();

	boolean setCustomMaxStackSize(int size);
	
	void unsetCustomMaxStackSize();
}
