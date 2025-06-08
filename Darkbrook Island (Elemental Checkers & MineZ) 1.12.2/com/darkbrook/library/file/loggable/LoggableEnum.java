package com.darkbrook.library.file.loggable;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LoggableEnum {

	private LoggableFile file;
	private String key;
	private Enum value;

	public LoggableEnum(LoggableFile file, String key, Enum defaultValue, Class<? extends Enum> clazz) {
		this.file = file;
		this.key = key;
		this.value = defaultValue;
		if(!file.exists()) return;
		this.value = file.hasKey(key) ? Enum.valueOf(clazz, file.getString(key)) : defaultValue;
		this.setEnum(this.value);
	}
	
	public void setEnum(Enum value) {
		this.value = value;
		this.file.setString(key, value.name());
	}
	
	public Enum getEnum() {
		return value;
	}	
	
}
