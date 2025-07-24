package tests.ZScreenShots;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.BaseTest;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ======================buradaki method çok önemli========================
// ===========methodun çağırılması
//  ReusableMethods.takeFullPageScreenshot("Anasayfa_Ilk_Hali");


public class FullScreenShots extends BaseTest {

    @Test
    public void anasayfaTesti(){

        // 1- testotomasyonu anasayfaya gidip
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));
        ReusableMethods.bekle(3);

        // ====================================================================
        // ===> YENİ METODUMUZU BURADA ÇAĞIRIYORUZ <===
        ReusableMethods.takeFullPageScreenshot("Anasayfa_Ilk_Hali");
        // ====================================================================

        // url'in testotomasyonu icerdigini test edin
        String expectedUrlIcerik = "testotomasyonu";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expectedUrlIcerik));
    }

    @Test(dependsOnMethods = "anasayfaTesti")
    public void phoneAramaTesti(){
        // 2- phone icin arama yapip
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));
        TestotomasyonuPage testotomasyonuPage = new TestotomasyonuPage();
        testotomasyonuPage.aramaKutusu.sendKeys("phone" + Keys.ENTER);

        // Arama sonuçları sayfasının ekran görüntüsünü alalım
        ReusableMethods.takeFullPageScreenshot("Arama_Sonuclari");

        // urun bulunabildigini test edin
        int actualBulunanUrunSayisi = testotomasyonuPage.bulunanUrunElementleriList
                .size();

        Assert.assertTrue(actualBulunanUrunSayisi > 0);
    }

    @Test(dependsOnMethods = "phoneAramaTesti")
    public void ilkUrunIsimTesti(){
        // 3- ilk urunu tiklayip,
        TestotomasyonuPage testotomasyonuPage = new TestotomasyonuPage();
        testotomasyonuPage
                .bulunanUrunElementleriList
                .get(0)
                .click();

        // Ürün detay sayfasının ekran görüntüsünü alalım
        ReusableMethods.takeFullPageScreenshot("Urun_Detay_Sayfasi");

        // urun isminde case sensitive olmadan "phone" bulundugunu test edin
        String expectedIsimIcerik = "phone";
        String actualUrunIsmi = testotomasyonuPage
                .ilkUrunSayfasiIsimElementi
                .getText()
                .toLowerCase();

        Assert.assertTrue(actualUrunIsmi.contains(expectedIsimIcerik));
    }
    public static void takeFullPageScreenshot(String fileName) {

        //=========================FULL PAGE SCREENSHOT METHODU========================================
        // methodu çağırmak için
        // ReusableMethods.takeFullPageScreenshot("Anasayfa_Ilk_Hali");
        //===== çok öneli bir method =====
        try {
            TakesScreenshot tss = (TakesScreenshot) Driver.getDriver();
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = fileName + "_" + timeStamp + ".png";

            // ====================================================================
            // ===> DEĞİŞİKLİK BURADA YAPILDI <===
            String directoryPath = "C:/Users/Hp/OneDrive/Desktop/testNG";
            // ====================================================================

            Path fullPath = Paths.get(directoryPath, finalFileName);
            Files.createDirectories(Paths.get(directoryPath));
            File sourceFile = tss.getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), fullPath);
            System.out.println("Tam sayfa ekran görüntüsü başarıyla kaydedildi: " + fullPath);
        } catch (IOException e) {
            System.err.println("Tam sayfa ekran görüntüsü alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}