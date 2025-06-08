package com.darkbrook.library.gameplay.blueprint;

import com.darkbrook.library.gameplay.blueprint.selection.area.AreaSelectionData;
import com.darkbrook.library.gameplay.blueprint.selection.player.PlayerSelection;
import com.darkbrook.library.gameplay.blueprint.selection.player.PlayerSelectionData;

public class Blueprint
{
	
	@SuppressWarnings("unused")
	private AreaSelectionData data;
	
	public boolean load(PlayerSelection selection)
	{
		return ((PlayerSelectionData) (data = new PlayerSelectionData(selection))).load();
	}
	
}
