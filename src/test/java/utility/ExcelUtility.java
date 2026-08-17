package utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	String path;
	public FileInputStream fs;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public ExcelUtility(String path) {
		this.path=path;
	}
	
	public int getRowCount(String name) throws IOException {
		fs= new FileInputStream(path);
		workbook= new XSSFWorkbook(fs);
		sheet= workbook.getSheet(name);
		int rowCount= sheet.getLastRowNum();
		workbook.close();
		fs.close();
		return rowCount;
	}
	
	public int getColumnCount(String name) throws IOException {
		fs= new FileInputStream(path);
		workbook= new XSSFWorkbook(fs);
		sheet= workbook.getSheet(name);
		int columnCount =sheet.getRow(1).getLastCellNum();
		workbook.close();
		fs.close();
		return columnCount;
	}
	
	
	
	

}
