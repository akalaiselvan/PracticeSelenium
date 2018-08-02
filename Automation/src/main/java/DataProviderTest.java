/*To test Login using TestNG Data Provider logic
 * */


import jxl.read.biff.BiffException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class DataProviderTest {
    Selenium selenium=new Selenium();

    @DataProvider(name = "login")
    public Object[][] testcredential() throws Exception {
    return TestHelper.getfromExcel(selenium.SHEET_LOCATIO,selenium.sheetname);
    }

    @Test(dataProvider = "login")
    public void setup(String username , String password) throws InterruptedException {
        selenium.login(username,password);

    }

    public void closedriver(){
        selenium.closedriver();
    }



}
