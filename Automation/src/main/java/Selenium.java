import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Selenium {
    private String WEBDRIVER_GECKO = "webdriver.gecko.driver";
    private String geckodriverPath = "/home/dell/Documents/INVEST/geckodriver";
    private String PAGE_TITLE;
    private String PAGE_HEADER="Guru99 Bank Manager HomePage";
    WebDriver driver;
    private String Warning_Message;
    private String EXPECTED_ALERT_MESSAGE="User or Password is not valid";
    private String SHEET_LOCATIO="/home/dell/Documents/seltest.xls";
    String sheetname="gtest";




@Test
public void validateLogin() throws InterruptedException, IOException, BiffException {


        String up[][]=getfromExcel(SHEET_LOCATIO,sheetname);
        for (int i=0;i<up.length;i++){
            String username=up[i][0];
            String password=up[i][1];
            login(username,password);
        }

    }


    public boolean login(String username,String password) throws InterruptedException {
        boolean loginStatus=false;
        getThisLink("http://demo.guru99.com/v4/");
        waitinSeconds(2);
        WebElement element = findElementbyName("uid");
        element.sendKeys(username);
        waitinSeconds(1);
        WebElement pw = findElementbyName("password");
        pw.sendKeys(password);
        waitinSeconds(2);
        WebElement butt = findElementbyName("btnLogin");
        butt.click();
        waitinSeconds(5);
        PAGE_TITLE = driver.getTitle();
        setPAGE_TITLE(PAGE_TITLE);


        Alert alert= null; try {
            alert = driver.switchTo().alert();
            Warning_Message=alert.getText();
            alert.accept();
            if (Warning_Message.contains(EXPECTED_ALERT_MESSAGE)){
                System.out.println("Login Failed . Test passed for"+username+"and"+password);
                loginStatus=false;
            }
            else {
                System.out.println("Test failed for"+username+"and"+password);

            }


        } catch (NoAlertPresentException exception) {
            PAGE_TITLE = driver.getTitle();
            setPAGE_TITLE(PAGE_TITLE);
            if (getPAGE_TITLE().equals(PAGE_HEADER)) {
                System.out.println("Login Sucessfull.Test passed for"+username+"and"+password);
                loginStatus=true;
            } else {
                System.out.println("Test passed for"+username+"and"+password);
            }

        }

    return loginStatus;
    }

    public WebElement findElementbyName(String ByName) {
        By Name = By.name(ByName);
        WebElement webElement = driver.findElement(Name);
        return webElement;
    }

    public boolean checkAssertEquals(String text1, String text2) {
        boolean assertEquals = false;
        try {
            Assert.assertEquals(text1, text2);
            assertEquals = true;
        } catch (Exception e) {
            assertEquals = false;
        }
        return assertEquals;
    }


    public void waitinSeconds(int secondsToWait) throws InterruptedException {
       // driver.manage().timeouts().implicitlyWait(secondsToWait, TimeUnit.SECONDS);
        secondsToWait=secondsToWait*1000;
        Thread.sleep(secondsToWait);
    }


    public void getThisLink(String URL) {
        System.setProperty(getWEBDRIVER_GECKO(), getGeckodriverPath());
        FirefoxProfile profile=new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability(FirefoxDriver.PROFILE,profile);
        driver = new FirefoxDriver(capabilities);
        try {
            driver.get(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gettet() throws IOException, BiffException {
        String up[][]=getfromExcel(SHEET_LOCATIO,sheetname);
        for (int i=0;i<up.length;i++){
            String username=up[i][0];
            String password=up[i][1];
            System.out.println("USername : "+username+" password : "+password);
        }
    }













    @Test
    public String[][] getfromExcel(String SHEET_LOCATION,String SHEET_NAME) throws IOException, BiffException {

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










    public String getGeckodriverPath() {
        return geckodriverPath;
    }

    public void setGeckodriverPath(String geckodriverPath) {
        this.geckodriverPath = geckodriverPath;
    }

    public String getWEBDRIVER_GECKO() {
        return WEBDRIVER_GECKO;
    }

    public void setWEBDRIVER_GECKO(String WEBDRIVER_GECKO) {
        this.WEBDRIVER_GECKO = WEBDRIVER_GECKO;
    }

    public String getPAGE_TITLE() {
        return PAGE_TITLE;
    }

    public void setPAGE_TITLE(String PAGE_TITLE) {
        this.PAGE_TITLE = PAGE_TITLE;
    }
}
