package com.darkbrook.prospector.init;

import java.util.Map;
import java.util.Map.Entry;

import com.darkbrook.prospector.item.ItemGoldPan;
import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProspectorItems 
{
	private static final Map<String, Item> itemMap = Maps.newLinkedHashMap();

	public static final Item gold_pan = register("gold_pan", new ItemGoldPan.Empty());
	public static final Item gold_pan_black_sand = register("gold_pan_black_sand", new ItemGoldPan.Full(4));
	public static final Item gold_pan_pyrite_dust = register("gold_pan_pyrite_dust", new ItemGoldPan.Full(6));
	public static final Item pyrite_dust = register("pyrite_dust", new Item());
	public static final Item gold_flake = register("gold_flake", new Item());
	public static final Item gold_chunk = register("gold_chunk", new Item());

	private static Item register(String name, Item item)
	{
		item.setUnlocalizedName(name).setCreativeTab(ProspectorTabs.all);
		itemMap.put(name, item);
		return item;
	}
	
	public static void register()
	{
		for (Entry<String, Item> entry : itemMap.entrySet())
			GameRegistry.registerItem(entry.getValue(), entry.getKey());
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModels() 
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		for (Item item : itemMap.values())
			mesher.register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
