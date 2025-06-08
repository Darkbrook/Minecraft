package dev.darkbrook.ages.common;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Interaction
{
	LIGHT_NETHER_PORTAL("light_nether_portal"),
	SPAWN_WITHER("spawn_wither");
	
	private final String id;

	private Interaction(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return id;
	}
	
	private static final Map<String, Interaction> eventMap =
		Arrays.stream(Interaction.values())
			.collect(Collectors.toMap(Interaction::getId, Function.identity()));
	
	public static Interaction fromId(String id)
	{
		return eventMap.get(id);
	}
}
