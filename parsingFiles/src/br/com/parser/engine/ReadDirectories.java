package br.com.parser.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

import br.com.parser.properties.Global;

public class ReadDirectories {

	public String getExtension(File file) {
		String extension = "";

		int i = file.getAbsolutePath().lastIndexOf('.');
		if (i > 0) {
			extension = file.getAbsolutePath().substring(i + 1);
		}
		return extension;

	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String substringBefore(String str, char c) {
		if (isEmpty(str)) {
			return str;
		}
		int pos = str.indexOf(c);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}
	
	public void listProjects(String path){
		File root = new File(path);
		File[] list = root.listFiles();
		
		for (File file : list) {
			System.out.println("\""+file.getAbsolutePath()+"\" ,");
		}
	}

	public void readDir(String path) throws FileNotFoundException {

		int entriesCounter = 0;
		ParseSingleFile parseSF = new ParseSingleFile();
		File root = new File(path);
		File[] list = root.listFiles();
		// int pathLength = Global.PATH.length();

		for (File file : list) {

			if (file.isDirectory()) {
				readDir(file.getAbsolutePath());
				// String subdir = file.getAbsolutePath().substring(pathLength);
				// if (!subdir.contains("/"))
				// System.out.println(subdir);

			} else {
				if (this.getExtension(file).equals("c")
					 || this.getExtension(file).equals("java")) {

					HashSet<String> entriesFound = new HashSet<String>();
					entriesFound = parseSF.run(file);

					if (!entriesFound.isEmpty()) {
						entriesCounter += entriesFound.size();
						System.out.println("\n" + entriesFound.size()
								+ " entries found in File: "
								+ file.getAbsoluteFile());
						for (String entry : entriesFound) {
							System.out.println(entry);
						}
						if (this.getExtension(file).equals("c"))
							Global.filesCAffected++;
						if (this.getExtension(file).equals("java"))
							Global.filesJavaAffected++;
					}
				}
			}
		}

		Global.globalCounter += entriesCounter;

	}
}
