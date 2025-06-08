package com.darkbrook.library.gameplay.bossbar;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.craftbukkit.v1_13_R1.boss.CraftBossBar;
import org.bukkit.entity.LivingEntity;

public class EntityBossBarPreset
{
	
	private BarFlag[] flags;
	private BarColor color;
	private BarStyle style;
	private String title;
	private double radius;
	
	public EntityBossBarPreset(String title, double radius, BarColor color, BarStyle style, BarFlag... flags)
	{
		this.flags = flags;
		this.color = color;
		this.style = style;
		this.title = title;
		this.radius = radius;
	}
	
	public EntityBossBar createBossBar(LivingEntity entity)
	{
		return new EntityBossBar(entity, new CraftBossBar(title, color, style, flags), radius);
	}
	
}
