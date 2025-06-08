package com.darkbrook.library.blueprint.task.base;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.misc.MathHandler;

public abstract class BlueprintTask {
	
	private static final Map<Integer, BlueprintTask> TASKS = new HashMap<Integer, BlueprintTask>();
	private static final Map<Location, BlueprintTask> TASKS_LOCATIONS = new HashMap<Location, BlueprintTask>();
	
	private BlueprintTaskClock clock;
	private Blueprint blueprint;
	private Location runLocation;
	private int id;
	private int delay;
	
	public BlueprintTask(Blueprint blueprint, Location runLocation, int delay) {
		this.blueprint = blueprint;
		this.blueprint.load();
		this.runLocation = runLocation;
		this.id = MathHandler.RANDOM.nextInt();
		this.delay = delay;
	}
	
	public void start() {
		clock = BlueprintTaskClock.getBlueprintTaskClock(delay);
		clock.addBlueprintTask(this);
		TASKS.put(id, this);
		TASKS_LOCATIONS.put(runLocation, this);
	}
	
	public void cancle() {
		clock = BlueprintTaskClock.getBlueprintTaskClock(delay);
		clock.removeBlueprintTask(this);
		if(clock.getSize() <= 0) clock.cancle();
		TASKS.remove(id);
		TASKS_LOCATIONS.remove(runLocation);
	}
	
	public Blueprint getBlueprint() {
		return blueprint;
	}
	
	public Location getRunLocation() {
		return runLocation;
	}
	
	public int getId() {
		return id;
	}

	public int getDelay() {
		return delay;
	}
	
	protected abstract void onTick();

	public static boolean cancleIfLocationMatches(Location location) {
		if(!TASKS_LOCATIONS.containsKey(location)) return false;
		TASKS_LOCATIONS.get(location).cancle();
		return true;
	}
	
}
