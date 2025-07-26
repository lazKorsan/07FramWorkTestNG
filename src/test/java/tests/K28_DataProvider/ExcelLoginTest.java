package tests.K28_DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelLoginTest {
    private WebDriver driver;
    private static final String EXCEL_YOLU = "C:\\Users\\Hp\\OneDrive\\Desktop\\stockKontrolu\\26.07.2025_logintest.xlsx";
    private static final String SCREENSHOT_FOLDER = "C:\\Users\\Hp\\OneDrive\\Desktop\\stockKontrolu\\";

    @FindBy(id = "email")
    public WebElement loginEmailKutusu;

    @FindBy(id = "password")
    public WebElement loginPasswordKutusu;

    @FindBy(id = "submitlogin")
    public WebElement loginSignInButonu;

    @FindBy(xpath = "(//*[text()='Logout'])[2]")
    public WebElement logoutButonu;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        FileInputStream fis = new FileInputStream(EXCEL_YOLU);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sayfa1");

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][2];

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i-1][0] = row.getCell(0).getStringCellValue(); // email
            data[i-1][1] = row.getCell(1).getStringCellValue(); // password
        }

        workbook.close();
        fis.close();

        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLoginWithExcelData(String email, String password) throws IOException {

        //Driver.getDriver().get("https://testotomasyonu.com/customer-login");
        driver.get("https://testotomasyonu.com/customer-login");

        loginEmailKutusu.sendKeys(email);
        loginPasswordKutusu.sendKeys(password);
        loginSignInButonu.click();

        boolean loginSuccess = isLogoutButtonVisible();

        // Excel'e sonuçları yaz
        updateExcelResult(email, password, loginSuccess);

        if (!loginSuccess) {
            takeScreenshot(email);
        }
    }

    private boolean isLogoutButtonVisible() {
        try {
            return logoutButonu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void updateExcelResult(String email, String password, boolean success) throws IOException {
        FileInputStream fis = new FileInputStream(EXCEL_YOLU);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sayfa1");

        // Hücre stilleri oluştur
        CellStyle successStyle = workbook.createCellStyle();
        successStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        successStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle failStyle = workbook.createCellStyle();
        failStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        failStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Tüm satırları kontrol et
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(0).getStringCellValue().equals(email) &&
                    row.getCell(1).getStringCellValue().equals(password)) {

                // Hücreleri renklendir
                row.getCell(0).setCellStyle(success ? successStyle : failStyle);
                row.getCell(1).setCellStyle(success ? successStyle : failStyle);
                break;
            }
        }

        FileOutputStream fos = new FileOutputStream(EXCEL_YOLU);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }

    private void takeScreenshot(String email) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "login_fail_" + email + "_" + timestamp + ".png";
        File destFile = new File(SCREENSHOT_FOLDER + fileName);

        Files.copy(screenshot.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}