package com.darkbrook.prospector.init;

import static com.darkbrook.prospector.init.ProspectorBlocks.pyrite_ore;
import static com.darkbrook.prospector.init.ProspectorItems.gold_chunk;
import static com.darkbrook.prospector.init.ProspectorItems.gold_flake;
import static com.darkbrook.prospector.init.ProspectorItems.gold_pan;
import static com.darkbrook.prospector.init.ProspectorItems.gold_pan_pyrite_dust;
import static com.darkbrook.prospector.init.ProspectorItems.pyrite_dust;
import static net.minecraft.init.Blocks.wooden_slab;
import static net.minecraft.init.Items.gold_ingot;
import static net.minecraft.init.Items.gold_nugget;
import static net.minecraft.init.Items.iron_ingot;
import static net.minecraft.init.Items.stick;

import com.darkbrook.prospector.block.BlockSluiceBox;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProspectorRecipes 
{
	public static void register()
	{
		addShapedRecipe(new ItemStack(gold_pan), " #", "##", '#', iron_ingot);
		addShapelessRecipe(new ItemStack(gold_flake, 3), gold_nugget);
		addShapelessRecipe(new ItemStack(gold_flake, 9), gold_chunk);
		addShapelessRecipe(new ItemStack(gold_nugget), gold_flake, gold_flake, gold_flake);
		addShapelessRecipe(new ItemStack(gold_chunk), gold_nugget, gold_nugget, gold_nugget);
		addShapedRecipe(new ItemStack(gold_chunk), "###", "###", "###", '#', gold_flake);
		addShapelessRecipe(new ItemStack(gold_ingot), gold_chunk, gold_chunk, gold_chunk);
		addShapelessRecipe(new ItemStack(gold_pan_pyrite_dust, 1, gold_pan_pyrite_dust.getMaxDamage()), gold_pan, pyrite_dust, pyrite_dust, pyrite_dust);

		for (BlockSluiceBox block : ProspectorBlocks.getSluiceBoxes())
			addShapedRecipe(new ItemStack(block), " /#", "/#/", "#/ ", '#', new ItemStack(wooden_slab, 1, BlockPlanks.EnumType.valueOf(block.getVariant().toUpperCase()).getMetadata()), '/', stick);
		
		addSmeltingRecipe(pyrite_ore, new ItemStack(pyrite_dust), 0.15f);
	}
	
	public static void addShapedRecipe(ItemStack output, Object... components)
	{
		GameRegistry.addShapedRecipe(output, components);
	}
	
	private static void addShapelessRecipe(ItemStack output, Object... components)
	{
		GameRegistry.addShapelessRecipe(output, components);
	}
	
	private static void addSmeltingRecipe(Block input, ItemStack output, float xp)
	{
		GameRegistry.addSmelting(input, output, xp);
	}
}
