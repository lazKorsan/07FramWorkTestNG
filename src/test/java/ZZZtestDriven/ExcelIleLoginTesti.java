package ZZZtestDriven;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ReusableMethods;

public class ExcelIleLoginTesti {

    @DataProvider
    public Object[][] loginVerileriProvider() {
        String dosyaYolu = "src/test/resources/LoginData.xlsx";
        String sayfaAdi = "Sheet1"; // Excel'deki sayfanızın adı
        return ReusableMethods.getExcelData(dosyaYolu, sayfaAdi);
    }

    @Test(dataProvider = "loginVerileriProvider")
    public void cokluLoginTesti(String email, String password) {
        System.out.println("Test ediliyor: Email = " + email + ", Şifre = " + password);

        // 1. Login sayfasına gidin
        // Driver.getDriver().get("login_url");

        // 2. Page objesi ile email ve password'u gönderin
        // testotomasyonuPage.loginEmailKutusu.sendKeys(email);
        // testotomasyonuPage.loginPasswordKutusu.sendKeys(password);
        // testotomasyonuPage.loginSignInButonu.click();

        // 3. Girişin başarılı/başarısız olduğunu doğrulayın
        // Assert.assertTrue(...)
    }
}