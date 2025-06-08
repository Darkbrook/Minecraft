package com.darkbrook.library.entity;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Potion {
	
	private List<PotionEffect> effects;
	private PotionEffectType type;
	private int amplifier;
	private int duration;
	
	public Potion(ItemStack stack) {
		
		try {
			PotionData data = ((PotionMeta) stack.getItemMeta()).getBasePotionData();
			effects = ((PotionMeta) stack.getItemMeta()).getCustomEffects();
			type = setPotionEffectType(data);
			amplifier = setAmplifier(data);
			duration = setDuration(data);
		} catch (Exception e) {}
		
	}
	
	public Potion(PotionEffectType type, int amplifier, int duration) {
		this.type = type;
		this.amplifier = amplifier;
		this.duration = duration;
	}
	
	private PotionEffectType setPotionEffectType(PotionData data) {
		return data.getType().getEffectType();
	}
	
	private int setAmplifier(PotionData data) {
		return data.isUpgraded() ? 1 : 0;
	}

	private int setDuration(PotionData data) {
		PotionType type = data.getType();
		if(type.isInstant()) return 1;
		double multiplier = type.getEffectType().getDurationModifier();
		double tick = amplifier == 0 ? (data.isExtended() ? 9600 : 3600) : 1800;
		return (int) (tick * multiplier);
	}
	
	public void apply(LivingEntity entity) {
		apply(entity, new PotionEffect(type, duration, amplifier));
		for(PotionEffect effect : effects) apply(entity, effect);
	}
	
	private void apply(LivingEntity entity, PotionEffect effect) {
		entity.removePotionEffect(effect.getType());
		entity.addPotionEffect(effect);
	}

	public PotionEffectType getType() {
		return type;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public int getDuration() {
		return duration;
	}

}
