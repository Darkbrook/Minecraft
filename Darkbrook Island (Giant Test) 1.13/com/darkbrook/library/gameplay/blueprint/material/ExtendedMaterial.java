package com.darkbrook.library.gameplay.blueprint.material;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ExtendedMaterial extends MaterialWrapper
{

	private MaterialMetadata metadata;
	private String materialKey;
	private String metadataKey;
	
	public ExtendedMaterial(Block block)
	{
		super(block.getType());
		loadMetadata(block);
	}
	
	@Override
	public String toString()
	{
		return materialKey;
	}

	public void setBlockAt(Location location)
	{
		
		Block block = location.getBlock();
		block.setType(getMaterial());
		
		if(metadata != null)
		{
			metadata.setMetadata(block, metadataKey);
		}
		
	}
	
	private void loadMetadata(Block block)
	{
		
		Material material = getMaterial();
		
		metadata = MaterialMetadata.fromMaterial(material);
		materialKey = material.toString();
		
		if(metadata != null)
		{
			materialKey += ":" + (metadataKey = metadata.getMetadata(block));
		}
		
	}

}
