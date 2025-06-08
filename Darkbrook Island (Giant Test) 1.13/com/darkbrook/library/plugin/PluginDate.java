package com.darkbrook.library.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PluginDate 
{

	private static final SimpleDateFormat FORMAT = getFormat();

	public static String getDate()
	{
		return FORMAT.format(new Date());
	}

	private static SimpleDateFormat getFormat()
	{
		SimpleDateFormat format = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return format;
	}
	
}
