package com.darkbrook.island.mmo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.darkbrook.island.References;
import com.darkbrook.island.bunkerworld.GunType;
import com.darkbrook.island.library.gui.Gui;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.SkullCreator;
import com.darkbrook.island.mmo.combat.Armor;
import com.darkbrook.island.mmo.combat.ArmorType;
import com.darkbrook.island.mmo.combat.ArmorValue;
import com.darkbrook.island.mmo.combat.InstanceBattle;
import com.darkbrook.island.mmo.entity.EntityHook;
import com.darkbrook.island.mmo.entity.HeartBeat;
import com.darkbrook.island.mmo.experience.Experience;
import com.darkbrook.island.mmo.gui.GuiTypeArmorDump;
import com.darkbrook.island.mmo.gui.GuiTypeInstanceBattleMenu;
import com.darkbrook.island.mmo.gui.GuiTypeItemDump;
import com.darkbrook.island.mmo.gui.GuiTypeMenu;
import com.darkbrook.island.mmo.gui.GuiTypeTome;
import com.darkbrook.island.mmo.item.DamageDie;
import com.darkbrook.island.mmo.item.ItemHook;
import com.darkbrook.island.mmo.item.Tome;
import com.darkbrook.island.mmo.mining.MineHook;
import com.darkbrook.island.mmo.misc.PhysicsSandHook;

public class GameRegistry {
	
	public static final Gui MENU = new Gui(ChatColor.DARK_GRAY + "Admin Menu", 1, new GuiTypeMenu());
	public static final Gui MENU_INSTANCE_BATTLES = new Gui(ChatColor.DARK_GRAY + "Instance Battle Menu", 3, new GuiTypeInstanceBattleMenu());
	public static final Gui MENU_ITEMS = new Gui(ChatColor.DARK_GRAY + "Items Menu", 3, new GuiTypeItemDump());
	public static final Gui MENU_ARMOR = new Gui(ChatColor.DARK_GRAY + "Armor Menu", 3, new GuiTypeArmorDump());
	public static final Gui MENU_LEGENDARIES = new Gui(ChatColor.DARK_GRAY + "Legendaries Menu", 3, new GuiTypeItemDump());
	public static final Gui MENU_BUNKERWORLD = new Gui(ChatColor.DARK_GRAY + "Bunker World Menu", 3, new GuiTypeItemDump());
	public static final Gui TOME = new Gui(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Tome Of Action", 1, new GuiTypeTome());

	public static final List<ItemStack> MENU_CONTENTS = new ArrayList<ItemStack>();
	public static final List<ItemStack> MENU_ITEMS_CONTENTS = new ArrayList<ItemStack>();
	public static final List<ArmorValue> MENU_ARMOR_VALUES = new ArrayList<ArmorValue>();
	public static final List<ItemStack> MENU_ARMOR_CONTENTS = new ArrayList<ItemStack>();
	public static final List<ItemStack> MENU_LEGENDARIES_CONTENTS = new ArrayList<ItemStack>();
	public static final List<ItemStack> MENU_BUNKERWORLD_CONTENTS = new ArrayList<ItemStack>();
	public static final List<ItemStack> TOME_CONTENTS = new ArrayList<ItemStack>();

	public static ItemStack instancebattle;
	public static ItemStack items;
	public static ItemStack armor;
	public static ItemStack legendaries;
	public static ItemStack bunkerworld;
	public static ItemStack back;
	
	public static ItemStack shard;
	public static ItemStack hide_strip;
	public static ItemStack stone_fragment;
	public static ItemStack coal_fragment;
	public static ItemStack iron_fragment;
	public static ItemStack gold_fragment;
	public static ItemStack crystal_fragment;
	public static ItemStack slime_core;
	public static ItemStack enchanted_slime_core;
	public static ItemStack die;
	public static ItemStack pouch;
	public static ItemStack chestskull;
	public static ItemStack minor_potion;

	public static ItemStack is_ofthesky;
	public static ItemStack bag_of_holding;
	public static ItemStack magic_carpet;

	public static ItemStack action_tome;
	public static ItemStack action_clickfeet;
	public static ItemStack action_knockknee;
	public static ItemStack action_shiver;
	public static ItemStack action_wink;
	public static ItemStack action_punchknuckles;
	
	public static boolean isEnabled() {
		return References.mmodata.getBoolean("enabled");
	}
	
	public static void load(Plugin plugin) {

		GameHook.load(plugin);
		
		if(isEnabled()) {
			
			//GameHook.loadMMO(plugin);
			//Experience.load(plugin);
			//Armor.load(plugin);
			MineHook.load(plugin);
			//InstanceBattle.load(plugin);
			//EntityHook.load(plugin);
			//ItemHook.load(plugin);
			PhysicsSandHook.load(plugin);
			//HeartBeat.load();

			ItemHandler.registerItemActivator(new DamageDie());
			ItemHandler.registerItemActivator(new Tome());
		
		}

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		instancebattle = ItemHandler.setDisplayName(new ItemStack(Material.PAPER), ChatColor.WHITE + "" + ChatColor.BOLD + "Open Instance Battle Menu");
		items = ItemHandler.setDisplayName(new ItemStack(Material.PRISMARINE_SHARD), ChatColor.GRAY + "" + ChatColor.BOLD + "Open Items Menu");
		armor = ItemHandler.addFlag(ItemHandler.setDisplayName(new ItemStack(Material.DIAMOND_CHESTPLATE), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Open Armor Menu"), ItemFlag.HIDE_ATTRIBUTES);
		legendaries = ItemHandler.setDisplayName(new ItemStack(Material.EMPTY_MAP), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Open Legendaries Menu");
		bunkerworld = ItemHandler.addFlag(ItemHandler.setDisplayName(new ItemStack(Material.WOOD_HOE), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Open Bunker World Menu"), ItemFlag.HIDE_ATTRIBUTES);
		back = ItemHandler.setDisplayName(new ItemStack(Material.WOOL, 1,  (byte) 14), ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		shard = ItemHandler.setDisplayName(new ItemStack(Material.PRISMARINE_SHARD), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Bluestone Shard");
		addLore(shard, ChatColor.AQUA, "A bluestone shard made from the");
		addLore(shard, ChatColor.AQUA, "infusion of blue crystals and marble;");
		addLore(shard, ChatColor.AQUA, "Worth 1 unit of currency.");

		hide_strip = ItemHandler.setDisplayName(new ItemStack(Material.LEATHER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Hide Strip");
		addLore(hide_strip, ChatColor.GOLD, "A hide strip used in the");
		addLore(hide_strip, ChatColor.GOLD, "creation of armor.");

		stone_fragment = ItemHandler.setDisplayName(new ItemStack(Material.CLAY_BALL), ChatColor.GRAY + "" + ChatColor.BOLD + "Stone Fragment");
		addLore(stone_fragment, ChatColor.WHITE, "A fragment of stone used in the");
		addLore(stone_fragment, ChatColor.WHITE, "creation of buildings and tools.");
		
		coal_fragment = ItemHandler.setDisplayName(new ItemStack(Material.COAL), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Coal Fragment");
		addLore(coal_fragment, ChatColor.GRAY, "A fragment of coal used in the");
		addLore(coal_fragment, ChatColor.GRAY, "creation of armor, weapons, and tools.");

		iron_fragment = ItemHandler.setDisplayName(new ItemStack(Material.INK_SACK, 1, (byte) 7), ChatColor.GRAY + "" + ChatColor.BOLD + "Iron Fragment");
		addLore(iron_fragment, ChatColor.WHITE, "A fragment of iron used in the");
		addLore(iron_fragment, ChatColor.WHITE, "creation of armor, weapons, and tools.");

		gold_fragment = ItemHandler.setDisplayName(new ItemStack(Material.INK_SACK, 1, (byte) 11), ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Fragment");
		addLore(gold_fragment, ChatColor.YELLOW, "A fragment of gold used in the");
		addLore(gold_fragment, ChatColor.YELLOW, "creation of embroidered armor,");
		addLore(gold_fragment, ChatColor.YELLOW, "weapons, and tools.");

		crystal_fragment = ItemHandler.setDisplayName(new ItemStack(Material.INK_SACK, 1, (byte) 12), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Blue Crystal Fragment");
		addLore(crystal_fragment, ChatColor.AQUA, "A fragment of blue crystal used in the");
		addLore(crystal_fragment, ChatColor.AQUA, "creation of embroidered armor,");
		addLore(crystal_fragment, ChatColor.AQUA, "weapons, and tools.");
		
		slime_core = ItemHandler.setDisplayName(new ItemStack(Material.SLIME_BALL), ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Slime Core");
		addLore(slime_core, ChatColor.GREEN, "A ball of slime containing");
		addLore(slime_core, ChatColor.GREEN, "the life energy of a fallen");
		addLore(slime_core, ChatColor.GREEN, "gelatinous cube.");
		
		enchanted_slime_core = ItemHandler.setDisplayName(new ItemStack(Material.MAGMA_CREAM), ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Enchanted Slime Core");
		addLore(enchanted_slime_core, ChatColor.GREEN, "A ball of slime containing");
		addLore(enchanted_slime_core, ChatColor.GREEN, "the life energy of a fallen");
		addLore(enchanted_slime_core, ChatColor.GREEN, "magical gelatinous cube.");

		die = SkullCreator.getSkullFromHash(ChatColor.GRAY + "" + ChatColor.BOLD + "Damage Die", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19");
		addLore(die, ChatColor.WHITE, "A 20 sided die used");
		addLore(die, ChatColor.WHITE, "for damage calculations.");
		
		pouch = ItemHandler.setDisplayName(new ItemStack(Material.INK_SACK), ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Small Pouch");
		addLore(pouch, ChatColor.GRAY, "A small storage pouch");
		addLore(pouch, ChatColor.GRAY, "used to store ore");
		addLore(pouch, ChatColor.GRAY, "fragments and shards.");
		addLore(pouch, ChatColor.GRAY, "Contents: (Empty)");
		
		chestskull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		chestskull = SkullCreator.getSkullFromHash(ChatColor.GOLD + "" + ChatColor.BOLD + "Loot Box", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY2OGQ1MDliNWQxNjY5Yjk3MWRkMWQ0ZGYyZTQ3ZTE5YmNiMWIzM2JmMWE3ZmYxZGRhMjliZmM2ZjllYmYifX19");
		addLore(chestskull, ChatColor.YELLOW, "A small loot box which");
		addLore(chestskull, ChatColor.YELLOW, "contains the contents");
		addLore(chestskull, ChatColor.YELLOW, "of your fallen enemy.");
		
		minor_potion = ItemHandler.setDisplayName(new ItemStack(Material.POTION), ChatColor.DARK_RED + "" + ChatColor.BOLD + "Minor Potion Of Healing");
		PotionMeta potion = (PotionMeta) minor_potion.getItemMeta();
		potion.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		minor_potion.setItemMeta(potion);
		addLore(minor_potion, ChatColor.RED, "A minor potion that restores 20 health.");
		ItemHandler.addFlag(minor_potion, ItemFlag.HIDE_POTION_EFFECTS);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		is_ofthesky = ItemHandler.setDisplayName(new ItemStack(Material.EMPTY_MAP), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Incantation Scroll Of Sky Walking");
		addLore(is_ofthesky, ChatColor.LIGHT_PURPLE, "A magical incantation that if applied to");
		addLore(is_ofthesky, ChatColor.LIGHT_PURPLE, "boots gifts the wearer with levitation.");
		is_ofthesky = ItemHandler.addFlag(is_ofthesky, ItemFlag.HIDE_ATTRIBUTES);
		
		bag_of_holding = SkullCreator.getSkullFromHash(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Bag Of Holding", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmNlYjU0MDEwNGU3YmJmM2QxM2YxNTI1YWNiNzEwZTNhZDM2NWQwY2IxMjkxYWQxZjc0OGY2MDJiNzdkMyJ9fX0=");
		addLore(bag_of_holding, ChatColor.LIGHT_PURPLE, "A small bag that feels");
		addLore(bag_of_holding, ChatColor.LIGHT_PURPLE, "almost endless internally.");

		magic_carpet = ItemHandler.setDisplayName(new ItemStack(Material.RABBIT_HIDE), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Magic Carpet");
		addLore(magic_carpet, ChatColor.LIGHT_PURPLE, "A seemingly normal");
		addLore(magic_carpet, ChatColor.LIGHT_PURPLE, "looking carpet that");
		addLore(magic_carpet, ChatColor.LIGHT_PURPLE, "has the ability to fly.");

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		action_tome = ItemHandler.setDisplayName(new ItemStack(Material.ENCHANTED_BOOK), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Tome Of Action");
		addLore(action_tome, ChatColor.LIGHT_PURPLE, "A tome used to control");
		addLore(action_tome, ChatColor.LIGHT_PURPLE, "simple movements.");

		action_clickfeet = ItemHandler.setDisplayName(new ItemStack(Material.RABBIT_FOOT), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Click Feet");
		addLore(action_clickfeet, ChatColor.LIGHT_PURPLE, "Activates boot abilities.");
		
		action_knockknee = ItemHandler.setDisplayName(new ItemStack(Material.BONE), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Knock Knees");
		addLore(action_knockknee, ChatColor.LIGHT_PURPLE, "Activates legging abilities.");

		action_shiver = ItemHandler.setDisplayName(new ItemStack(Material.RAW_FISH, 1, (byte) 3), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Shiver");
		addLore(action_shiver, ChatColor.LIGHT_PURPLE, "Activates chestplate abilities.");
		
		action_wink = ItemHandler.setDisplayName(new ItemStack(Material.EYE_OF_ENDER), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Wink");
		addLore(action_wink, ChatColor.LIGHT_PURPLE, "Activates helmet abilities.");
		
		action_punchknuckles = ItemHandler.setDisplayName(new ItemStack(Material.FLINT_AND_STEEL), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Punch Knuckles");
		addLore(action_punchknuckles, ChatColor.LIGHT_PURPLE, "Activates weapon abilities.");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		MENU_INSTANCE_BATTLES.setContents(new ItemStack[27]);

		MENU_CONTENTS.add(null);
		MENU_CONTENTS.add(instancebattle);
		MENU_CONTENTS.add(null);
		MENU_CONTENTS.add(items);
		MENU_CONTENTS.add(armor);
		MENU_CONTENTS.add(legendaries);
		MENU_CONTENTS.add(null);
		MENU_CONTENTS.add(bunkerworld);
		MENU_CONTENTS.add(null);
		
		MENU_ITEMS_CONTENTS.add(shard);
		MENU_ITEMS_CONTENTS.add(hide_strip);
		MENU_ITEMS_CONTENTS.add(stone_fragment);
		MENU_ITEMS_CONTENTS.add(coal_fragment);
		MENU_ITEMS_CONTENTS.add(iron_fragment);
		MENU_ITEMS_CONTENTS.add(gold_fragment);
		MENU_ITEMS_CONTENTS.add(crystal_fragment);
		MENU_ITEMS_CONTENTS.add(slime_core);
		MENU_ITEMS_CONTENTS.add(enchanted_slime_core);
		MENU_ITEMS_CONTENTS.add(die);
		MENU_ITEMS_CONTENTS.add(pouch);
		MENU_ITEMS_CONTENTS.add(chestskull);
		MENU_ITEMS_CONTENTS.add(action_tome);
		MENU_ITEMS_CONTENTS.add(minor_potion);
		
		addArmor(ArmorType.LEATHER, null, 1, false);
		addArmor(ArmorType.LEATHER, "Of The Skeleton", 4, true);

		MENU_LEGENDARIES_CONTENTS.add(is_ofthesky);
		MENU_LEGENDARIES_CONTENTS.add(bag_of_holding);
		MENU_LEGENDARIES_CONTENTS.add(magic_carpet);

		List<ItemStack> guns = new ArrayList<ItemStack>();
		List<ItemStack> magazines = new ArrayList<ItemStack>();
		List<ItemStack> ammos = new ArrayList<ItemStack>();

		for(GunType type : GunType.values()) {
			guns.add(type.getItemStack());
			if(type.hasMagazine()) magazines.add(type.getFullMagazine());
			if(!ammos.contains(type.getAmmo())) ammos.add(type.getAmmo());
		}
		
		for(ItemStack gun : guns) MENU_BUNKERWORLD_CONTENTS.add(gun);
		for(ItemStack magazine : magazines) MENU_BUNKERWORLD_CONTENTS.add(magazine);
		for(ItemStack ammo : ammos) MENU_BUNKERWORLD_CONTENTS.add(ammo);

		TOME_CONTENTS.add(null);
		TOME_CONTENTS.add(null);
		TOME_CONTENTS.add(action_clickfeet);
		TOME_CONTENTS.add(action_knockknee);
		TOME_CONTENTS.add(action_shiver);
		TOME_CONTENTS.add(action_wink);
		TOME_CONTENTS.add(action_punchknuckles);
		TOME_CONTENTS.add(null);
		TOME_CONTENTS.add(null);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		registerGui(MENU, MENU_CONTENTS);
		registerGui(MENU_ITEMS, MENU_ITEMS_CONTENTS, 27);
		registerGui(MENU_ARMOR, MENU_ARMOR_CONTENTS, 27);
		registerGui(MENU_LEGENDARIES, MENU_LEGENDARIES_CONTENTS, 27);
		registerGui(MENU_BUNKERWORLD, MENU_BUNKERWORLD_CONTENTS, 27);
		registerGui(TOME, TOME_CONTENTS);

	}
	
	public static void registerGui(Gui gui, List<ItemStack> contents) {
		gui.setContents(new ItemStack[contents.size()]);
		for(int i = 0; i < contents.size(); i++) gui.getContents()[i] = contents.get(i);
	}
	
	public static void registerGui(Gui gui, List<ItemStack> contents, int size) {
		for(int i = 0; i < contents.size(); i++) gui.setItem(i, contents.get(i));
		gui.setItem(size - 1, back);
	}
	
	public static void addLore(ItemStack stack, ChatColor color, String lore) {
		stack = ItemHandler.appendLore(stack, color + "" + ChatColor.ITALIC + lore);
	}
	
	public static void addArmor(ArmorType type, String name, int defenceScale, boolean incant) {
		
		String add = name != null ? " " + name : "";
		
		ArmorValue helmet = new ArmorValue(ItemHandler.setDisplayName(type.helmet, type.color + "" + ChatColor.BOLD + type.name + " Helmet" + add), defenceScale, defenceScale * 2, incant, type.subColor);
		ArmorValue chestplate = new ArmorValue(ItemHandler.setDisplayName(type.chestplate, type.color + "" + ChatColor.BOLD + type.name + " Chestplate" + add), defenceScale * 8, defenceScale * 16, incant, type.subColor);
		ArmorValue leggings = new ArmorValue(ItemHandler.setDisplayName(type.leggings, type.color + "" + ChatColor.BOLD + type.name + " Leggings" + add), defenceScale * 4, defenceScale * 8, incant, type.subColor);
		ArmorValue boots = new ArmorValue(ItemHandler.setDisplayName(type.boots, type.color + "" + ChatColor.BOLD + type.name+ " Boots" + add), defenceScale, defenceScale * 2, incant, type.subColor);

		MENU_ARMOR_VALUES.add(helmet);
		MENU_ARMOR_VALUES.add(chestplate);
		MENU_ARMOR_VALUES.add(leggings);
		MENU_ARMOR_VALUES.add(boots);
		
		MENU_ARMOR_CONTENTS.add(helmet.getDisplay());
		MENU_ARMOR_CONTENTS.add(chestplate.getDisplay());
		MENU_ARMOR_CONTENTS.add(leggings.getDisplay());
		MENU_ARMOR_CONTENTS.add(boots.getDisplay());

	}

}
