package ZZZRaporluTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;

// TestBaseRapor yerine, tüm işi doğru yapan TestBaseCross'u extend ediyoruz.
public class RaporluPositiveLoginTesti extends TestBaseRapor {

    @Test
    public void positiveLoginTesti(){

        // ARTIK GEREKLİ DEĞİL! Base class bunu bizim için otomatik yapıyor.
        // extentTest = extentReports.createTest("pozitif Login testi",
        //         "Kullanici gecerli bilgilerle sisteme giris yapabilmeli");

        // 1- https://www.testotomasyonu.com/ anasayfasina gidin
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));
        // Raporlama log'larını testin içine yazmak yerine, base class'a bırakmak daha temizdir.
        // İsterseniz bu log'lar kalabilir, ama en iyi pratik testin sade olmasıdır.
        extentTest.info("Kullanici testotomasyonu anasayfaya gider");

        // 2- account linkine basin
        TestotomasyonuPage testotomasyonuPage = new TestotomasyonuPage();
        testotomasyonuPage.accountLinki.click();
        extentTest.info("account linkine basar");

        // 3- Kullanici email'i olarak gecerli email girin
        testotomasyonuPage.loginSayfasiEmailKutusu
                .sendKeys(ConfigReader.getProperty("toGecerliMail"));
        extentTest.info("Email kutusuna gecerli email'i yazar");

        // 4- Kullanici sifresi olarak gecerli sifre girin
        testotomasyonuPage.loginSayfasiPasswordKutusu
                .sendKeys(ConfigReader.getProperty("toGecerliPassword"));
        extentTest.info("Password kutusuna gecerli password'u yazar");

        // 5- Login butonuna basarak login olun
        testotomasyonuPage.loginSayfasiSubmitButonu.click();
        extentTest.info("Login butonuna basarak login olur");

        // 6- Basarili olarak giris yapilabildigini test edin
        Assert.assertTrue(testotomasyonuPage.logoutButonu.isDisplayed());
        // ARTIK GEREKLİ DEĞİL! Base class, testin sonunda assertion'lar sağlamsa
        // otomatik olarak "PASS" olarak işaretleyecektir.
        // extentTest.pass("Basarili olarak giris yapilabildigini test eder");

        // 7- logout olun
        testotomasyonuPage.logoutButonu.click();
        extentTest.info("Logout butonuna basarak sistemden cikis yapar");

        // 8- sayfayi kapatin
        // ARTIK GEREKLİ DEĞİL! Driver.quitDriver() base class'taki @AfterMethod'da
        // otomatik olarak çağrılıyor.
    }
}