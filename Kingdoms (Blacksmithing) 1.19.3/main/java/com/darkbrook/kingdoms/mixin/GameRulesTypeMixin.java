package com.darkbrook.kingdoms.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.GameRules.Rule;
import net.minecraft.world.GameRules.Type;

@Mixin(Type.class)
abstract class GameRulesTypeMixin<T extends Rule<T>>
{
   @Shadow @Final @Mutable
   private Function<Type<T>, T> ruleFactory;
}
