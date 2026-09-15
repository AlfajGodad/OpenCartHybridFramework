package utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName)
            throws IOException {

        try (
                FileInputStream fis =
                        new FileInputStream(path);

                XSSFWorkbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            XSSFSheet sheet =
                    workbook.getSheet(sheetName);

            return sheet.getLastRowNum();
        }
    }

    public int getCellCount(
            String sheetName,
            int rowNumber)
            throws IOException {

        try (
                FileInputStream fis =
                        new FileInputStream(path);

                XSSFWorkbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            XSSFSheet sheet =
                    workbook.getSheet(sheetName);

            return sheet
                    .getRow(rowNumber)
                    .getLastCellNum();
        }
    }

    public String getCellData(
            String sheetName,
            int rowNumber,
            int columnNumber)
            throws IOException {

        try (
                FileInputStream fis =
                        new FileInputStream(path);

                XSSFWorkbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            XSSFSheet sheet =
                    workbook.getSheet(sheetName);

            DataFormatter formatter =
                    new DataFormatter();

            return formatter.formatCellValue(
                    sheet
                            .getRow(rowNumber)
                            .getCell(columnNumber)
            );
        }
    }
}