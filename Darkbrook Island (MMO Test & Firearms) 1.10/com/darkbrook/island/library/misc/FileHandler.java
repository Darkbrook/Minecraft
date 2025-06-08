package com.darkbrook.island.library.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	
	public static void createExternalDirectorys(String path) {
		File file = new File(path);
		file.mkdirs();
	}
	
	private static void copyInternal(String from, String to) {

		try {
			if(!new File(to.substring(0, to.lastIndexOf("\\"))).exists()) createExternalDirectorys(to.substring(0, to.lastIndexOf("\\")));
			Files.copy(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void copy(String from, String to) {

		if(new File(from).isDirectory()) {
		
			for(File file : new File(from).listFiles()) {
				
				if(file.isDirectory()) {
					copy(file.getAbsolutePath(), to + "\\" + file.getName());
				} else {
					copyInternal(file.getAbsolutePath(), to + "\\" + file.getName());
				}
			
			}
		
		} else {
			copyInternal(from, to);
		}
		
	}
	
	public static void delete(String path) {
		
		if(new File(path).isDirectory()) {
			
			for(File f : new File(path).listFiles()) {
				
				if(f.isDirectory()) {
					delete(f.getAbsolutePath());
				} else {
					f.delete();
				}
				
			}
			
			new File(path).delete();
						
		} else {
			new File(path).delete();
		}
		
	}

	public static void writeToExternalFile(String path, String message, boolean newLine) {
		
		try {
			
			FileWriter writer = new FileWriter(new File(path), true);
			
			if(newLine) {
				writer.write(message + System.getProperty("line.separator"));
			} else {
				writer.write(message);
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeToExternalFile(String path, String[] messages, boolean newLine) {
		
		for(String message : messages) {
			writeToExternalFile(path, message, newLine);
		}
		
	}
	
	public static ArrayList<String> readFromExternalFile(String path) {
		
		ArrayList<String> message = new ArrayList<String>();
				
		try {
			
			Scanner scanner = new Scanner(new File(path));
			
			while(scanner.hasNext()) {
				message.add(scanner.nextLine());
			}
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return message;
		
	}
	
}
