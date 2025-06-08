package dev.darkbrook.ages.common.lang;

import java.util.function.Predicate;

import com.ibm.icu.text.MessageFormat;

public class DelimitedBuilder<T>
{
	private final StringBuilder builder;
	
	private Predicate<T> predicate = t -> true;
	private String delimiter = ", ";
	private String pattern = "{0}";
	private String entryPattern = "{0} {1}";
	private String emptyAlternative = "";
	private boolean needsDelimiter;
	
	public DelimitedBuilder()
	{
		this(new StringBuilder());
	}
	
	public DelimitedBuilder(StringBuilder builder)
	{
		this.builder = builder;
	}
	
	public DelimitedBuilder<T> predicate(Predicate<T> predicate)
	{
		this.predicate = predicate;
		return this;
	}
	
	public DelimitedBuilder<T> delimiter(String delimiter)
	{
		this.delimiter = delimiter;
		return this;
	}
	
	public DelimitedBuilder<T> pattern(String pattern)
	{
		this.pattern = pattern;
		return this;
	}
	
	public DelimitedBuilder<T> entryPattern(String entryPattern)
	{
		this.entryPattern = entryPattern;
		return this;
	}
	
	public DelimitedBuilder<T> emptyAlternative(String emptyAlternative)
	{
		this.emptyAlternative = emptyAlternative;
		return this;
	}
	
	public DelimitedBuilder<T> append(T entry, Object... args)
	{
		return append(entryPattern, entry, args);
	}
	
	public DelimitedBuilder<T> append(String pattern, T entry, Object... args)
	{
		if (predicate.test(entry))
		{
			if (needsDelimiter)
				builder.append(delimiter);
			
			Object[] copy = new Object[args.length + 1];
			copy[0] = entry;
			System.arraycopy(args, 0, copy, 1, args.length);
			
			for (int i = 0; i < copy.length; i++)
				if (copy[i] instanceof Plural plural)
					copy[i] = plural.pluralize(copy);
			
			builder.append(MessageFormat.format(pattern, copy));
			needsDelimiter = true;
		}
		
		return this;
	}
	
	@Override
	public String toString()
	{
		return builder.length() > 0
			? MessageFormat.format(pattern, builder.toString())
			: emptyAlternative;
	}
}
