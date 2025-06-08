package com.darkbrook.library.file.loggable;

public class LoggableString {

	private LoggableFile file;
	private String key;
	private String value;
	
	public LoggableString(LoggableFile file, String key, String defaultValue) {
		this.file = file;
		this.key = key;
		this.value = defaultValue;
		if(!file.exists()) return;
		this.value = file.hasKey(key) ? file.getString(key) : defaultValue;
		this.setString(this.value);
	}
	
	public void setString(String value) {
		this.value = value;
		this.file.setString(key, value);
	}
	
	public String getString() {
		return value;
	}	
	
}
