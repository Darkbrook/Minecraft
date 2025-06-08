package com.darkbrook.prospector.init;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.darkbrook.prospector.block.BlockBlackSand;
import com.darkbrook.prospector.block.BlockBlackSandLayer;
import com.darkbrook.prospector.block.BlockPyriteOre;
import com.darkbrook.prospector.block.BlockSluiceBox;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProspectorBlocks
{
	private static final Map<String, Block> blockMap = Maps.newLinkedHashMap();
	private static final Set<BlockSluiceBox> sluiceBoxes = Sets.newLinkedHashSet();

	public static final Block pyrite_ore = register("pyrite_ore", new BlockPyriteOre());
	public static final Block black_sand_layer = register("black_sand_layer", new BlockBlackSandLayer());
	public static final Block black_sand = register("black_sand", new BlockBlackSand());	
	public static final Block oak_sluice_box = registerSluiceBox("oak");
	public static final Block spruce_sluice_box = registerSluiceBox("spruce");
	public static final Block birch_sluice_box = registerSluiceBox("birch");
	public static final Block jungle_sluice_box = registerSluiceBox("jungle");
	public static final Block acacia_sluice_box = registerSluiceBox("acacia");
	public static final Block dark_oak_sluice_box = registerSluiceBox("dark_oak");
	
	private static Block registerSluiceBox(String variant)
	{
		BlockSluiceBox block = new BlockSluiceBox(variant);
		sluiceBoxes.add(block);
		return register(variant + "_sluice_box", block);
	}
	
	private static Block register(String name, Block block)
	{
		block.setUnlocalizedName(name).setCreativeTab(ProspectorTabs.all);
		blockMap.put(name, block);
		return block;
	}
	
	public static void register()
	{
		for (Entry<String, Block> entry : blockMap.entrySet())
			GameRegistry.registerBlock(entry.getValue(), entry.getKey());
	}
	
	public static Set<BlockSluiceBox> getSluiceBoxes()
	{
		return Sets.newLinkedHashSet(sluiceBoxes);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModels() 
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		for (Block block : blockMap.values())
			mesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
