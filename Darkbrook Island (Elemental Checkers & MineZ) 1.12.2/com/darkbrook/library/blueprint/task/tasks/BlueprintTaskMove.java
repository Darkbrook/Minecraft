package com.darkbrook.library.blueprint.task.tasks;

import org.bukkit.Location;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.task.base.BlueprintTaskMoving;

public class BlueprintTaskMove extends BlueprintTaskMoving {

	private String direction;
	private int count;
	private int moveCount;
	private boolean isPositive;
	
	public BlueprintTaskMove(Blueprint blueprint, Location runLocation, String direction, int xOffset, int yOffset, int zOffset, int count, int delay) {
		super(blueprint, runLocation, xOffset, yOffset, zOffset, delay);
		this.direction = this.getDirectionCoordinate(direction);
		this.count = count;
		this.moveCount = 0;
		this.isPositive = this.isPositive(direction);
	}

	@Override
	protected Location getMoveLocation() {
		if(moveCount < count) moveCount++;	
		if(moveCount >= count) this.cancle();
		this.modifyDirection(direction, isPositive);
		return this.getCurrentLocation();
	}

	@Override
	protected void onTick() {
		this.onTickMove();
	}

}
