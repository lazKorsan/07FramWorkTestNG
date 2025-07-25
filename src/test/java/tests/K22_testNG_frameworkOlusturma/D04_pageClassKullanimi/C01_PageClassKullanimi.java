package tests.K22_testNG_frameworkOlusturma.D04_pageClassKullanimi;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods; // Yeni metodumuzu import ediyoruz
import utilities.TestBaseRapor;


public class C01_PageClassKullanimi extends TestBaseRapor {

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
        ReusableMethods.getWebElementScreenshot(testotomasyonuPage.aramaKutusu, "arama_kutusu");
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
}