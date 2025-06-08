package dev.darkbrook.wildfire.mixin.fullbright;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin extends EntityLivingBase
{
	@Unique
	private static final PotionEffect INFINITE_NIGHT_VISION = new PotionEffect(
			MobEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false);

	public EntityPlayerSPMixin(World world)
	{
		super(world);
	}

	@Override
	public boolean isPotionActive(Potion potion)
	{
		return isBrightenPotion(potion) || super.isPotionActive(potion);
	}

	@Override
	public PotionEffect getActivePotionEffect(Potion potion)
	{
		return isBrightenPotion(potion) ? INFINITE_NIGHT_VISION
				: super.getActivePotionEffect(potion);
	}

	@Unique
	private static boolean isBrightenPotion(Potion potion)
	{
		return potion == MobEffects.NIGHT_VISION && Wildfire.getInstance().getOptions().useNightVisionFullbright();
	}
}
