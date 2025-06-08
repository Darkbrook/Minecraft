package com.darkbrook.kingdoms;

import java.nio.file.Paths;

import com.darkbrook.kingdoms.common.io.GameRulesProperties;
import com.darkbrook.kingdoms.server.block.anvil.AnvilBlockEntity;
import com.darkbrook.kingdoms.server.block.forge.ForgeBlockEntity;
import com.darkbrook.kingdoms.server.command.KingdomsCommandManager;
import com.darkbrook.kingdoms.server.item.KingdomsStack;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class KingdomsServer implements DedicatedServerModInitializer
{	
	public static final BlockEntityType<AnvilBlockEntity> ANVIL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, 
			new Identifier("kingdoms", "anvil"), FabricBlockEntityTypeBuilder.create(AnvilBlockEntity::new, Blocks.ANVIL).build());
	
	public static final BlockEntityType<ForgeBlockEntity> FORGE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, 
			new Identifier("kingdoms", "forge"), FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new, Blocks.CAULDRON).build());

	private static KingdomsServer instance;

	@Override
	public void onInitializeServer()
	{
		instance = this;
		GameRulesProperties.createInstance(Paths.get("gamerules.properties"));
		KingdomsCommandManager.register();
		AnvilBlockEntity.registerEvents();
		KingdomsStack.registerEvents();
	}

	public static KingdomsServer getInstance()
	{
		return instance;
	}
}
