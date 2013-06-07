package br.com.parser.output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.parser.properties.Global;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportToExcel {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write(Hashtable<String, ArrayList<Integer>> projectInfo)
			throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Project Info", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet, projectInfo);

		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, 0, 0, "Project");
		addCaption(sheet, 1, 0, "# of Entries");
		addCaption(sheet, 2, 0, "C Files affected");
		addCaption(sheet, 3, 0, "Java Files affected");
		addCaption(sheet, 4, 0, "BTS URL");
		addCaption(sheet, 5, 0, "# of Bugs");

	}

	private void createContent(WritableSheet sheet,
			Hashtable<String, ArrayList<Integer>> projectInfo)
			throws WriteException, RowsExceededException {

		Set<Entry<String, ArrayList<Integer>>> set = projectInfo.entrySet();
		Iterator<Entry<String, ArrayList<Integer>>> it = set.iterator();

		int i = 1;
		while (it.hasNext()) {
			Entry<String, ArrayList<Integer>> entry = (Map.Entry<String, ArrayList<Integer>>) it
					.next();
			System.out.println(entry.getKey() + ": " + entry.getValue().get(0));
			Label col0 = new Label(0, i, entry.getKey(), times);
			Label col1 = new Label(1, i, entry.getValue().get(0).toString(),
					times);
			Label col2 = new Label(2, i, entry.getValue().get(1).toString(),
					times);
			Label col3 = new Label(3, i, entry.getValue().get(2).toString(),
					times);
			sheet.addCell(col0);
			sheet.addCell(col1);
			sheet.addCell(col2);
			sheet.addCell(col3);

			i++;
		}
	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	public void run(Hashtable<String, ArrayList<Integer>> projectInfo)
			throws WriteException, IOException {
		ExportToExcel test = new ExportToExcel();
		test.setOutputFile(Global.DESTINATION);
		test.write(projectInfo);
	}

}
