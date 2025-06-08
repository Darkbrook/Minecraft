package com.darkbrook.library.gameplay.blueprint.selection.player;

import org.bukkit.entity.Player;

import com.darkbrook.library.gameplay.blueprint.Blueprint;
import com.darkbrook.library.gameplay.blueprint.selection.area.AreaSelection;

public class PlayerSelection extends AreaSelection
{

	private Player player;
	@SuppressWarnings("unused")
	private Blueprint canvas;
	
	public PlayerSelection(Player player)
	{
		this.player = player;
	}
	
	public boolean updateCanvas()
	{
		return (canvas = new Blueprint()).load(this);
	}

	public Player getPlayer() 
	{
		return player;
	}	

}
