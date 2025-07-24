package tests.K22_testNG_frameworkOlusturma.D01_TestNG_tanitim;

import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.ConfigReader;
import utilities.Driver;

public class C01 {

    @Test (groups = {"smoke", "e2e"})
    public void test01() throws InterruptedException {
        // 1. ChromeDriver kullanarak bir WebDriver objesi olusturun


        // 2. https://www.testotomasyonu.com adresine gidin
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));
        //3 urun arama kutusunu locate edin
        TestotomasyonuPage testOtomasyonuPage = new TestotomasyonuPage();
        testOtomasyonuPage.aramaKutusu.sendKeys("phone");
        testOtomasyonuPage.aramaKutusu.submit();



        // 4. urun arama kutusuna "shoe" yazdirin






    }
}
