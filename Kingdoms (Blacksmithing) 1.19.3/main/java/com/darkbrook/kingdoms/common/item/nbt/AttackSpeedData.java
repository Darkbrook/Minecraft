package com.darkbrook.kingdoms.common.item.nbt;

import java.util.UUID;

import com.darkbrook.kingdoms.common.item.ItemStackData;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

public class AttackSpeedData implements ItemStackData
{
	private static final UUID ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
	
	private final EntityAttributeModifier modifier;
	
	public AttackSpeedData(int ticks)
	{
		modifier = new EntityAttributeModifier(ATTACK_SPEED_UUID, "generic.attack_speed", 20.0d / ticks - 4.0d,
				Operation.ADDITION);
	}
	
	@Override
	public void write(ItemStack stack)
	{
		stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, modifier, EquipmentSlot.MAINHAND);
	}
}
