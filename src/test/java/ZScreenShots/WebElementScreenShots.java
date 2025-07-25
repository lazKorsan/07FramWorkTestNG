package ZScreenShots;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods; // Yeni metodumuzu import ediyoruz
import utilities.TestBaseRapor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebElementScreenShots extends TestBaseRapor {

    // 3 farkli test methodu olusturup, asagidaki gorevleri yapin
    // 1- testotomasyonu anasayfaya gidip url'in testotomasyonu icerdigini test edin
    // 2- phone icin arama yapip urun bulunabildigini test edin
    // 3- ilk urunu tiklayip, urun isminde case sensitive olmadan "phone" bulundugunu test edin

    @Test
    public void anasayfaTesti(){

        // 1- testotomasyonu anasayfaya gidip
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));

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

        // ====================================================================
        // ===> ReusableMethods ile ekran görüntüsü alıyoruz <===
        ReusableMethods.getWebElementScreenshot(testotomasyonuPage.aramaKutusu, "arama_kutusu");
        // ====================================================================
        testotomasyonuPage.aramaKutusu.sendKeys("phone" + Keys.ENTER);





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

        // urun isminde case sensitive olmadan "phone" bulundugunu test edin
        String expectedIsimIcerik = "phone";
        String actualUrunIsmi = testotomasyonuPage
                .ilkUrunSayfasiIsimElementi
                .getText()
                .toLowerCase();

        // ====================================================================
        // ===> İŞTE BURADA EKRAN GÖRÜNTÜSÜNÜ ALIYORUZ <===
        ReusableMethods.getWebElementScreenshot(testotomasyonuPage.ilkUrunSayfasiIsimElementi, "urun_ismi_elementi");
        // ====================================================================

        Assert.assertTrue(actualUrunIsmi.contains(expectedIsimIcerik));


    }
    public static void getWebElementScreenshot(WebElement element, String fileName) {
        try {

            // methodu çağırmak için
            // ReusableMethods.getWebElementScreenshot(testotomasyonuPage.aramaKutusu, "arama_kutusu");
            // 1. Adım: Zaman damgası oluşturarak eşsiz bir dosya adı sağlıyoruz.
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = fileName + "_" + timeStamp + ".png";

            // 2. Adım: Kaydedilecek klasör yolunu ve dosya yolunu belirliyoruz.
            String directoryPath = "C:/Users/Hp/OneDrive/Desktop/testNG";
            Path fullPath = Paths.get(directoryPath, finalFileName);

            // 3. Adım: Eğer belirtilen klasör yoksa, onu oluşturuyoruz.
            Files.createDirectories(Paths.get(directoryPath));

            // 4. Adım: WebElement'in ekran görüntüsünü alıyoruz.
            File sourceFile = element.getScreenshotAs(OutputType.FILE);

            // 5. Adım: Aldığımız ekran görüntüsünü hedef dosyamıza kopyalıyoruz.
            Files.copy(sourceFile.toPath(), fullPath);

            System.out.println("Ekran görüntüsü başarıyla kaydedildi: " + fullPath);

        } catch (IOException e) {
            System.err.println("Ekran görüntüsü alınırken veya kaydedilirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
