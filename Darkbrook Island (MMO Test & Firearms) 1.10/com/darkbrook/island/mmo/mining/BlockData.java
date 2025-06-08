package com.darkbrook.island.mmo.mining;

import org.bukkit.Material;

public class BlockData {

	public Material material;
	public int data;
	public int cooldown;
	
	public BlockData(Material material, int data, int cooldown) {
		this.material = material;
		this.data = data;
		this.cooldown = cooldown;
	}

}
