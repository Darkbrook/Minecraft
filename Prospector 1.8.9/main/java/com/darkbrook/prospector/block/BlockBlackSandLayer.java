package com.darkbrook.prospector.block;

import java.util.Random;

import com.darkbrook.prospector.init.ProspectorBlocks;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlackSandLayer extends BlockBlackSand
{
	public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 4);
	
	public BlockBlackSandLayer()
	{
		setDefaultState(blockState.getBaseState().withProperty(LAYERS, 1));
		setLightOpacity(255);
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) 
	{
		return state.getValue(LAYERS) == 4 ? Item.getItemFromBlock(ProspectorBlocks.black_sand) : null;
    }
	
	@Override
    protected void onStartFalling(EntityFallingBlock entity)
	{
		entity.shouldDropItem = entity.getBlock().getValue(LAYERS) == 4;
    }
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean isFullCube()
	{
	    return false;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
    {
		setBlockBoundsBasedOnState(state);
        return super.getCollisionBoundingBox(world, pos, state);
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) 
	{
	    setBlockBoundsBasedOnState(world.getBlockState(pos));
	}
	
	public void setBlockBoundsBasedOnState(IBlockState state)
	{
		if (state.getBlock() == this)		
			setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, state.getValue(LAYERS).intValue() * 0.25f, 1.0f);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(LAYERS, meta + 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) 
	{
	    return state.getValue(LAYERS).intValue() - 1;
	}
	
	@Override
	public BlockState createBlockState() 
	{
	    return new BlockState(this, LAYERS);
	}
}
