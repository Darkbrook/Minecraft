package com.darkbrook.kingdoms.common.util;

import net.minecraft.util.Formatting;

public enum Color implements ColorSupplier
{
	BLACK,
	DARK_BLUE,
	DARK_GREEN,
	DARK_AQUA,
	DARK_RED,
	DARK_PURPLE,
	GOLD,
	GRAY,
	DARK_GRAY,
	BLUE,
	GREEN,
	AQUA,
	RED,
	LIGHT_PURPLE,
	YELLOW,
	WHITE,
	PINK(0xFF3366),
	CHARCOAL(0x38383B),
	COPPER(0xEA7F59),
	BRONZE(0xE9CA6A),
	IRON(0xC6C6C6),
	STEEL(0x707070),
	PLATINUM(0x4AEDD9),
	METEORIC_IRON(0x766A76);
	
	private final int colorValue;
	private final String stringValue;
	private final float r, g, b;

	Color()
	{
		Formatting formatting = Formatting.valueOf(name());
		colorValue = formatting.getColorValue();
		stringValue = formatting.toString();
		r = ((colorValue >> 16) & 0xFF) / 255.0f;
		g = ((colorValue >> 8) & 0xFF) / 255.0f;
		b = (colorValue & 0xFF) / 255.0f;
	}

	Color(int colorValue)
	{
		this.colorValue = colorValue;
		stringValue = String.format("#%06X", colorValue);
		r = ((colorValue >> 16) & 0xFF) / 255.0f;
		g = ((colorValue >> 8) & 0xFF) / 255.0f;
		b = (colorValue & 0xFF) / 255.0f;
	}
	
	public int getColorValue()
	{
		return colorValue;
	}
	
	@Override
	public String toString()
	{
		return stringValue;
	}
	
	public float getR()
	{
		return r;
	}

	public float getG()
	{
		return g;
	}

	public float getB()
	{
		return b;
	}

	@Override
	public Color getColor()
	{
		return this;
	}
}
