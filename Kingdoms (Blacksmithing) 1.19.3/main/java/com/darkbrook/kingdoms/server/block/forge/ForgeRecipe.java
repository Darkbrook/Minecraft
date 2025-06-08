package com.darkbrook.kingdoms.server.block.forge;

import static com.darkbrook.kingdoms.server.item.KingdomsItems.BRONZE_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.COPPER_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.HEMATITE;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.IRON_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.KAMACITE;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.MALACHITE;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.METEORIC_IRON_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.PLATINUM_INGOT;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.SPERRYLITE;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.STANNITE;
import static com.darkbrook.kingdoms.server.item.KingdomsItems.STEEL_INGOT;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.darkbrook.kingdoms.server.item.KingdomsItem;

public class ForgeRecipe
{
	@SuppressWarnings("serial")
	public static final Map<KingdomsItem, KingdomsItem> LIGNITE = Collections.unmodifiableMap(new HashMap<>()
	{{
		put(MALACHITE, COPPER_INGOT);
		put(STANNITE, BRONZE_INGOT);
		put(HEMATITE, IRON_INGOT);
	}});
	
	@SuppressWarnings("serial")
	public static final Map<KingdomsItem, KingdomsItem> BITUMINOUS_COAL = Collections.unmodifiableMap(new HashMap<>()
	{{
		putAll(LIGNITE);
		put(HEMATITE, STEEL_INGOT);
		put(SPERRYLITE, PLATINUM_INGOT);
		put(KAMACITE, METEORIC_IRON_INGOT);
	}});
}
