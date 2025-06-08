package com.darkbrook.library.file.loggable;

public class LoggableInteger {

	private LoggableFile file;
	private String key;
	private int value;
	
	public LoggableInteger(LoggableFile file, String key, int defaultValue) {
		this.file = file;
		this.key = key;
		this.value = defaultValue;
		if(!file.exists()) return;
		this.value = file.hasKey(key) ? file.getInteger(key) : defaultValue;
		this.setInteger(this.value);
	}
	
	public void setInteger(int value) {
		this.value = value;
		this.file.setInteger(key, value);
	}
	
	public int getInteger() {
		return value;
	}
	
}
