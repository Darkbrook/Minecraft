package com.darkbrook.library.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResourceLocation 
{
	
	private static final List<String[]> REPLACEMENTS = new ArrayList<String[]>();
				
	public static void addReplacements(String... values) 
	{
		
		for(int i = 0; i < values.length; i += 2) 
		{
			REPLACEMENTS.add(new String[] {values[i], values[i + 1]});
		}
		
	}

	private File file;
	private String path;
	
	public ResourceLocation(String path) 
	{
		for(String[] replacements : REPLACEMENTS) 
		{
			path = path.replace("$" + replacements[0], replacements[1]);
		}
		
		this.file = new File(path);
		this.path = path;
	}
	
	public File getFile() 
	{
		return file;
	}
	
	public String getPath() 
	{
		return path;
	}

}
