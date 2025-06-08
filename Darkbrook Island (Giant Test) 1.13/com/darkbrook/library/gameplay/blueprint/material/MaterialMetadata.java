package com.darkbrook.library.gameplay.blueprint.material;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.darkbrook.library.gameplay.tileentity.CommandBlock;

public enum MaterialMetadata
{
	
	COMMAND_BLOCK(block -> new CommandBlock(block).getCommand(), (block, metadata) -> new CommandBlock(block).setCommand(metadata), Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK);
	
	private static final Map<Material, MaterialMetadata> materialMapping = new HashMap<Material, MaterialMetadata>();

	private Material[] materials;
	private Function<Block, String> function;
	private BiConsumer<Block, String> consumer;
	
	private MaterialMetadata(Function<Block, String> function, BiConsumer<Block, String> consumer, Material... materials)
	{
		this.materials = materials;
		this.function = function;
		this.consumer = consumer;
	}
	
	public void setMetadata(Block block, String metadata)
	{
		consumer.accept(block, new String(Base64.getDecoder().decode(metadata.getBytes())));
	}
	
	public String getMetadata(Block block)
	{
		return Base64.getEncoder().encodeToString(function.apply(block).getBytes());
	}
	
	public static MaterialMetadata fromMaterial(Material material)
	{
		if(materialMapping.isEmpty()) for(MaterialMetadata metadata : values()) for(Material materialKey : metadata.materials)
		{
			materialMapping.put(materialKey, metadata);
		}
		
		return materialMapping.get(material);
	}

}
