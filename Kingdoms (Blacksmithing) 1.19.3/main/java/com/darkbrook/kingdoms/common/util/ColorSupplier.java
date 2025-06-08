package com.darkbrook.kingdoms.common.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public interface ColorSupplier
{
	default MutableText toText()
	{
		return on(toString());
	}
	
	default MutableText on(String format, Object... args)
	{
		return on(String.format(format, args));
	}
	
	default MutableText on(String text)
	{
		return Text.literal(text).setStyle(Style.EMPTY.withItalic(false).withColor(getColor().getColorValue()));
	}

	Color getColor();
}
