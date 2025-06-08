package com.darkbrook.kingdoms.common.nbt;

import java.util.List;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public interface NbtTextList extends List<NbtElement>
{
	default void addText(Text text)
	{
		addText(size(), text);
	}
	
	default void addText(int index, Text text)
	{
		addString(index, Text.Serializer.toJson(text));
	}
	
	default MutableText getText(int index)
	{
		return Text.Serializer.fromJson(getString(index));
	}
	
	default void setText(int index, Text text)
	{
		setString(index, Text.Serializer.toJson(text));
	}
	
	default void addString(String s)
	{
		addString(size(), s);
	}
				
	default void addString(int index, String s)
	{
		add(index, NbtString.of(s));
	}
	
	default String getString(int index)
	{
		return get(index).toString();
	}
	
	default void setString(int index, String s)
	{
		set(index, NbtString.of(s));
	}
}
