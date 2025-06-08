package com.darkbrook.island.common.registry.command;

public interface ICommandProfile<U, A, L, R>
{
	public R apply(U user, A arguments, L length);
}
