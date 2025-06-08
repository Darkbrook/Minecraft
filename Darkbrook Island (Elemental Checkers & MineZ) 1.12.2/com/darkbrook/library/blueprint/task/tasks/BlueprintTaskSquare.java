package com.darkbrook.library.blueprint.task.tasks;

import org.bukkit.Location;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.task.base.BlueprintTaskMoving;

public class BlueprintTaskSquare extends BlueprintTaskMoving {

	private String direction0;
	private String direction1;
	private int count;
	private int countMove;
	private int phase;
	private boolean isPositive0;
	private boolean isPositive1;
	
	public BlueprintTaskSquare(Blueprint blueprint, Location runLocation, String direction0, String direction1, int xOffset, int yOffset, int zOffset, int offset, int count, int delay) {
		super(blueprint, runLocation, xOffset, yOffset, zOffset, delay);
		this.countMove = offset - 1;
		this.count = count;
		this.phase = 0;
		this.isPositive0 = this.isPositive(direction0);
		this.isPositive1 = this.isPositive(direction1);
		this.direction0 = this.getDirectionCoordinate(direction0);
		this.direction1 = this.getDirectionCoordinate(direction1);
		this.modifyDirection(direction0, isPositive0 ? offset : offset * -1);
	}

	@Override
	protected Location getMoveLocation() {
		
		if(countMove < count) countMove++;
			
		if(countMove >= count) {
			countMove = 0;
			phase = phase < 3 ? phase + 1 : 0;
		}

		if(phase == 0 || phase == 2) {
			this.modifyDirection(direction0, phase == 0 ? isPositive0 : !isPositive0);
		} else if(phase == 1 || phase == 3) {
			this.modifyDirection(direction1, phase == 1 ? isPositive1 : !isPositive1);
		}
		
		return this.getCurrentLocation();
		
	}

	@Override
	protected void onTick() {
		this.onTickMove();
	}

}
