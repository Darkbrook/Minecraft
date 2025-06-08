package com.darkbrook.kingdoms.server.block.anvil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.darkbrook.kingdoms.server.block.table.TableBehavior;
import com.darkbrook.kingdoms.server.item.KingdomsItem;
import com.darkbrook.kingdoms.server.item.KingdomsItems;

import net.minecraft.item.ItemStack;

public interface AnvilBehavior extends TableBehavior<AnvilBlockEntity>
{
	static final AnvilBehavior PICKUP_ITEM = (anvil, player, stack) -> anvil.swapItems(player, ItemStack.EMPTY);
	static final AnvilBehavior SWAP_ITEMS = AnvilBlockEntity::swapItems;
	
	@SuppressWarnings("serial") 
	static final Map<KingdomsItem, AnvilBehavior> DEFAULTS = Collections.unmodifiableMap(new HashMap<>()
	{{
		put(null, PICKUP_ITEM);
		put(KingdomsItems.COPPER_INGOT, SWAP_ITEMS);
		put(KingdomsItems.BRONZE_INGOT, SWAP_ITEMS);
		put(KingdomsItems.IRON_INGOT, SWAP_ITEMS);
		put(KingdomsItems.STEEL_INGOT, SWAP_ITEMS);
		put(KingdomsItems.PLATINUM_INGOT, SWAP_ITEMS);
		put(KingdomsItems.METEORIC_IRON_INGOT, SWAP_ITEMS);
	}});
}
