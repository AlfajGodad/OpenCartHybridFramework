package utility;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProvider1 {
	
	@DataProvider(name="LoginData1")
	public Object[][] loginData() throws IOException{
		String path= ".\\src\\testData\\LoginData.xlsx";
		ExcelUtility ex= new ExcelUtility(path);
		int rows= ex.getRowCount("Sheet1");
		int cols= ex.getColumnCount("Sheet1");
		Object [][] temp= new Object[rows][cols];
		for(int i=1; i<= rows; i++) {
			for(int j=0; j<cols; j++) {
				temp[i-1][j]=ex.sheet.getRow(i).getCell(j).getStringCellValue();
			}
		}
		return temp;
	}
	
	

}
