package com.darkbrook.kingdoms.server.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.darkbrook.kingdoms.common.item.ItemStackConvertable;
import com.darkbrook.kingdoms.common.item.ItemStackData;
import com.darkbrook.kingdoms.common.item.nbt.CustomModelData;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class KingdomsItem implements ItemStackConvertable, ItemStackData
{
	protected static final String KINGDOMS_KEY = "kingdoms";
	
	private final String name;
	private final Item item;
	private final Data data;

	public KingdomsItem(String name, Item item, Data data)
	{
		this.name = name;
		this.item = item;
		this.data = data;
	}
		
	public String getName()
	{
		return name;
	}

	public Item getItem()
	{
		return item;
	}
	
	@Override
	public ItemStack asStack()
	{
		return createStack(1);
	}

	public ItemStack createStack(int count, ItemStackData... data)
	{
		ItemStack stack = new ItemStack(item, count);
		write(stack);
		
		for (ItemStackData child : data)
			child.write(stack);
		
		return stack;
	}

	@Override
	public void write(ItemStack stack)
	{
		NbtCompound compound = stack.getOrCreateNbt();
		compound.putString(KINGDOMS_KEY, name);
		data.write(stack);
	}
	
	public static class Data implements ItemStackData
	{
		private final List<ItemStackData> data;
		
		private Data(ItemStackData... data)
		{
			this.data = new ArrayList<>(Arrays.asList(data));
		}
		
		@Override
		public void write(ItemStack stack)
		{
			for (ItemStackData child : data)
				child.write(stack);		
		}
				
		public void add(ItemStackData data)
		{
			this.data.add(data);
		}
		
		public void setTextureSupplier(Supplier<CustomModelData> textureSupplier)
		{
			if (this instanceof TexturedData)
				((TexturedData) this).textureSupplier = textureSupplier;
		}
		
		public static Data empty()
		{
			return of();
		}
		
		public static Data of(ItemStackData... data)
		{
			return new Data(data);
		}
				
		public static Data textured(ItemStackData... data)
		{
			return new TexturedData(data);
		}
	}
	
	private static class TexturedData extends Data
	{
		private Supplier<CustomModelData> textureSupplier;

		private TexturedData(ItemStackData... data)
		{
			super(data);
		}

		@Override
		public void write(ItemStack stack)
		{
			textureSupplier.get().write(stack);
			super.write(stack);
		}
	}
}
