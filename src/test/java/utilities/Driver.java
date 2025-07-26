package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions; // Firefox için eklendi


import java.time.Duration;

public class Driver {

    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private Driver() {
    }

    public static WebDriver getDriver(String browser) {
        if (driverPool.get() == null) {

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    // === YENİ: Firefox için zoom ayarı ===
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    // Bu ayar, Firefox'un ölçeklendirmesini 1.0 (%100) olarak ayarlar.
                    firefoxOptions.addPreference("layout.css.devPixelsPerPx", "1.0");
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;



                default: // "chrome" veya tanımsız bir değer gelirse
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-search-engine-choice-screen");

                    // ====================================================================
                    // ===> YENİ EKLENEN SATIR: Tarayıcı zoom seviyesini %100'e zorlar <===
                    // 1.0 = %100, 1.25 = %125 anlamına gelir.
                    options.addArguments("--force-device-scale-factor=1.0");
                    // ====================================================================

                    driverPool.set(new ChromeDriver(options));
                    break;
            }

            driverPool.get().manage().window().maximize();
            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        }
        return driverPool.get();
    }

    public static WebDriver getDriver() {
        String defaultBrowser = ConfigReader.getProperty("browser", "chrome");
        return getDriver(defaultBrowser);
    }

    public static void quitDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

    public static void closeDriver() {
    }
}