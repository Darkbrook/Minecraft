package com.darkbrook.library.util.helper;

import java.util.Map;
import java.util.function.Function;

import com.mojang.datafixers.types.Type;

import net.minecraft.server.v1_13_R1.DataConverterRegistry;
import net.minecraft.server.v1_13_R1.DataConverterTypes;
import net.minecraft.server.v1_13_R1.Entity;
import net.minecraft.server.v1_13_R1.EntityTypes;
import net.minecraft.server.v1_13_R1.World;

public class CustomEntityHelper
{
	
	@SuppressWarnings("unchecked")
	public static void register(Class<? extends Entity> custom, Function<? super World, ? extends Entity> function, String customIdentity, String parentIdentity)
	{
		Map<Object, Type<?>> types = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
		types.put("minecraft:" + customIdentity, types.get("minecraft:" + parentIdentity));
		EntityTypes.a(customIdentity, EntityTypes.a.a(custom, function));
	}

}
