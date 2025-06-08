package dev.darkbrook.ages.common.lang;

public interface Plural
{
	String pluralize(Object[] arguments);

	public static Plural of(int index, String word)
	{
		return args -> (int) args[index] == 1 
			? word 
			: word + "s";
	}
}
