package com.darkbrook.kingdoms.server.block.forge;

import static com.darkbrook.kingdoms.server.item.KingdomsItems.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.darkbrook.kingdoms.server.block.table.TableBehavior;
import com.darkbrook.kingdoms.server.item.KingdomsItem;

import net.minecraft.item.ItemStack;

public interface ForgeBehavior extends TableBehavior<ForgeBlockEntity>
{
	static final ForgeBehavior ADD_GRADE_1_FUEL = (forge, player, stack) -> forge.addFuel(player, stack, 500, 0.75f);
	static final ForgeBehavior ADD_GRADE_2_FUEL = (forge, player, stack) -> forge.addFuel(player, stack, 500, 1.0f);
	static final ForgeBehavior PICKUP_ITEM = (forge, player, stack) -> forge.swapItems(player, ItemStack.EMPTY);
	static final ForgeBehavior SWAP_ITEMS = ForgeBlockEntity::swapItems;

	@SuppressWarnings("serial") 
	static final Map<KingdomsItem, ForgeBehavior> DEFAULTS = Collections.unmodifiableMap(new HashMap<>()
	{{
		put(null, PICKUP_ITEM);
		put(LIGNITE, ADD_GRADE_1_FUEL);
		put(BITUMINOUS_COAL, ADD_GRADE_2_FUEL);
		put(MALACHITE, SWAP_ITEMS);
		put(STANNITE, SWAP_ITEMS);
		put(HEMATITE, SWAP_ITEMS);
		put(SPERRYLITE, SWAP_ITEMS);
		put(KAMACITE, SWAP_ITEMS);
		put(COPPER_INGOT, SWAP_ITEMS);
		put(BRONZE_INGOT, SWAP_ITEMS);
		put(IRON_INGOT, SWAP_ITEMS);
		put(STEEL_INGOT, SWAP_ITEMS);
		put(PLATINUM_INGOT, SWAP_ITEMS);
		put(METEORIC_IRON_INGOT, SWAP_ITEMS);
	}});
}
