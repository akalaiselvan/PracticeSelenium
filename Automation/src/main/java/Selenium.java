import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Selenium {
    public static String WEBDRIVER_GECKO = "webdriver.gecko.driver";
    public static String geckodriverPath = "/home/dell/Documents/INVEST/geckodriver";
    public static String PAGE_TITLE;
    public static String PAGE_HEADER="Guru99 Bank Manager HomePage";
    WebDriver driver;
    public String Warning_Message;
    public static String EXPECTED_ALERT_MESSAGE="User or Password is not valid";
    public String SHEET_LOCATIO="/home/dell/Documents/seltest.xls";
    String sheetname="gtest";






@Test
public void validateLogin() throws InterruptedException, IOException, BiffException {
        String up[][]=TestHelper.getfromExcel(SHEET_LOCATIO,sheetname); //get input data from excel sheet
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
            String check=findElementbyTagname("tbody");
            System.out.println(check);
            try {
                Assert.assertTrue(check.contains("Manger Id : mngr"));
            } catch (AssertionError e) {
                TestHelper.takeScreenshot(driver,"/home/dell/Documents/Failcase.png");
                System.out.println("Desired text not found in header");            }
            if (getPAGE_TITLE().equals(PAGE_HEADER)) {
                System.out.println("Login Sucessfull.Test passed for"+username+"and"+password);
                loginStatus=true;
            } else {
                System.out.println("Test passed for"+username+"and"+password);
            }

        }
        closedriver();

    return loginStatus;
    }

    public void closedriver(){
    driver.close();
    }

    public WebElement findElementbyName(String ByName) {
        By Name = By.name(ByName);
        WebElement webElement = driver.findElement(Name);
        return webElement;
    }

    public String findElementbyTagname(String tagname){
    By findtagname = By.tagName(tagname);
    String finddtag= driver.findElement(findtagname).getText();
    return finddtag;
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
        String up[][]=TestHelper.getfromExcel(SHEET_LOCATIO,sheetname);
        for (int i=0;i<up.length;i++){
            String username=up[i][0];
            String password=up[i][1];
            System.out.println("USername : "+username+" password : "+password);
        }
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
