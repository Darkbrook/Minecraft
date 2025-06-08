package com.darkbrook.library.blueprint.task.file;

import org.bukkit.Location;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.task.base.BlueprintTask;
import com.darkbrook.library.blueprint.task.tasks.BlueprintTaskBounce;
import com.darkbrook.library.file.loggable.LoggableBoolean;
import com.darkbrook.library.file.loggable.LoggableInteger;
import com.darkbrook.library.file.loggable.LoggableString;

public class BlueprintTaskFileBounce extends BlueprintTaskFile {

	private LoggableString type;
	private LoggableString direction;
	private LoggableInteger count;
	private LoggableInteger delay;
	private LoggableBoolean canEndStop;
	
	public BlueprintTaskFileBounce(String name) {
		super(name);
		type = new LoggableString(this, "Type", "Bounce");
		direction = new LoggableString(this, "Direction", "");
		count = new LoggableInteger(this, "Count", 0);
		delay = new LoggableInteger(this, "Delay", 0);
		canEndStop = new LoggableBoolean(this, "CanEndStop", false);
	}
	
	public boolean create(String direction, String count, String delay, String canEndStop) {
		
		if(!super.isDirectionSyntax(direction) || !super.isBooleanSyntax(canEndStop)) return false;
		
		int countInt = 0;
		int delayInt = 0;
		
		try {
			countInt = Integer.parseInt(count);
			delayInt = Integer.parseInt(delay);
		} catch (NumberFormatException e) {
			return false;
		}
		
		create(direction, countInt, delayInt, canEndStop.equals("true"));
		return true;
		
	}
	
	private String getType() {
		return type.getString();
	}

	private void create(String direction, int count, int delay, boolean canEndStop) {
		this.create();
		this.type.setString("Bounce");
		this.direction.setString(direction);
		this.count.setInteger(count);
		this.delay.setInteger(delay);
		this.canEndStop.setBoolean(canEndStop);
	}

	@Override
	public BlueprintTask getBlueprintTask(Blueprint blueprint, Location runLocation, int xOffset, int yOffset, int zOffset) {
		return new BlueprintTaskBounce(blueprint, runLocation, this.direction.getString(), xOffset, yOffset, zOffset, this.count.getInteger(), this.delay.getInteger(), this.canEndStop.getBoolean());
	}	
	
	public static boolean isTask(String name) {
		BlueprintTaskFileBounce task = new BlueprintTaskFileBounce(name);
		return task.exists() && task.getType().equals("Bounce");
	}
	
}
