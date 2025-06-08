package com.darkbrook.kingdoms.server.block.table;

import com.darkbrook.kingdoms.common.item.ItemStackConvertable;
import com.google.common.base.Function;

import net.minecraft.item.ItemStack;

public class TableRecipe<T extends TableBlockEntity<T>>
{
	private final Function<T, ItemStackConvertable> resultFunc;

	public TableRecipe(Function<T, ItemStackConvertable> resultFunc)
	{
		this.resultFunc = resultFunc;
	}
	
	public ItemStack getResultStack(T table)
	{
		return resultFunc.apply(table).asStack();
	}
}
