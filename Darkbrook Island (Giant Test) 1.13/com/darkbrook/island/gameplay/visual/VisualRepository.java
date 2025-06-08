package com.darkbrook.island.gameplay.visual;

import org.bukkit.Particle;
import org.bukkit.Sound;

import com.darkbrook.library.gameplay.visual.DarkbrookParticle;
import com.darkbrook.library.gameplay.visual.DarkbrookSound;

public class VisualRepository 
{
	public static final DarkbrookSound soundOpenClose = new DarkbrookSound(Sound.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
	public static final DarkbrookSound soundClick = new DarkbrookSound(Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
	public static final DarkbrookSound errorSound = new DarkbrookSound(Sound.ENTITY_ARROW_HIT, 0.5F, 2.0F);
	public static final DarkbrookSound messageSound = new DarkbrookSound(Sound.ENTITY_HORSE_GALLOP, 0.5F, 0.0F);	
	public static final DarkbrookSound tableInteract = new DarkbrookSound(Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
	public static final DarkbrookSound tableHit = new DarkbrookSound(Sound.BLOCK_ANVIL_LAND, 1.0F, 2.0F);
	public static final DarkbrookSound bossMusic = new DarkbrookSound(Sound.MUSIC_NETHER, 2.0F, 2.0F);
	public static final DarkbrookSound growl = new DarkbrookSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 8.0F, 0.0F);
	public static final DarkbrookSound groundCrack = new DarkbrookSound(Sound.BLOCK_CHORUS_FLOWER_DEATH, 4.0F, 1.0F);
	public static final DarkbrookSound groundCrash = new DarkbrookSound(Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 4.0F, 0.0F);
	
	public static final DarkbrookParticle explosionFlame = new DarkbrookParticle(Particle.FLAME, 0.2F, 0.2F, 1000, false, true);
	public static final DarkbrookParticle crash = new DarkbrookParticle(Particle.MOB_APPEARANCE, 0, 0, Integer.MAX_VALUE, true, false);
}
