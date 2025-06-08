package com.darkbrook.library.gameplay.gui.data;

import org.bukkit.Sound;

import com.darkbrook.library.data.object.ObjectDataParsed;
import com.darkbrook.library.data.object.ObjectDataQueue;
import com.darkbrook.library.gameplay.visual.DarkbrookSound;

public class GuiSound extends ObjectDataParsed<DarkbrookSound>
{

	public GuiSound(String identity) 
	{
		super(identity);
	}

	@Override
	protected DarkbrookSound onParsedValueLoad() 
	{		
		ObjectDataQueue data = getData("type/volm/tone", "level up", 1.0F, 1.0F);
		return new DarkbrookSound(Sound.valueOf(data.s().toUpperCase().replace(' ', '_')), data.f(), data.f());
	}

}
