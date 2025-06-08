package com.darkbrook.library.blueprint.task.file;

import org.bukkit.Location;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.task.base.BlueprintTask;
import com.darkbrook.library.blueprint.task.tasks.BlueprintTaskMove;
import com.darkbrook.library.file.loggable.LoggableInteger;
import com.darkbrook.library.file.loggable.LoggableString;

public class BlueprintTaskFileMove extends BlueprintTaskFile {

	private LoggableString type;
	private LoggableString direction;
	private LoggableInteger count;
	private LoggableInteger delay;
	
	public BlueprintTaskFileMove(String name) {
		super(name);
		type = new LoggableString(this, "Type", "Move");
		direction = new LoggableString(this, "Direction", "");
		count = new LoggableInteger(this, "Count", 0);
		delay = new LoggableInteger(this, "Delay", 0);
	}
	
	public boolean create(String direction, String count, String delay) {
		
		if(!super.isDirectionSyntax(direction)) return false;
		
		int countInt = 0;
		int delayInt = 0;
		
		try {
			countInt = Integer.parseInt(count);
			delayInt = Integer.parseInt(delay);
		} catch (NumberFormatException e) {
			return false;
		}
		
		create(direction, countInt, delayInt);
		return true;
		
	}
	
	private String getType() {
		return type.getString();
	}

	private void create(String direction, int count, int delay) {
		this.create();
		this.type.setString("Move");
		this.direction.setString(direction);
		this.count.setInteger(count);
		this.delay.setInteger(delay);
	}

	@Override
	public BlueprintTask getBlueprintTask(Blueprint blueprint, Location runLocation, int xOffset, int yOffset, int zOffset) {
		return new BlueprintTaskMove(blueprint, runLocation, this.direction.getString(), xOffset, yOffset, zOffset, this.count.getInteger(), this.delay.getInteger());
	}	
	
	public static boolean isTask(String name) {
		BlueprintTaskFileMove task = new BlueprintTaskFileMove(name);
		return task.exists() && task.getType().equals("Move");
	}
	
}
