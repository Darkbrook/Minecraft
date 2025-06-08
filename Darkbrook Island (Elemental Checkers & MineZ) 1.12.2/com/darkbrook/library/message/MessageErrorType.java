package com.darkbrook.library.message;

public enum MessageErrorType {
	
	PERMISSION("Permission", "You do not have permission to execute this command"),
	INTEGER_SINGLE("Format", "The value you specified was not a valid integer."),
	INTEGER_MULTIPLE("Format", "The values you specified are not valid integers."),
	PAGE("Format", "The page number you specified is not a valid index."),
	HAND_ITEM("Item", "You do not have a valid item in your hand."), 
	PLAYER("Player", "The player your are querying is currently not online."), 
	SELECTION("Selection", "You do not have a valid selection."),
	CLIPBOARD_SELECTION("Selection", "You did not have a valid clipboard selection."),
	CLIPBOARD_PASTE("Paste", "Your clipboard could not be pasted."),
	BLUEPRINT_PASTE("Paste", "The specified blueprint could not be pasted."),
	BLOCK_PASTE("Paste", "The specified block could not be pasted."),
	BLUEPRINT_NOT_EXISTING("Blueprint", "The specified blueprint does not exist."),
	BLUEPRINT_ALREADY_EXISTING("Blueprint", "You can not overwrite blueprints that already exist."),
	DELAY("Blueprint", "The specified delayed blueprint could not be pasted."),
	CREATE_TASK("Task", "The task you attempted to make could not be created."),
	TASK_NOT_EXISTING("Task", "The specified task does not exist."),
	TASK_CALL("Task", "The specified task could not be called.");
	
	private String error;
	private String data;
	
	private MessageErrorType(String error, String data) {
		this.error = error;
		this.data = data;
	}
	
	public String getError() {
		return error;
	}
	
	public String getData() {
		return data;
	}

}
