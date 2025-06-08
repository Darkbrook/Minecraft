package com.darkbrook.library.blueprint.task.tasks;

import org.bukkit.Location;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.task.base.BlueprintTaskMoving;

public class BlueprintTaskBounce extends BlueprintTaskMoving {

	private String direction;
	private int count;
	private int moveCount;
	private boolean isPositive;
	private boolean isInversed;
	private boolean isEndStopped;
	private boolean canEndStop;
	
	public BlueprintTaskBounce(Blueprint blueprint, Location runLocation, String direction, int xOffset, int yOffset, int zOffset, int count, int delay, boolean canEndStop) {
		super(blueprint, runLocation, xOffset, yOffset, zOffset, delay);
		this.direction = this.getDirectionCoordinate(direction);
		this.count = count;
		this.moveCount = -1;
		this.isPositive = this.isPositive(direction);
		this.isInversed = false;
		this.isEndStopped = canEndStop;
		this.canEndStop = canEndStop;
	}

	@Override
	public Location getMoveLocation() {
				
		if(isEndStopped) {
			isEndStopped = false;
		} else {
			
			if(moveCount < count) {
				moveCount++;
				if((moveCount == (count - 1)) && canEndStop) isEndStopped = true;
			}
							
			if(moveCount >= count) {
				moveCount = 0;
				isInversed = !isInversed;
			}
						
			this.modifyDirection(direction, !isInversed ? isPositive : !isPositive);
			
		}
		
		return this.getCurrentLocation();
		
	}

	@Override
	protected void onTick() {
		this.onTickMove();
	}

}
