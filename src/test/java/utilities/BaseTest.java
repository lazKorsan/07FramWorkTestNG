package utilities;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utilities.Driver;

public class BaseTest {

    @BeforeMethod
    @Parameters("browser") // testng.xml dosyasından "browser" parametresini oku
    public void setUp(@Optional("chrome") String browser) {
        // XML'den gelen 'browser' parametresini kullanarak Driver'ı başlat.
        // @Optional("chrome"): Eğer XML'den parametre gelmezse (örn: testi direkt IDE'den çalıştırırsan)
        // varsayılan olarak "chrome" kullan.
        Driver.getDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        // Her test metodundan sonra tarayıcıyı güvenli bir şekilde kapat.
        Driver.quitDriver();
    }
}