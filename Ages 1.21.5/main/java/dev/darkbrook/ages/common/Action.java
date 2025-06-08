package dev.darkbrook.ages.common;

public enum Action
{
	USE("use"),
	MINE("mine"),
	PLACE("place"),
	CRAFT("craft"),
	CRAFT_WITH("craft with");
	
	private final String descriptor;
	
	Action(String descriptor)
	{
		this.descriptor = descriptor;
	}
	
	public String getDescriptor()
	{
		return descriptor;
	}
}
