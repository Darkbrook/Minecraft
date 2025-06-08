package com.darkbrook.prospector.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class BlockBlackSand extends BlockFalling
{	
	public BlockBlackSand()
	{
		super(Material.sand);
		setHardness(0.5f);
		setStepSound(soundTypeSand);
		setHarvestLevel("shovel", 0);
	}
}
