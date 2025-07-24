package tests.K22_testNG_frameworkOlusturma.D03_pageObjectModel_POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.time.Duration;

public class C01_BasicDriverClassKullanimi {

    @Test (priority = 1) // priority ile testlerin calisma sirasi degistirilebilir
    public void bestbuyTest(){

        Driver.getDriver().get(ConfigReader.getProperty("url_bestbuy"));

        String expectedUrl = "bestbuy" ;
        String actualUrl = Driver.getDriver().getCurrentUrl();
        System.out.println(actualUrl);

        Assert.assertTrue(actualUrl.contains(expectedUrl),
                "Bestbuy sayfasina gidilemedi, url beklenen degeri icermiyor");


    }

    @Test (priority = 2) // priority ile testlerin calisma sirasi degistirilebilir
    public void wisequarterTest(){

        Driver.getDriver().get(ConfigReader.getProperty("url_wisequarter"));

        String expectedurlIcerik = "wisequarter";
        //String actualUrl = Driver.getDriver().getCurrentUrl();
        System.out.println(Driver.getDriver().getWindowHandle());
        System.out.println(Driver.getDriver().getTitle());

        String actualUrl = Driver.getDriver().getTitle() ;



        Assert.assertTrue(actualUrl.contains(expectedurlIcerik));
        ReusableMethods.bekle(1);
        Driver.quitDriver();



    }

    @Test (priority = 3) // priority ile testlerin calisma sirasi degistirilebilir

    public void testotomasyonuTest(){

        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonu"));
        String expectedUrl = "testotomasyonu" ;
        String actualUrl = Driver.getDriver().getCurrentUrl();
        System.out.println(actualUrl);
        Assert.assertTrue(actualUrl.contains(expectedUrl),
                "Testotomasyonu sayfasina gidilemedi, url beklenen degeri icermiyor");
    }

    @Test
    public void youtube(){
       // Driver.getDriver().get(ConfigReader.getProperty("url_wisequarter"));

        Driver.getDriver("chrome").get("https://www.wisequarter.com/");

        Driver.getDriver().quit();


    }
}
