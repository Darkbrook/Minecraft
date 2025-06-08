package com.darkbrook.library.command.events;

public abstract class CommandEvent {

	protected Object sender;
	protected String[] arguments;
	protected int length;
		
	public CommandEvent(Object sender, String[] arguments) {
		this.sender = sender;
		this.arguments = arguments;
		this.length = arguments.length;
	}
	
	public String[] getArguments() {
		return arguments;
	}
	
	public int getLength() {
		return length;
	}
	
	public abstract Object getCommandSender();

}
