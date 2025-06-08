package com.darkbrook.library.file.loggable;

public class LoggableBoolean {

	private LoggableFile file;
	private String key;
	private boolean value;
	
	public LoggableBoolean(LoggableFile file, String key, boolean defaultValue) {
		this.file = file;
		this.key = key;
		this.value = defaultValue;
		if(!file.exists()) return;
		this.value = file.hasKey(key) ? file.getString(key).equals("true") : defaultValue;
		this.setBoolean(this.value);
	}
	
	public void setBoolean(Boolean value) {
		this.value = value;
		this.file.setBoolean(key, value);
	}
	
	public boolean getBoolean() {
		return value;
	}	
	
}
