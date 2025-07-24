package tests.K22_testNG_frameworkOlusturma.D04_pageClassKullanimi;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.TestotomasyonuFormPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class C03_DropdownTesti {
    //1 - https://testotomasyonu.com/form adresine gidin
    //2- Dogum tarihi gun seçeneğinden index kullanarak 5’i secin
    //3- Dogum tarihi ay seçeneğinden value kullanarak Nisan’i secin
    //4- Dogum tarihi yil seçeneğinden visible text kullanarak 1990’i secin
    //5- Secilen değerleri konsolda yazdirin
    //6- Ay dropdown menüdeki tum değerleri(value) yazdırın
    //7- Ay Dropdown menusunun boyutunun 13 olduğunu test edin
    @Test

    public void dropDownTesti() {

        TestotomasyonuFormPage testotomasyonuFormPage = new TestotomasyonuFormPage() ;

        Driver.getDriver().get(ConfigReader.getProperty("url_testotomasyonuform"));

        //2- Dogum tarihi gun seçeneğinden index kullanarak 5’i secin
        Select selectGun = new Select(testotomasyonuFormPage.gunDropdownElementi);
        selectGun.selectByIndex(5);
        ReusableMethods.getWebElementScreenshot(testotomasyonuFormPage.gunDropdownElementi ,"gunDropdownElementi");

        //3- Dogum tarihi ay seçeneğinden value kullanarak Nisan’i secin
        Select selectAy = new Select(testotomasyonuFormPage.ayDropdownElementi);
        selectAy.selectByValue("nisan");
        ReusableMethods.getWebElementScreenshot(testotomasyonuFormPage.ayDropdownElementi ,"ayDropdownElementi");

        //4- Dogum tarihi yil seçeneğinden visible text kullanarak 1990’i secin
        Select selectYil = new Select(testotomasyonuFormPage.yilDropdownElementi);
        selectYil.selectByVisibleText("1990");

        ReusableMethods.getWebElementScreenshot(testotomasyonuFormPage.yilDropdownElementi ,"yilDropdownElementi");

        //5- Secilen değerleri konsolda yazdirin
        System.out.println(
                selectGun.getFirstSelectedOption().getText()+ " " +
                        selectAy.getFirstSelectedOption().getText() + " " +
                        selectYil.getFirstSelectedOption().getText()
        );





    }

}
