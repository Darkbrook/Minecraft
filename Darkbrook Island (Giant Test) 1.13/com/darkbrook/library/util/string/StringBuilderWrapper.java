package com.darkbrook.library.util.string;

public class StringBuilderWrapper
{
	
	private StringBuilder builder;

	public StringBuilderWrapper()
	{
		builder = new StringBuilder();
	}
	
	public void append(IStringCondition condition, String... strings)
	{
		for(int i = 0; i < strings.length; i++) append(condition.onModify(strings[i], i, i == strings.length - 1));		
	}
	
	public void append(String suffix, String... strings)
	{		
		for(int i = 0; i < strings.length; i++) append(strings[i] + (i < strings.length - 1 ? suffix : ""));
	}
	
	public void append(String... strings)
	{
		for(String string : strings) append(string);
	}
	
	public void append(String string)
	{
		builder.append(string);
	}

	public String toString()
	{
		return builder.toString();
	}
	
}