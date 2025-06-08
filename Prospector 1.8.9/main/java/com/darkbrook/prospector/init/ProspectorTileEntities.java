package com.darkbrook.prospector.init;

import com.darkbrook.prospector.tileentity.TileEntitySluiceBox;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProspectorTileEntities
{
	public static void register()
	{
		registerTileEntity("sluice_box", TileEntitySluiceBox.class);
	}
	
	private static void registerTileEntity(String name, Class<? extends TileEntity> clazz)
	{
		GameRegistry.registerTileEntity(clazz, name);
	}
}
