package com.darkbrook.island.mmo.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.EntityTypes;

public class EntityHandler {

	public static void register(String name, int id, Class<? extends EntityInsentient> clazz) {			
		
		try {
			
			List<Map<?, ?>> map = new ArrayList<Map<?, ?>>();
			for(Field f : EntityTypes.class.getDeclaredFields()) {
				if(f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					map.add((Map<?,?>) f.get(null));
				}
			}
			
			if(map.get(2).containsKey(id)) {
				map.get(0).remove(name);
				map.get(2).remove(id);
			}
			
			Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			method.setAccessible(true);
			method.invoke(null, clazz, name, id);
			
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
			
	}
	
}
