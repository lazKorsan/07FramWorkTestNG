package ZDonguselTestOotmasyonu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class onBellekIsındırma {


    @Test
    public void test05_HerDefasindaAcKapat() {
        System.out.println("Her defasında tarayıcı aç-kapat testi başladı.");

        for (int i = 1; i <= 5; i++) {
            // 1. Adım: Her döngüde YENİ bir WebDriver nesnesi oluştur.
            WebDriver loopDriver = new ChromeDriver();
            loopDriver.manage().window().maximize();
            loopDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // 2. Adım: İstenen sayfaya git.
            loopDriver.get("https://www.testotomasyonu.com");
            System.out.println(i + ". kez YENİ tarayıcı açıldı ve siteye gidildi.");

            // 3. Adım: O döngüde açılan tarayıcıyı kapat.
            loopDriver.quit();
            System.out.println("   " + i + ". tarayıcı kapatıldı.");
        }

        System.out.println("Her defasında tarayıcı aç-kapat testi bitti.");
    }
}
