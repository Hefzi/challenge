package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ExcelReader {

	private FileInputStream fis;
	private XSSFWorkbook workbook;

	/**
	 * Creates a new instance of the excel reader using the expected excel file path
	 *
	 * @param excelFilePath the expected path for the target excel file
	 */
	public ExcelReader(String excelFilePath) {

		fis = null;
		workbook = null;

		try {
			fis = new FileInputStream(excelFilePath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (IOException e) {
			Assert.fail("Couldn't find the desired file. [" + excelFilePath + "].");
		} catch (OutOfMemoryError e) {
			Assert.fail("Couldn't open the desired file. [" + excelFilePath + "].");
		} catch (EmptyFileException e) {
			Assert.fail("Please check the target file, as it may be corrupted. [" + excelFilePath + "].");
		}
	}

	/**
	 * Reads cell data from the first sheet in the desired excel workbook Reads cell
	 * data using row name (1st column) and column name
	 *
	 * @param rowName    the value of the first cell of the target row
	 * @param columnName the value of the first cell of the target column
	 * @return the value of the target cell within the target row and column within
	 *         the default sheet
	 */
	public String getCellData(int rowNo, int columnNo) {

		String value = null;

		Sheet sheet = workbook.getSheetAt(0); // getting the XSSFSheet object at given index
		Row row = sheet.getRow(rowNo); // returns the logical row
		Cell cell = row.getCell(columnNo); // getting the cell representing the given column
		value = cell.getStringCellValue(); // getting cell value
		return value; // returns the cell value

	}

}
