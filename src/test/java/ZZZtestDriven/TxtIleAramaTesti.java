package ZZZtestDriven;

import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.Driver;
import utilities.ReusableMethods;
import java.util.List;

public class TxtIleAramaTesti {

    @Test
    public void cokluAramaTesti() {
        String dosyaYolu = "src/test/resources/AranacakUrunler.txt";
        List<String> aranacakKelimeler = ReusableMethods.readTxtFileAsList(dosyaYolu);

        TestotomasyonuPage testotomasyonuPage = new TestotomasyonuPage();

        for (String herBirKelime : aranacakKelimeler) {
            System.out.println("Aranan kelime: " + herBirKelime);

            // 1. Anasayfaya git
            Driver.getDriver().get("https://www.testotomasyonu.com");

            // 2. Arama kutusuna kelimeyi yaz ve ara
            testotomasyonuPage.aramaKutusu.clear(); // Önceki aramayı temizle
            testotomasyonuPage.aramaKutusu.sendKeys(herBirKelime);
            // ... arama butonu click ...

            // 3. Arama sonucunu doğrula
            // Assert.assertTrue(...)
        }
    }
}