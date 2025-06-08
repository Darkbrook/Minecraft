package com.darkbrook.library.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.message.MessageErrorType;
import com.google.common.collect.Lists;

public class CommandHelpMenu {

	private List<List<String>> commands;
	private List<List<String>> descriptions;
	private List<String> commandsInitial;
	private List<String> descriptionsInitial;
	private String[] text;
	private int pages;
		
	public CommandHelpMenu(String... text) {
		this.commandsInitial = new ArrayList<String>();
		this.descriptionsInitial = new ArrayList<String>();
		this.text = text;
	}

	public void addEntry(String command, String description) {
		this.commandsInitial.add(command);
		this.descriptionsInitial.add(description);
	}
	
	public void sortEntryList() {
		this.commands = Lists.partition(commandsInitial, 4);
		this.descriptions = Lists.partition(descriptionsInitial, 4);
		this.pages = commands.size();
	}
	
	public void sendPageInformation(Player player, int page) {
		
		if(page > pages || page <= 0) {
			FormatMessage.error(player, MessageErrorType.PAGE);
			return;
		}
		
		List<String> commands = new ArrayList<String>(this.commands.get(page - 1));
		List<String> descriptions = new ArrayList<String>(this.descriptions.get(page - 1));
		int commandIndex = 0;
		
		for(int i = 0; i < text.length; i++) {	
			
			String displayText = text[i].replace("${page_number}", "" + page).replace("${page_numbers}", "" + pages);
			
			if(text[i].contains("${command}")) {
				
				if(commandIndex < commands.size()) {
					displayText = displayText.replace("${command}", commands.get(commandIndex)).replace("${description}", descriptions.get(commandIndex));
					commandIndex++;
					player.sendMessage(displayText);
				}
				
			} else {
				player.sendMessage(displayText);
			}
						
		}
		
	}
	
}
