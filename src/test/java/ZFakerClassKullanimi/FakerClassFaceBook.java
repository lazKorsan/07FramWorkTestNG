package ZFakerClassKullanimi;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FacebookPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class FakerClassFaceBook {

    @Test
    public void facebookTesti(){
        //1 - https://www.facebook.com/ adresine gidin
        //2- Cookies cikiyorsa kabul edin
        //3- POM’a uygun olarak email, sifre kutularini ve giris yap butonunu locate edin
        //4- Faker class’ini kullanarak email ve sifre degerlerini yazdirip, giris butonuna basin
        //5- Basarili giris yapilamadigini test edin

        FacebookPage facebookPage = new FacebookPage();
        Driver.getDriver().get(ConfigReader.getProperty("url_facebook"));
        ReusableMethods.bekle(5);
        ReusableMethods.takeFullPageScreenshot("facebook");
    }
    @Test (dependsOnMethods = "facebookTesti")
    public void facebookLoginTesti(){
        FacebookPage facebookPage = new FacebookPage() ;

        Faker faker = new Faker();
        facebookPage.emailKutusu.sendKeys(faker.internet().emailAddress());
        facebookPage.passwordKutusu.sendKeys(faker.internet().password());
        ReusableMethods.bekle(2);
        ReusableMethods.getWebElementScreenshot(facebookPage.emailKutusu, "emailKutusu");
        ReusableMethods.takeFullPageScreenshot("facebookLogin");
        ReusableMethods.getWebElementScreenshot(facebookPage.passwordKutusu, "passsswordKutusu");
        ReusableMethods.getWebElementScreenshot(facebookPage.loginButonu , "loginButonu");
        facebookPage.loginButonu.click();
        ReusableMethods.bekle(2);
        ReusableMethods.takeFullPageScreenshot("girilemedi");

        Assert.assertTrue(facebookPage.loginButonu.isDisplayed());


    }
}
