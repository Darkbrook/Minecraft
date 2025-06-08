package com.darkbrook.kingdoms.server.block.anvil;



import static com.darkbrook.kingdoms.server.item.KingdomsItems.BRONZE_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.BRONZE_RINGS;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.BRONZE_SMITHING_HAMMER;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.COPPER_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.COPPER_RINGS;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.COPPER_SMITHING_HAMMER;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.IRON_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.IRON_RINGS;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.IRON_SMITHING_HAMMER;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.METEORIC_IRON_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.METEORIC_IRON_RINGS;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.METEORIC_IRON_SMITHING_HAMMER;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.PLATINUM_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.PLATINUM_RINGS;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.PLATINUM_SMITHING_HAMMER;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.STEEL_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.STEEL_RINGS;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.STEEL_SMITHING_HAMMER;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.darkbrook.kingdoms.common.item.ItemStackConvertable;
import com.darkbrook.kingdoms.server.block.table.TableRecipe;
import com.darkbrook.kingdoms.server.item.KingdomsItem;

public class AnvilRecipe extends TableRecipe<AnvilBlockEntity>
{
	@SuppressWarnings("serial")
	public static final Map<KingdomsItem, Integer> TOOL_FORCE = Collections.unmodifiableMap(new HashMap<>()
	{{
		put(COPPER_SMITHING_HAMMER, 1);
		put(BRONZE_SMITHING_HAMMER, 4);
		put(IRON_SMITHING_HAMMER, 16);
		put(STEEL_SMITHING_HAMMER, 64);
		put(PLATINUM_SMITHING_HAMMER, 256);
		put(METEORIC_IRON_SMITHING_HAMMER, 1024);
	}});

	@SuppressWarnings("serial")
	public static final Map<KingdomsItem, AnvilRecipe> DEFAULTS = Collections.unmodifiableMap(new HashMap<>()
	{{
		put(COPPER_INGOT, new AnvilRecipe(1, 10, () -> COPPER_RINGS.createStack(3)));
		put(BRONZE_INGOT, new AnvilRecipe(4, 12, () -> BRONZE_RINGS.createStack(3)));
		put(IRON_INGOT, new AnvilRecipe(16, 14, () -> IRON_RINGS.createStack(3)));
		put(STEEL_INGOT, new AnvilRecipe(64, 16, () -> STEEL_RINGS.createStack(3)));
		put(PLATINUM_INGOT, new AnvilRecipe(256, 18, () -> PLATINUM_RINGS.createStack(3)));
		put(METEORIC_IRON_INGOT, new AnvilRecipe(1024, 20, () -> METEORIC_IRON_RINGS.createStack(3)));
	}});
	
	private final int toolForce;
	private final int force;
	
	public AnvilRecipe(int toolForce, int force, ItemStackConvertable stack)
	{
		super(anvil -> stack);
		this.toolForce = toolForce;
		this.force = force;
	}

	public int getToolForce()
	{
		return toolForce;
	}

	public int getForce()
	{
		return force;
	}	
}
