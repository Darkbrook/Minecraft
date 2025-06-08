package com.darkbrook.kingdoms.server.item;

import static com.darkbrook.kingdoms.server.item.KingdomsItem.KINGDOMS_KEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.darkbrook.kingdoms.common.item.nbt.AttackSpeedData;
import com.darkbrook.kingdoms.common.nbt.NbtOptional;
import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.server.item.KingdomsItem.Data;
import com.darkbrook.kingdoms.server.item.items.ArmorItem;
import com.darkbrook.kingdoms.server.item.items.CategorizedItem;
import com.darkbrook.kingdoms.server.item.items.IngotItem;
import com.darkbrook.kingdoms.server.item.items.OreItem;
import com.darkbrook.kingdoms.server.item.items.RingsItem;
import com.darkbrook.kingdoms.server.item.items.ToolItem;
import com.darkbrook.kingdoms.server.item.items.WeaponItem;
import com.darkbrook.kingdoms.server.item.mmo.ArmorMake;
import com.darkbrook.kingdoms.server.item.mmo.ArmorSlot;
import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;
import com.darkbrook.kingdoms.server.item.mmo.Tool;
import com.darkbrook.kingdoms.server.item.mmo.Weapon;
import com.darkbrook.kingdoms.server.item.nbt.DurabilityData;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public class KingdomsItems
{
	private static final Map<String, KingdomsItem> itemMap = new HashMap<>();
	private static final List<KingdomsItem> items = new ArrayList<>();

	public static final KingdomsItem FORGE = register(new CategorizedItem(Category.WORKSTATION, "Forge", Color.RED, Items.CAULDRON));
	public static final KingdomsItem ANVIL = register(new CategorizedItem(Category.WORKSTATION, "Anvil", Color.RED, Items.ANVIL));
	public static final KingdomsItem LIGNITE = register(new CategorizedItem(Category.COMBUSTABLE, "Lignite", Color.CHARCOAL, Items.CHARCOAL));
	public static final KingdomsItem BITUMINOUS_COAL = register(new CategorizedItem(Category.COMBUSTABLE, "Bituminous Coal", Color.CHARCOAL, Items.COAL));
	public static final KingdomsItem MALACHITE = register(new OreItem(Metal.COPPER, "Malachite", Items.RAW_COPPER));
	public static final KingdomsItem STANNITE = register(new OreItem(Metal.BRONZE, "Stannite", Items.RAW_GOLD, Data.textured()));
	public static final KingdomsItem HEMATITE = register(new OreItem(Metal.IRON, "Hematite",Items.RAW_IRON));
	public static final KingdomsItem SPERRYLITE = register(new OreItem(Metal.PLATINUM, "Sperrylite", Items.PRISMARINE_CRYSTALS));
	public static final KingdomsItem KAMACITE = register(new OreItem(Metal.METEORIC_IRON, "Kamacite", Items.NETHERITE_SCRAP));
	public static final KingdomsItem COPPER_RINGS = register(new RingsItem(Metal.COPPER));
	public static final KingdomsItem BRONZE_RINGS = register(new RingsItem(Metal.BRONZE));
	public static final KingdomsItem IRON_RINGS = register(new RingsItem(Metal.IRON));
	public static final KingdomsItem STEEL_RINGS = register(new RingsItem(Metal.STEEL));
	public static final KingdomsItem PLATINUM_RINGS = register(new RingsItem(Metal.PLATINUM));
	public static final KingdomsItem METEORIC_IRON_RINGS = register(new RingsItem(Metal.METEORIC_IRON));
	public static final KingdomsItem COPPER_INGOT = register(new IngotItem(Metal.COPPER, Items.COPPER_INGOT));
	public static final KingdomsItem BRONZE_INGOT = register(new IngotItem(Metal.BRONZE, Items.GOLD_INGOT, Data.textured()));
	public static final KingdomsItem IRON_INGOT = register(new IngotItem(Metal.IRON, Items.IRON_INGOT));
	public static final KingdomsItem STEEL_INGOT = register(new IngotItem(Metal.STEEL, Items.IRON_INGOT, Data.textured()));
	public static final KingdomsItem PLATINUM_INGOT = register(new IngotItem(Metal.PLATINUM, Items.DIAMOND, Data.textured()));
	public static final KingdomsItem METEORIC_IRON_INGOT = register(new IngotItem(Metal.METEORIC_IRON, Items.NETHERITE_INGOT));
	public static final KingdomsItem COPPER_MORNINGSTAR = register(new WeaponItem(Metal.COPPER, Weapon.MORNINGSTAR, Items.GOLDEN_SHOVEL, Data.textured()));
	public static final KingdomsItem BRONZE_MORNINGSTAR = register(new WeaponItem(Metal.BRONZE, Weapon.MORNINGSTAR, Items.GOLDEN_SHOVEL, Data.textured()));
	public static final KingdomsItem IRON_MORNINGSTAR = register(new WeaponItem(Metal.IRON, Weapon.MORNINGSTAR, Items.IRON_SHOVEL, Data.textured()));
	public static final KingdomsItem STEEL_MORNINGSTAR = register(new WeaponItem(Metal.STEEL, Weapon.MORNINGSTAR, Items.IRON_SHOVEL, Data.textured()));
	public static final KingdomsItem PLATINUM_MORNINGSTAR = register(new WeaponItem(Metal.PLATINUM, Weapon.MORNINGSTAR, Items.DIAMOND_SHOVEL, Data.textured()));
	public static final KingdomsItem METEORIC_IRON_MORNINGSTAR = register(new WeaponItem(Metal.METEORIC_IRON, Weapon.MORNINGSTAR, Items.NETHERITE_SHOVEL, Data.textured()));
	public static final KingdomsItem COPPER_SMITHING_HAMMER = register(new ToolItem(Metal.COPPER, Tool.SMITHING_HAMMER, Items.GOLDEN_AXE, Data.textured(new DurabilityData(100), new AttackSpeedData(20))));
	public static final KingdomsItem BRONZE_SMITHING_HAMMER = register(new ToolItem(Metal.BRONZE, Tool.SMITHING_HAMMER, Items.GOLDEN_AXE, Data.textured(new DurabilityData(120), new AttackSpeedData(18))));
	public static final KingdomsItem IRON_SMITHING_HAMMER = register(new ToolItem(Metal.IRON, Tool.SMITHING_HAMMER, Items.IRON_AXE, Data.textured(new DurabilityData(140), new AttackSpeedData(16))));
	public static final KingdomsItem STEEL_SMITHING_HAMMER = register(new ToolItem(Metal.STEEL, Tool.SMITHING_HAMMER, Items.IRON_AXE, Data.textured(new DurabilityData(160), new AttackSpeedData(14))));
	public static final KingdomsItem PLATINUM_SMITHING_HAMMER = register(new ToolItem(Metal.PLATINUM, Tool.SMITHING_HAMMER, Items.DIAMOND_AXE, Data.textured(new DurabilityData(180), new AttackSpeedData(12))));
	public static final KingdomsItem METEORIC_IRON_SMITHING_HAMMER = register(new ToolItem(Metal.METEORIC_IRON, Tool.SMITHING_HAMMER, Items.NETHERITE_AXE, Data.textured(new DurabilityData(200), new AttackSpeedData(10))));
		
	public static final ArmorSet<KingdomsItem> COPPER_MAIL = registerArmorSet(Metal.COPPER, ArmorMake.MAIL, ArmorSet.CHAINMAIL, Data::textured);
	public static final ArmorSet<KingdomsItem> BRONZE_MAIL = registerArmorSet(Metal.BRONZE, ArmorMake.MAIL, ArmorSet.CHAINMAIL, Data::textured);
	public static final ArmorSet<KingdomsItem> IRON_MAIL = registerArmorSet(Metal.IRON, ArmorMake.MAIL, ArmorSet.CHAINMAIL, Data::textured);
	public static final ArmorSet<KingdomsItem> STEEL_MAIL = registerArmorSet(Metal.STEEL, ArmorMake.MAIL, ArmorSet.CHAINMAIL, Data::textured);
	public static final ArmorSet<KingdomsItem> PLATINUM_MAIL = registerArmorSet(Metal.PLATINUM, ArmorMake.MAIL, ArmorSet.CHAINMAIL, Data::textured);
	public static final ArmorSet<KingdomsItem> METEORIC_IRON_MAIL = registerArmorSet(Metal.METEORIC_IRON, ArmorMake.MAIL, ArmorSet.CHAINMAIL, Data::textured);
	public static final ArmorSet<KingdomsItem> COPPER_PLATE = registerArmorSet(Metal.COPPER, ArmorMake.PLATE, ArmorSet.GOLDEN, Data::textured);
	public static final ArmorSet<KingdomsItem> BRONZE_PLATE = registerArmorSet(Metal.BRONZE, ArmorMake.PLATE, ArmorSet.GOLDEN, Data::textured);
	public static final ArmorSet<KingdomsItem> IRON_PLATE = registerArmorSet(Metal.IRON, ArmorMake.PLATE, ArmorSet.IRON, Data::empty);
	public static final ArmorSet<KingdomsItem> STEEL_PLATE = registerArmorSet(Metal.STEEL, ArmorMake.PLATE, ArmorSet.IRON, Data::textured);
	public static final ArmorSet<KingdomsItem> PLATINUM_PLATE = registerArmorSet(Metal.PLATINUM, ArmorMake.PLATE, ArmorSet.DIAMOND, Data::empty);
	public static final ArmorSet<KingdomsItem> METEORIC_IRON_PLATE = registerArmorSet(Metal.METEORIC_IRON, ArmorMake.PLATE, ArmorSet.NETHERITE, Data::empty);
		
	public static Optional<KingdomsItem> ofNullable(@Nullable NbtCompound compound)
	{
		return NbtOptional.ofNullable(compound).getString(KINGDOMS_KEY).map(itemMap::get);
	}

	public static List<KingdomsItem> toList()
	{
		return items;
	}
	
	private static ArmorSet<KingdomsItem> registerArmorSet(Metal metal, ArmorMake make, ArmorSet<Item> armorSet, 
			Supplier<Data> dataSupplier)
	{
		return new ArmorSet<>(
				register(new ArmorItem(metal, make, ArmorSlot.HELMET, armorSet, dataSupplier.get())),
				register(new ArmorItem(metal, make, ArmorSlot.CHESTPLATE, armorSet, dataSupplier.get())), 
				register(new ArmorItem(metal, make, ArmorSlot.LEGGINGS, armorSet, dataSupplier.get())), 
				register(new ArmorItem(metal, make, ArmorSlot.BOOTS, armorSet, dataSupplier.get())));
	}
	
	private static KingdomsItem register(KingdomsItem item)
	{
		itemMap.put(item.getName(), item);
		items.add(item);
		return item;
	}
}
