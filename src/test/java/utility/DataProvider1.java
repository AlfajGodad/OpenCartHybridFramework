package utility;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProvider1 {

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData()
            throws IOException {

        String path =
                "./src/testData/LoginData.xlsx";

        ExcelUtility excelUtility =
                new ExcelUtility(path);

        String sheetName = "Sheet1";

        int rowCount =
                excelUtility.getRowCount(sheetName);

        int cellCount =
                excelUtility.getCellCount(
                        sheetName,
                        1
                );

        Object[][] loginData =
                new Object[rowCount][cellCount];

        for (int row = 1;
             row <= rowCount;
             row++) {

            for (int column = 0;
                 column < cellCount;
                 column++) {

                loginData[row - 1][column] =
                        excelUtility.getCellData(
                                sheetName,
                                row,
                                column
                        );
            }
        }

        return loginData;
    }
}