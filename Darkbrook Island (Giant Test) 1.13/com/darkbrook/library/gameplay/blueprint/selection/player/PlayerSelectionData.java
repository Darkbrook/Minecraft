package com.darkbrook.library.gameplay.blueprint.selection.player;

import com.darkbrook.library.gameplay.blueprint.selection.area.AreaSelectionData;
import com.darkbrook.library.util.helper.math.Vector3i;

public class PlayerSelectionData extends AreaSelectionData
{

	public PlayerSelectionData(PlayerSelection selection)
	{
		super(selection);
	}
	
	public boolean load()
	{
		return load(new Vector3i(((PlayerSelection) selection).getPlayer().getLocation()));
	}

}
