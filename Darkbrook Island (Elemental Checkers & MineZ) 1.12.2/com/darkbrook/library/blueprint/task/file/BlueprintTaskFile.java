package com.darkbrook.library.blueprint.task.file;

import org.bukkit.Location;

import com.darkbrook.darkbrookisland.DarkbrookIsland;
import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.blueprint.task.base.BlueprintTask;
import com.darkbrook.library.file.loggable.LoggableFile;

public abstract class BlueprintTaskFile extends LoggableFile {

	public BlueprintTaskFile(String name) {
		super(DarkbrookIsland.getResourcePath() + "\\BlueprintTasks\\" + name + ".bpt");
	}
	
	protected boolean isDirectionSyntax(String direction) {
		return direction.equals("X") || direction.equals("-X") || direction.equals("Y") || direction.equals("-Y") || direction.equals("Z") || direction.equals("-Z");
	}
	
	protected boolean isBooleanSyntax(String bool) {
		return bool.equals("true") || bool.equals("false");
	}
	
	public abstract BlueprintTask getBlueprintTask(Blueprint blueprint, Location runLocation, int xOffset, int yOffset, int zOffset);

}
