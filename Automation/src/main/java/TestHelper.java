import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

public class TestHelper {

    public static String[][] getfromExcel(String SHEET_LOCATION, String SHEET_NAME) throws IOException, BiffException {

        Workbook workbook= Workbook.getWorkbook(new File(SHEET_LOCATION));
        Sheet sheet=workbook.getSheet(SHEET_NAME);
        int rowcount=sheet.getRows();
        int columncount=sheet.getColumns();

        String[][] cresds=null;
        cresds=new String[rowcount-1][columncount];

        int credrow=0;
        for (int row=1;row<=rowcount-1;row++,credrow++) {
            int credcol=0;
            for (int column = 0; column <= columncount-1; column++,credcol++) {
                cresds[credrow][credcol]=sheet.getCell(column,row).getContents();
            }
        }

        return (cresds);

    }

}
