package com.darkbrook.kingdoms.mixin;

import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.GameRules.Rule;
import net.minecraft.world.GameRules.Type;

@Mixin(Type.class)
public interface GameRulesTypeAccessor
{    
    @Accessor("ruleFactory")
    <T extends Rule<T>> void setRuleFactory(Function<Type<T>, T> ruleFactory);
}
