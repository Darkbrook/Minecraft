package com.darkbrook.prospector.init;

import com.darkbrook.prospector.Prospector;
import com.darkbrook.prospector.client.gui.GuiSluiceBox;
import com.darkbrook.prospector.tileentity.TileEntitySluiceBox;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProspectorGuis implements IGuiHandler
{
	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));

		switch (id)
		{
			case 0:
				return ((TileEntitySluiceBox) entity).createContainer(player.inventory, player);
		}
		
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Gui getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		
		switch (id)
		{
			case 0:
				return new GuiSluiceBox((TileEntitySluiceBox) entity, player.inventory);
		}
		
		return null;
	}
	
	public static void open(EnumGui gui, World world, BlockPos pos, EntityPlayer player)
	{
		player.openGui(Prospector.instance, gui.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	public static enum EnumGui
	{
		SLUICE_BOX;
	}
	
	public static void register()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Prospector.instance, new ProspectorGuis());
	}
}
