package com.darkbrook.kingdoms.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFile extends PropertiesManager
{
	private static final Logger LOGGER = LoggerFactory.getLogger("Kingdoms");
	
	protected final Path path;
	protected final String description;
	
	public PropertiesFile(Path path, String description)
	{
		this(path, description, null);
	}
	
	public PropertiesFile(Path path, String description, Properties properties)
	{
		super(properties);
		this.path = path;
		this.description = description;
	}
	
	public void load()
	{		
		try (InputStream stream = Files.newInputStream(path))
		{				
			properties.load(stream);
		}
		catch(IOException e)
		{
			LOGGER.warn("Failed to load properties from file: {}", path);
		}
	}
	
	public void store()
	{		
		try (OutputStream stream = Files.newOutputStream(path))
		{
			properties.store(stream, description);
		}
		catch (IOException e)
		{
			LOGGER.warn("Failed to store properties to file: {}", path);
		}
	}
}
