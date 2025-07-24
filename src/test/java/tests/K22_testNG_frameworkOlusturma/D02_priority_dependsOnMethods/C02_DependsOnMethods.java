package tests.K22_testNG_frameworkOlusturma.D02_priority_dependsOnMethods;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;

public class C02_DependsOnMethods {
    // 3 farkli test methodu olusturup, asagidaki gorevleri yapin
    // 1- testotomasyonu anasayfaya gidip url'in testotomasyonu icerdigini test edin
    // 2- phone icin arama yapip urun bulunabildigini test edin
    // 3- ilk urunu tiklayip, urun isminde case sensitive
    // olmadan "phone" bulundugunu test edin

    @Test (priority = 1)

    public void anaSayfaTesti() throws InterruptedException {

        TestotomasyonuPage testOtomasyonuPage = new TestotomasyonuPage();

        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));




        String expectedUrlIcerik = "testotomasyonu";
        String actualUrl = Driver.getDriver().getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expectedUrlIcerik),"url verilen icerige sahip degil");


    }

    @Test (dependsOnMethods = "anaSayfaTesti")

    public void phoneAramaTesti(){
        TestotomasyonuPage testOtomasyonuPage = new TestotomasyonuPage() ;

        testOtomasyonuPage.aramaKutusu.sendKeys("phone" + Keys.ENTER);

        List<WebElement> bulunanUrunElementleriList = testOtomasyonuPage.bulunanUrunElementleriList;

        int actualUrunSayisi = bulunanUrunElementleriList.size();

        Assert.assertTrue(actualUrunSayisi>0, "Urun bulunamadi");


    }

    @Test (dependsOnMethods = "phoneAramaTesti")
    public void ilkUrunTesti(){

        TestotomasyonuPage testOtomasyonuPage = new TestotomasyonuPage();

        // ilk urune tiklayin
        testOtomasyonuPage.bulunanUrunElementleriList.get(0).click();

        // urun isminde "phone" kelimesinin oldugunu test edin
        String actualUrunIsmi = testOtomasyonuPage.ilkUrunSayfasiIsimElementi.getText().toLowerCase();
        String expectedUrunIsmi = "phone";

        Assert.assertTrue(actualUrunIsmi.contains(expectedUrunIsmi), "Urun ismi 'phone' kelimesini icermiyor");

        Driver.getDriver().quit();
    }
}
