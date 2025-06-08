package com.darkbrook.prospector.item;

import com.darkbrook.prospector.block.BlockBlackSandLayer;
import com.darkbrook.prospector.block.BlockSluiceBox;
import com.darkbrook.prospector.init.ProspectorBlocks;
import com.darkbrook.prospector.init.ProspectorItems;
import com.darkbrook.prospector.item.crafting.PanningRecipes;
import com.darkbrook.prospector.item.crafting.PanningRecipes.PanningRecipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class ItemGoldPan extends Item
{	
	private ItemGoldPan()
	{
		maxStackSize = 1;
	}
	
	public static class Empty extends ItemGoldPan
	{
		@Override
	    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) 
	    {
			if (!player.canPlayerEdit(pos, side, stack))
				return false;
			
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			int layers = 4;
			
			if (block == ProspectorBlocks.black_sand)
				state = ProspectorBlocks.black_sand_layer.getDefaultState();
			else if (block == ProspectorBlocks.black_sand_layer)
				layers = state.getValue(BlockBlackSandLayer.LAYERS);
			else
				return false;
			
			if (--layers > 0)
				world.setBlockState(pos, state.withProperty(BlockBlackSandLayer.LAYERS, layers));
			else
				world.setBlockToAir(pos);
			
			stack.setItem(ProspectorItems.gold_pan_black_sand);
			stack.setItemDamage(stack.getMaxDamage());
			world.playSoundEffect(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, "dig.sand", 0.8f, 0.8f);
			return true;
	    }
	}
	
	public static class Full extends ItemGoldPan
	{
		public Full(int panningSeconds)
		{
			setMaxDamage((panningSeconds * 5) + 1);
			setNoRepair();
		}
		
		@Override
	    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) 
	    {
			if (world.getBlockState(pos.offset(side)).getBlock() != Blocks.water)
			{
				IBlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				
				if ((block != Blocks.cauldron || state.getValue(BlockCauldron.LEVEL) == 0) && !BlockSluiceBox.isRunning(state))
					return false;
			}
			
			if (stack.getItemDamage() <= 1 || player.capabilities.isCreativeMode)
			{				
				if (!world.isRemote)
				{
					PanningRecipe recipe = PanningRecipes.getInstance().getRecipe(stack);
					
					if (recipe != null)
						world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ProspectorItems.gold_flake, recipe.getAmount().nextInt(itemRand))));
				}

				stack.setItem(ProspectorItems.gold_pan);
			}
			
			stack.setItemDamage(stack.getItemDamage() - 1);
			
			if (stack.getItemDamage() % 4 != 0)
				return false;
			
			world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "game.player.swim.splash", 0.4f, 1.2f);
			return true;
	    }
	}
}
