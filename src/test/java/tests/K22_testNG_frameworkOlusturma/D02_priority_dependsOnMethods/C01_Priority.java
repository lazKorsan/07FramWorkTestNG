package tests.K22_testNG_frameworkOlusturma.D02_priority_dependsOnMethods;

import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.Driver;

// Sınıfımızın artık BaseTest'in özelliklerini miras almasını sağlıyoruz.
public class C01_Priority  {

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void testotomasyonuTest() {
        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));
        String expectedUrlContent = "testotomasyonu";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedUrlContent));
    }

    @Test(priority = 2, groups = "regression")
    public void wisequarterTest() {
        Driver.getDriver().get(ConfigReader.getProperty("url_wisequarter"));
        String expectedUrlContent = "wisequarter";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedUrlContent));
    }

    @Test(priority = 3, groups = "regression")
    public void bestbuyTest() {
        Driver.getDriver().get(ConfigReader.getProperty("url_bestbuy"));
        String expectedUrlContent = "bestbuy";
        String actualUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedUrlContent));
    }

    // @AfterClass veya benzeri driver kapatma metotlarını buradan SİLİN.
    // Çünkü bu işi artık BaseTest'teki @AfterMethod yapıyor.
}