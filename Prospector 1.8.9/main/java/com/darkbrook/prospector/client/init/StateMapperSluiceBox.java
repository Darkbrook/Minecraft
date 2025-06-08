package com.darkbrook.prospector.client.init;

import com.darkbrook.prospector.block.BlockSluiceBox;
import com.darkbrook.prospector.init.ProspectorBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StateMapperSluiceBox extends StateMapperBase
{
	private static final StateMapperSluiceBox instance = new StateMapperSluiceBox();
	
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) 
	{
		return new ModelResourceLocation(state.getBlock().getRegistryName(), getPropertyString(state));
	}
	
	protected String getPropertyString(IBlockState state)
	{
		return String.format("facing=%s,half_running=%s_%s", state.getValue(BlockSluiceBox.FACING).getName(), 
				state.getValue(BlockSluiceBox.HALF).getName(), state.getValue(BlockSluiceBox.RUNNING));
	}
	
	public static void register()
	{
		for (Block block : ProspectorBlocks.getSluiceBoxes())
			ModelLoader.setCustomStateMapper(block, instance);
	}
}
