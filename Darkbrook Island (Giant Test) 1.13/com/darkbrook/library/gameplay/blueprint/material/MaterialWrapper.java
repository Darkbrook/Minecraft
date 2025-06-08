package com.darkbrook.library.gameplay.blueprint.material;

import org.bukkit.Material;

public class MaterialWrapper
{
	
	private Material material;
	
	public MaterialWrapper(Material material)
	{
		this.material = material;
	}

	public Material getMaterial()
	{
		return material;
	}

}
