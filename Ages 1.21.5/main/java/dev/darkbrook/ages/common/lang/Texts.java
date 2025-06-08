package dev.darkbrook.ages.common.lang;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public final class Texts
{
	private Texts() {}
	
	public static Text join(Text delimiter, Collection<Text> entries)
	{
		Iterator<Text> iterator = entries.iterator();
		
		if (!iterator.hasNext())
			return Text.empty();
		
		MutableText joined = Text.empty().append(iterator.next());
		
		while (iterator.hasNext())
			joined = joined.append(delimiter).append(iterator.next());
		
		return joined;
	}
}
