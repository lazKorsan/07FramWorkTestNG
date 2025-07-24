package tests.K22_testNG_frameworkOlusturma.D02_priority_dependsOnMethods;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TestotomasyonuPage;
import utilities.ConfigReader;
import utilities.Driver;

public class C02_PageKullanimi {

    @Test (groups = {"smoke", "e2e"})
    public void aramaTesti() {
        TestotomasyonuPage testOtomasyonuPage = new TestotomasyonuPage();
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));


        String expectedUrlIcerik = "testotomasyonu";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedUrlIcerik), "URL 'testotomasyonu' icermiyor");

    }
}