package tests.K23_testNG_Assertions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ZeroPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.util.List;

// Artık tüm testlerimiz bu tek ve güçlü base class'ı extend edecek.
public class C03_SoftAssert extends TestBaseRapor {

    @Test
    public void test01(){
        // Base class @BeforeMethod'da driver'ı zaten başlattığı için
        // testin içinde tekrar Driver.getDriver() çağırmaya gerek yok.

        // 1. “http://zero.webappsecurity.com/” Adresine gidin
        // DÜZELTME: .properties dosyasındaki doğru anahtar kullanıldı.
        Driver.getDriver().get(ConfigReader.getProperty("url_zerowebappsecurity"));
        extentTest.info("Kullanıcı Zero Web App Security anasayfasına gider.");

        // 2. Anasayfaya gidildiğini doğrulayın
        String expectedUrl = ConfigReader.getProperty("url_zerowebappsecurity");
        String actualUrl = Driver.getDriver().getCurrentUrl();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualUrl, expectedUrl, "URL beklenenden farklı");

        ZeroPage zeroPage = new ZeroPage();

        // 3. Sign in butonuna basin
        zeroPage.anaSayfaSignInButton.click();
        extentTest.info("Sign In butonuna tıklandı.");

        // 4. Login kutusuna “username” yazin
        zeroPage.loginKutusu.sendKeys(ConfigReader.getProperty("zerowebappsecurity_kullanici_adi"));

        // 5. Password kutusuna “password” yazin
        zeroPage.passwordKutusu.sendKeys(ConfigReader.getProperty("zerowebappsecurity_sifre"));
        extentTest.info("Geçerli kullanıcı adı ve şifre girildi.");

        // 6. Sign in tusuna basin
        zeroPage.loginPageSignInButton.click();

        // 7. Back tusuna basin
        Driver.getDriver().navigate().back();

        // 8. Giris yapilabildigini dogrulayin
        softAssert.assertTrue(zeroPage.settingsMenu.isDisplayed(), "Giriş sonrası 'Settings' menüsü görünmüyor.");
        extentTest.pass("Başarılı bir şekilde login olundu ve anasayfaya dönüldü.");

        // 9. Online banking menusunu tiklayin
        zeroPage.onlineBankingMenu.click();

        //10. Pay Bills sayfasina gidin
        zeroPage.payBills.click();
        extentTest.info("Pay Bills sayfasına gidildi.");

        //11. “Purchase Foreign Currency” tusuna basin
        zeroPage.purchaseForeignCurrency.click();

        //12. Currency dropdown menusunun erisilebilir oldugunu dogrulayin
        softAssert.assertTrue(zeroPage.currencyDdm.isDisplayed(),"Currency dropdown kullanılamıyor");

        //13. “Currency” dropdown menusunden Eurozone’u secin
        Select select = new Select(zeroPage.currencyDdm);
        select.selectByValue("EUR");

        //14. "Eurozone (euro)" secildigini dogrulayin
        String expectedSecim = "Eurozone (euro)";
        String actualSecim = select.getFirstSelectedOption().getText();
        softAssert.assertEquals(actualSecim, expectedSecim, "Dropdown seçimi yanlış");
        extentTest.info("Para birimi olarak Eurozone seçildi ve doğrulandı.");

        //15. Dropdown menude 16 option bulundugunu dogrulayin.
        List<WebElement> dropdownElementleriList = select.getOptions();
        int expectedOptionSayisi = 16;
        int actualOptionSayisi = dropdownElementleriList.size();
        softAssert.assertEquals(actualOptionSayisi, expectedOptionSayisi, "Dropdown'daki option sayısı 16 değil");

        //16. Dropdown menude "Canada (dollar)" bulunduğunu dogrulayin
        List<String> optionYazilariList = ReusableMethods.getElementsText(dropdownElementleriList);
        String expectedOption = "Canada (dollar)";
        softAssert.assertTrue(optionYazilariList.contains(expectedOption),"Dropdown 'Canada (dollar)' içermiyor");
        extentTest.info("Dropdown içeriği doğrulandı.");

        // 17. Sayfayi kapatin
        // GEREKLİ DEĞİL! Base class'taki @AfterMethod bunu otomatik olarak yapıyor.

        // EN ÖNEMLİ EKLEME: SoftAssert'in sonuçları değerlendirmesi için bu satır MUTLAKA olmalı!
        softAssert.assertAll();
    }
}