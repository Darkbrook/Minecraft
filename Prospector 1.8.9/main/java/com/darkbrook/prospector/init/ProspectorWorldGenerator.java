package com.darkbrook.prospector.init;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProspectorWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider generator, IChunkProvider provider)
	{		
		if (world.provider.getDimensionId() != 0)
			return;
		
		int x = chunkX * 16;
		int z = chunkZ * 16;
		
		generate(random, x, z, world, ProspectorBlocks.pyrite_ore, Blocks.stone, 0, 63, 8, 8);
		generate(random, x, z, world, ProspectorBlocks.black_sand, Blocks.sand, 61, 61, 32, 2, BiomeGenBase.river);
	}
	
	protected void generate(Random random, int x, int z, World world, Block block, Block predicate, int minY, int maxY, int maxVein, int chance)
	{		
		WorldGenMinable generator = new WorldGenMinable(block.getDefaultState(), maxVein, BlockHelper.forBlock(predicate));

		for (int i = 0; i < chance; i++)
			generator.generate(world, random, new BlockPos(random.nextInt(16) + x, random.nextInt(maxY - minY + 1) + minY, random.nextInt(16) + z));
	}
	
	protected void generate(Random random, int x, int z, World world, Block block, Block predicate, int minY, int maxY, int maxVein, int chance, BiomeGenBase biome)
	{		
		WorldGenMinable generator = new WorldGenMinable(block.getDefaultState(), maxVein, BlockHelper.forBlock(predicate));

		for (int i = 0; i < chance; i++)
		{
			BlockPos pos = new BlockPos(random.nextInt(16) + x, random.nextInt(maxY - minY + 1) + minY, random.nextInt(16) + z);
			
			if (world.getBiomeGenForCoords(pos) == biome)
				generator.generate(world, random, pos);
		}
	}
	
	public static void register()
	{
    	GameRegistry.registerWorldGenerator(new ProspectorWorldGenerator(), 32);
	}
}
