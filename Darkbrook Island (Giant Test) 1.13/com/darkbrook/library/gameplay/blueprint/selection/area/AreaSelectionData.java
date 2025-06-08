package com.darkbrook.library.gameplay.blueprint.selection.area;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import com.darkbrook.library.gameplay.blueprint.material.ExtendedMaterial;
import com.darkbrook.library.util.helper.math.Vector3i;

public class AreaSelectionData
{

	protected AreaSelection selection;
	
	private List<ExtendedMaterial> materials;
	private List<String> keys;
	private List<Short> indexes;
	
	public AreaSelectionData(AreaSelection selection)
	{
		this.selection = selection;

		materials = new ArrayList<ExtendedMaterial>();
		keys = new ArrayList<String>();
		indexes = new ArrayList<Short>();		
	}

	public boolean load(Vector3i offset)
	{
		boolean hasSelection = selection.hasSelection();
		
		if(hasSelection)
		{
			loadSelection(selection.getWorld(), offset);
		}
		
		return hasSelection;
	}
	
	public List<ExtendedMaterial> getMaterials()
	{
		return materials;
	}

	public List<String> getKeys()
	{
		return keys;
	}

	public List<Short> getIndexes()
	{
		return indexes;
	}

	private void loadSelection(World world, Vector3i offset)
	{
		selection.scan(position -> 
		{
			ExtendedMaterial material = new ExtendedMaterial(position.sub(offset).getLocation(world).getBlock());
			String key = material.toString();
			
			if(!keys.contains(key))
			{
				keys.add(key);
				materials.add(material);
			}
			
			indexes.add((short) keys.indexOf(key));
		});
	}
	
}
