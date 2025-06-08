package com.darkbrook.library.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MalleableFile {
	
	private File file;
	private String path;
	
	public MalleableFile(String path) {
		this.path = path;
		this.file = new File(path);
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public void create() {
		
		if(exists()) return;
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clear() {
		
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void write(String data, boolean isOnNewLine) {
		
		try {
			FileWriter writer = new FileWriter(file, true);
			writer.write(isOnNewLine ? data + System.getProperty("line.separator") : data);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void replaceWith(List<String> datas, boolean isOnNewLine) {
		clear();
		for(String data : datas) write(data, isOnNewLine);
	}
	
	public List<String> read() {
		
		List<String> data = new ArrayList<String>();
		
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) data.add(scanner.nextLine());			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	
	public void delete() {
		file.delete();
	}
	
	public List<File> getSubFiles() {
		return getFiles(new ArrayList<File>(), path);
	}
	
	private List<File> getFiles(List<File> files, String path) {
		for(File file : new File(path).listFiles()) if(file.isDirectory()) getFiles(files, file.getAbsolutePath()); else files.add(file);		
		return files;
	}
	
}
