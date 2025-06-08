package com.darkbrook.prospector.block;

import java.util.Random;

import com.darkbrook.prospector.init.ProspectorBlocks;
import com.darkbrook.prospector.init.ProspectorItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockPyriteOre extends Block
{
	public BlockPyriteOre() 
	{
		super(Material.rock);
		setHardness(3.5f);
		setResistance(5.0f);
		setStepSound(soundTypePiston);
		setHarvestLevel("pickaxe", 1);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return ProspectorItems.pyrite_dust;
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return quantityDropped(random) + random.nextInt(fortune + 1);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return MathHelper.getRandomIntegerInRange(random, 3, 9);
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
	{
		return getItemDropped(world.getBlockState(pos), RANDOM, fortune) != Item.getItemFromBlock(this) ? RANDOM.nextInt(2) : 0;
	}
	
	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(ProspectorBlocks.pyrite_ore);
	}
}
