package br.com.parser.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import jxl.write.WriteException;
import br.com.parser.engine.ReadDirectories;
import br.com.parser.output.ExportToExcel;
import br.com.parser.properties.Global;

public class Main {

	public static void main(String[] args) {
		ReadDirectories rd = new ReadDirectories();

		Hashtable<String, ArrayList<Integer>> projectInfo = new Hashtable<String, ArrayList<Integer>>();

		// rd.listProjects(Global.PATH);

		for (String path : Global.PATHS) {

			String project = path.substring(path.lastIndexOf('/') + 1);
//			System.out.println("\nProject: " + project.toUpperCase());

			try {
				rd.readDir(path);
				// System.out.println(Global.globalCounter
				// + " entries found in the whole project!");
				// System.out.println(Global.filesCAffected +
				// " C files affected");
				// System.out.println(Global.filesJavaAffected
				//	+ " Java files affected");

				ArrayList<Integer> numbers = new ArrayList<Integer>();
				numbers.add(Global.globalCounter);
				numbers.add(Global.filesCAffected);
				numbers.add(Global.filesJavaAffected);

				projectInfo.put(project.toUpperCase(), numbers);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			Global.filesCAffected = 0;
			Global.filesJavaAffected = 0;
			Global.globalCounter = 0;

		}

		ExportToExcel excel = new ExportToExcel();
		try {
			excel.run(projectInfo);
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
