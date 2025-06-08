package com.darkbrook.library.blueprint;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AsyncBlueprintTask {
	
	private static final Map<String, AsyncBlueprintTask> ASYNC_TASK_MAPPING = new HashMap<String, AsyncBlueprintTask>();
	private static final Random RANDOM = new Random();
	
	public static String scheduleNewTask(String taskName, int staticValue) {
		
		String taskId = RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + "-" +
				    	RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + "-" +
				    	RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + "-" +
				    	RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10) + RANDOM.nextInt(10);
		
		AsyncBlueprintTask task = new AsyncBlueprintTask(taskId, taskName, 0, staticValue);
		ASYNC_TASK_MAPPING.put(taskId, task);
		
		return taskId;
		
	}
	
	public static AsyncBlueprintTask getTask(String taskId) {
		return ASYNC_TASK_MAPPING.containsKey(taskId) ? ASYNC_TASK_MAPPING.get(taskId) : null;
	}
	
	public static void endTask(String taskId) {
		
		if(ASYNC_TASK_MAPPING.containsKey(taskId)) {
			ASYNC_TASK_MAPPING.remove(taskId);
		}
		
	}
	
	private String taskId;
	private String taskName;
	private int value;
	private int staticValue;
	
	public AsyncBlueprintTask(String taskId, String taskName, int value, int staticValue) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.value = value;
		this.staticValue = staticValue;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setStaticValue(int staticValue) {
		this.staticValue = staticValue;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public int getPercent() {
		return (int) Math.round(((double) value) / ((double) staticValue));
	}
	
	public int getValue() {
		return value;
	}

	public int getStaticValue() {
		return staticValue;
	}

}
