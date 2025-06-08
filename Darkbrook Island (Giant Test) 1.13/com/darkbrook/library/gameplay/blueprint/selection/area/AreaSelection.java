package com.darkbrook.library.gameplay.blueprint.selection.area;

import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.World;

import com.darkbrook.library.util.helper.math.Vector2i;
import com.darkbrook.library.util.helper.math.Vector3i;

public class AreaSelection
{

	private Vector3i[] positions;
	private World world;
	private Location position0;
	private Location position1;
	
	public void scan(Consumer<Vector3i> consumer)
	{
		
		for(int x = positions[0].x; x <= positions[1].x; x++) 
		for(int y = positions[0].y; y <= positions[1].y; y++) 
		for(int z = positions[0].z; z <= positions[1].z; z++)
		{
			consumer.accept(new Vector3i(x, y, z));
		}
		
	}
	
	public void setPosition(Location location, int index)
	{
		
		if(index <= 0)
		{
			update(position0 = location);
		}
		else
		{
			update(position1 = location);
		}
		
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public boolean hasSelection()
	{
		return position0 != null && position1 != null && position0.getWorld() == position1.getWorld();
	}
	
	private void update(Location location)
	{
		
		if(hasSelection()) 
		{
			positions = getMatrix();
			world = location.getWorld();
		}
		
	}
	
	private Vector3i[] getMatrix() 
	{
		Vector2i x = getWeightedPoint(position0.getBlockX(), position1.getBlockX());
		Vector2i y = getWeightedPoint(position0.getBlockY(), position1.getBlockY());
		Vector2i z = getWeightedPoint(position0.getBlockZ(), position1.getBlockZ());

		return new Vector3i[] {new Vector3i(x.x, y.x, z.x), new Vector3i(x.y, y.y, z.y)};
	}

	private Vector2i getWeightedPoint(int value0, int value1) 
	{
		boolean isValueMax = value0 >= value1;
		return new Vector2i(isValueMax ? value1 : value0, isValueMax ? value0 : value1);
	}

}
