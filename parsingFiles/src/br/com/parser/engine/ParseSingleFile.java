package br.com.parser.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class ParseSingleFile {

	public HashSet<String> run(File file) throws FileNotFoundException {

		HashSet<String> listEntries = new HashSet<String>();

		Scanner scanner = new Scanner(file);

		int lineNum = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lineNum++;
			if (line.contains("#if ") || line.contains("#ifdef ")
					|| line.contains("#ifndef ") || line.contains("#else ")
					|| line.contains("#elif ") || line.contains("#endif ")
					|| line.contains("#end ") || line.contains("#enddef ")) {

				listEntries.add(lineNum + ": " + line);
			}
		}

		return listEntries;
	}
}
