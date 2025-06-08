package com.darkbrook.island;

import com.darkbrook.apoc.ApocListener;
import com.darkbrook.apoc.BlockListener;
import com.darkbrook.apoc.GenericListener;
import com.darkbrook.apoc.armor.ArmorListener;
import com.darkbrook.apoc.entity.EntityApocGiant;
import com.darkbrook.apoc.entity.EntityApocGiantListener;
import com.darkbrook.island.command.CommandBind;
import com.darkbrook.island.command.CommandCrash;
import com.darkbrook.island.command.CommandGm;
import com.darkbrook.island.command.CommandGui;
import com.darkbrook.island.command.CommandIdentify;
import com.darkbrook.island.command.CommandSee;
import com.darkbrook.island.gameplay.gui.GuiChatColor;
import com.darkbrook.island.gameplay.gui.GuiGamerule;
import com.darkbrook.island.gameplay.gui.GuiWorld;
import com.darkbrook.library.plugin.DarkbrookPlugin;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.helper.CustomEntityHelper;
import com.darkbrook.library.util.helper.ItemHelper;

import net.minecraft.server.v1_13_R1.Items;

public class DarkbrookIsland extends DarkbrookPlugin 
{
		
	@Override
	public void onEnable() 
	{
		super.onEnable();
		
		register(new IRegistryValue[]
		{
			new CommandBind(),
			new CommandGui("chatcolor", GuiChatColor.class, false),
			new CommandCrash(),
			new CommandGui("gamerule", GuiGamerule.class, true),
			new CommandGm(),
			new CommandGui("gui", null, false),
			new CommandIdentify(),
			new CommandSee(),
			new CommandGui("world", GuiWorld.class, true),
			new ApocListener(),
			new ArmorListener(),
			new BlockListener(),
			new EntityApocGiantListener(),
			new GenericListener()
		});
		
		ItemHelper.setMaxStackSize(Items.ARROW, 16);
		CustomEntityHelper.register(EntityApocGiant.class, EntityApocGiant::new, "apoc_giant", "giant");
	}
	
}
