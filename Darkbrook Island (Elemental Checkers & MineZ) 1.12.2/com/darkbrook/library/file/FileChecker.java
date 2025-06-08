package com.darkbrook.library.file;

import java.io.File;

public class FileChecker {
	
	public static void createMissingDirectory(String path) {
		File file = new File(path);
		if(!file.exists()) file.mkdir();
	}

}
