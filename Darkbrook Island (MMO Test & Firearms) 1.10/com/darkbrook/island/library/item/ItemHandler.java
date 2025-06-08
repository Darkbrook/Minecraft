package com.darkbrook.island.library.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.darkbrook.island.References;
import com.darkbrook.island.library.config.Config;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagString;

public class ItemHandler {
	
	public static void registerItemActivator(ItemActivator activator) {
		References.plugin.getServer().getPluginManager().registerEvents(activator, References.plugin);
	}
	
	public static ItemStack setDisplayName(ItemStack stack, String name) {
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static String getDisplayName(ItemStack stack) {
		if(stack == null || stack.getType() == Material.AIR) return "";
		ItemMeta meta = stack.getItemMeta();
		return meta.hasDisplayName() ? meta.getDisplayName() : stack.getType().name();
	}
	
	public static boolean compareDisplayNames(ItemStack stack, String name) {
		return getDisplayName(stack).equals(name);
	}
	
	public static boolean compareDisplayNames(ItemStack stack0, ItemStack stack1) {
		return getDisplayName(stack0).equals(getDisplayName(stack1));
	}
	
	public static ItemStack setLore(ItemStack stack, List<String> lorelist) {
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(lorelist);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack setLore(ItemStack stack, String lore, int line) {
		List<String> list = getLore(stack);
		list.set(line, lore);
		return setLore(stack, list);
	}
	
	public static List<String> getLore(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		return meta.hasLore() ? meta.getLore() : new ArrayList<String>();
	}
	
	public static ItemStack appendLore(ItemStack stack, String lore) {
		ItemMeta meta = stack.getItemMeta();
		List<String> lorelist = getLore(stack);
		lorelist.add(lore);
		meta.setLore(lorelist);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack replaceLore(ItemStack stack, String loreIn, String loreOut) {
		List<String> lorelist = getLore(stack);
		for(int i = 0; i < lorelist.size(); i++) if(lorelist.get(i).equals(loreIn)) lorelist.set(i, loreOut);
		stack = setLore(stack, lorelist);
		return stack;
	}
	
	public static String getLoreFromLine(ItemStack stack, int line) {
		return getLore(stack).size() > line ? getLore(stack).get(line) : null;
	}
	
	public static int getLineFromLore(ItemStack stack, String lore) {
		List<String> lorelist = getLore(stack);
		for(int i = 0; i < lorelist.size(); i++) if(lorelist.get(i).equals(lore)) return i;
		return -1;
	}
	
	public static int getLineFromLorePart(ItemStack stack, String lore) {
		List<String> lorelist = getLore(stack);
		for(int i = 0; i < lorelist.size(); i++) if(lorelist.get(i).contains(lore)) return i;
		return -1;
	}
	
	public static boolean hasLoreFromLine(ItemStack stack, String lore) {
		for(String s : getLore(stack)) if(s.equals(lore)) return true;
		return false;
	}
	
	public static int getIntFromLore(ItemStack stack, String loreIn) {
		int line = getLineFromLorePart(stack, loreIn);
		if(line == -1) return 0;
		String lore = getLoreFromLine(stack, line);
		return Integer.parseInt(lore.substring(lore.indexOf(loreIn) + loreIn.length()));
	}
	
	public static boolean canSubtractAmount(ItemStack stack, int amount) {
		return stack.getAmount() >= amount;
	}
	
	public static ItemStack subtractAmount(ItemStack stack, int amount) {
		if(!canSubtractAmount(stack, amount)) return stack;
		if(stack.getAmount() == amount) return null;
		stack.setAmount(stack.getAmount() - amount);
		return stack;
	}
	
	public static boolean canSubtractDurability(ItemStack stack, int amount) {
		return stack.getDurability() + amount < stack.getType().getMaxDurability();
	}
	
	public static ItemStack subtractDurability(ItemStack stack, int amount) {
		
		if(canSubtractDurability(stack, amount)) {
			stack.setDurability((short) (stack.getDurability() + amount));
			return stack;
		} 			
		
		return subtractAmount(stack, 1);
	}
	
	public static boolean canAddDurability(ItemStack stack, int amount) {
		return stack.getDurability() - amount >= 0;
	}
	
	public static ItemStack addDurability(ItemStack stack, int amount) {
		
		if(canAddDurability(stack, amount)) {
			stack.setDurability((short) (stack.getDurability() - amount));
			return stack;
		}
		
		return stack;
	}
	
	public static float getDurabilityFloat(ItemStack stack) {
		return MathHandler.getFloatPercent(stack.getType().getMaxDurability() - stack.getDurability(), stack.getType().getMaxDurability());
	}
	
	public static boolean hasItem(Player player, ItemStack stack) {
		if(player.getInventory().getItemInOffHand().isSimilar(stack)) return true;
		for(int i = 0; i < 36; i++) if(player.getInventory().getItem(i) != null && player.getInventory().getItem(i).isSimilar(stack)) return true;
		return false;
	}
	
	public static void addItem(Player player, ItemStack stack) {
		
		int fullslots = 0;
		for(int i = 0; i < 36; i++) if(player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() != Material.AIR) fullslots++;
		
		if(fullslots < 36) {
			player.getInventory().addItem(stack);
		} else {
			Item item = player.getWorld().dropItem(player.getLocation(), stack);
			item.getVelocity().setY(-10);
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 2.0F, 2.0F);
			player.sendMessage(ChatColor.GRAY + "The item you have obtained was dropped beneath your feet.");
		}
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				player.updateInventory();
			}
			
		}, 1);
		
	}
	
	public static void setItem(Player player, int slot, ItemStack stack) {
		
		player.getInventory().setItem(slot, stack);
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				player.updateInventory();
			}
			
		}, 1);
		
	}
	
	public static ItemStack addFlag(ItemStack stack, ItemFlag flag) {
		ItemMeta meta = stack.getItemMeta();
		meta.addItemFlags(flag);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack removeFlag(ItemStack stack, ItemFlag flag) {
		ItemMeta meta = stack.getItemMeta();
		meta.removeItemFlags(flag);
		stack.setItemMeta(meta);
		return stack;
	}
	
	private static void addAttribute(NBTTagList modifiers, AttributeType attributeType, int amount, SlotType slotType) {
		
		NBTTagCompound attribute = new NBTTagCompound();
		attribute.set("AttributeName", new NBTTagString(attributeType.getAttribute()));
		attribute.set("Name", new NBTTagString(attributeType.getAttribute()));
		attribute.set("Amount", new NBTTagInt(amount));
		attribute.set("Operation", new NBTTagInt(0));
		attribute.set("UUIDMost", new NBTTagInt(attributeType.getUUID()));
		attribute.set("UUIDLeast", new NBTTagInt(attributeType.getUUID()));
		if(!slotType.getSlot().equals("all")) attribute.set("Slot", new NBTTagString(slotType.getSlot()));
		modifiers.add(attribute);
		
	}
	
	public static ItemStack addAttribute(ItemStack stack, AttributeType attributeType, int amount, SlotType slotType) {
		
		net.minecraft.server.v1_10_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		NBTTagCompound compound = nms.hasTag() ? nms.getTag() : new NBTTagCompound();
		NBTTagList modifiers = compound.hasKey("AttributeModifiers") ? (NBTTagList) compound.get("AttributeModifiers") : new NBTTagList();
		nms.setTag(compound);
	
		if(slotType.isSlot("hands")) {
			addAttribute(modifiers, attributeType, amount, SlotType.MAINHAND);
			addAttribute(modifiers, attributeType, amount, SlotType.OFFHAND);
		} else if(slotType.isSlot("armor")) {
			addAttribute(modifiers, attributeType, amount, SlotType.HELMET);
			addAttribute(modifiers, attributeType, amount, SlotType.CHESTPLATE);
			addAttribute(modifiers, attributeType, amount, SlotType.LEGGINGS);
			addAttribute(modifiers, attributeType, amount, SlotType.BOOTS);	
		} else {
			addAttribute(modifiers, attributeType, amount, slotType);
		}
		
		compound.set("AttributeModifiers", modifiers);
		stack = CraftItemStack.asBukkitCopy(nms);
		return stack;
		
	}
	
	public static int getAttributeAmount(ItemStack stack, AttributeType attributeType, SlotType slotType) {
		net.minecraft.server.v1_10_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		if(!nms.hasTag() || !nms.getTag().hasKey("AttributeModifiers")) return 0;
		
		NBTTagList modifiers  = (NBTTagList) nms.getTag().get("AttributeModifiers");
		
		for(int i = 0; i < modifiers.size(); i++) {
			NBTTagCompound attribute = modifiers.get(i);
			if(attribute.getString("Slot").equals(slotType.getSlot()) && attribute.getString("AttributeName").equals(attributeType.getAttribute())) return modifiers.get(i).getInt("Amount");			
		}
		
		return 0;
	}
	
	public static ItemStack enchant(ItemStack stack, Enchantment enchantment, int level, boolean show) {
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(enchantment, level, show);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack setLoggedContents(ItemStack stack, ItemStack[] contents, Config config) {
		int id = MathHandler.RANDOM.nextInt();
		id = id >= 0 ? id : id * -1;
		stack = ItemHandler.addAttribute(stack, AttributeType.LUCK, id, SlotType.HELMET);
		stack = ItemHandler.addFlag(stack, ItemFlag.HIDE_ATTRIBUTES);
		for(int i = 0; i < contents.length; i++) config.set("itemdata." + id + ".contents." + i, contents[i]);
		return stack;
	}

	public static int getLoggedId(ItemStack stack) {
		return getAttributeAmount(stack, AttributeType.LUCK, SlotType.HELMET);
	}
	
	public static ItemStack[] getLoggedContents(ItemStack stack, int size, Config config) {
		int id = getLoggedId(stack);
		if(id == 0) return null;
		ItemStack[] contents = new ItemStack[size];
		for(int i = 0; i < size; i++) if(config.contains("itemdata." + id + ".contents." + i)) contents[i] = config.getItemStack("itemdata." + id + ".contents." + i);
		config.set("itemdata." + id, null);
		return contents;
	}
	
}
