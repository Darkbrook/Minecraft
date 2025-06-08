package dev.darkbrook.wildfire.client.option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.common.mixin.StackSizeOverride;
import dev.darkbrook.wildfire.common.util.SimpleResourceLocation;
import net.minecraft.item.Item;

public class MaxStackSizeConfig
{
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private final Path path;

	public MaxStackSizeConfig(Path path)
	{
		this.path = path;
	}

	public void load()
	{
		if (!Files.exists(path))
		{
			Wildfire.LOGGER.debug("Max stack size file not found at {}", path);
		}
		else try (JsonReader reader = GSON.newJsonReader(Files.newBufferedReader(path)))
		{
			deserialize(reader);
		}
		catch (IOException e)
		{
			Wildfire.LOGGER.error("Failed to load max stack sizes from {}", path, e);
		}
	}

	public void save()
	{
		try
		{
			Files.createDirectories(path.getParent());
			
			try (JsonWriter writer = GSON.newJsonWriter(Files.newBufferedWriter(path)))
			{
				serialize(writer);
			}
		}
		catch (IOException e)
		{
			Wildfire.LOGGER.error("Failed to save max stack sizes to {}", path, e);
		}
	}
	
	private static void deserialize(JsonReader reader) throws IOException
	{
		Set<Item> itemsToRestore = new HashSet<>(StackSizeOverride.AFFECTED_ITEMS);

		reader.beginObject();
		
		while (reader.hasNext())
		{
			String name = reader.nextName();
			Item item = Item.getByNameOrId(name);

			if (item == null)
			{
				Wildfire.LOGGER.warn("Item {} not found, skipping", name);
				reader.skipValue();
				continue;
			}

			int size = reader.nextInt();
			
			if (((StackSizeOverride) item).setCustomMaxStackSize(size))
				itemsToRestore.remove(item);
		}
		
		reader.endObject();
		
		itemsToRestore.stream()
			.map(StackSizeOverride.class::cast)
			.forEach(StackSizeOverride::unsetCustomMaxStackSize);
	}
	
	@SuppressWarnings("deprecation") 
	private static void serialize(JsonWriter writer) throws IOException
	{
		writer.beginObject();
		
		for (Item item : StackSizeOverride.AFFECTED_ITEMS)
			writer.name(SimpleResourceLocation.of(item)).value(item.getItemStackLimit());
		
		writer.endObject();
	}
}
