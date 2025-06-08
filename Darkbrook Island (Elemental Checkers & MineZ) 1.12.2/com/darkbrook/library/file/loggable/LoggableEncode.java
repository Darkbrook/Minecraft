package com.darkbrook.library.file.loggable;

import org.bukkit.Material;

public class LoggableEncode {
		
	private String extrasString;
	private String syntaxKey;
	private String syntaxData;
	private boolean hasExtras;
	
	public LoggableEncode(Material type, int data, String[] extras) {

		this.hasExtras = (extras != null && extras.length > 0);
		
		if(this.hasExtras) {
			extrasString = "";
			for(String extra : extras) extrasString += extra + ",";
		}
		
		this.syntaxKey = "{" + type.name().toLowerCase() + "," + data  + (hasExtras ? (",[" + extrasString.substring(0, extrasString.length() - 1) + "]}") : "}");
		this.syntaxData = "";
				
	}
	
	public void addLocation(int blockX, int blockY, int blockZ) {
		this.syntaxData += "[" + blockX + "," + blockY + "," + blockZ + "],";
	}
	
	public String getSyntaxKey() {
		return syntaxKey;
	}
	
	public String getSyntaxData() {
		return "{" + syntaxData.substring(0, syntaxData.length() - 1) + "}";
	}
	
}
