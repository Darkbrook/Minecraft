package com.darkbrook.library.blueprint.task.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class BlueprintTaskClock {
	
	private static final Map<Integer, BlueprintTaskClock> CLOCKS = new HashMap<Integer, BlueprintTaskClock>();
	
	private Map<Integer, BlueprintTask> taskMapping;
	private int size;
	private int id;
	private int delay;
	
	public BlueprintTaskClock(int delay) {
		this.taskMapping = new HashMap<Integer, BlueprintTask>();
		this.size = 0;
		this.delay = delay;
		CLOCKS.put(delay, this);
	}
	
	public void start() {
			
		id = UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				List<Integer> keys = getKeys();
				for(int key : keys) taskMapping.get(key).onTick();
			}
			
		}, 0, delay);
		
	}
	
	public void cancle() {
		UpdateHandler.cancle(id);
		CLOCKS.remove(delay);
	}
	
	private List<Integer> getKeys() {
		List<Integer> keys = new ArrayList<Integer>();
		keys.addAll(taskMapping.keySet());
		return keys;
	}
	
	public void removeBlueprintTask(BlueprintTask task) {
		taskMapping.remove(task.getId());
		size--;
	}
	
	public void addBlueprintTask(BlueprintTask task) {
		taskMapping.put(task.getId(), task);
		size++;
	}
	
	public int getSize() {
		return size;
	}
	
	public static BlueprintTaskClock getBlueprintTaskClock(int delay) {
		if(CLOCKS.containsKey(delay)) return CLOCKS.get(delay);
		BlueprintTaskClock clock = new BlueprintTaskClock(delay);
		clock.start();
		return clock;
	}

}
