package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    public static ExtentReports extent;
    public static ExtentSparkReporter spark;
    public static ExtentTest test;
    // 'test' nesnesini ThreadLocal olarak değiştiriyoruz.
    // Bu, her testin (thread'in) kendi ExtentTest nesnesine sahip olmasını sağlar.
    // Böylece paralel testlerde raporlar birbirine karışmaz.
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports setupExtentReport() {
        // Rapor dosyalarına benzersiz bir isim vermek için tarih-saat formatı kullanılır.
        // Bu sayede hiçbir raporun üzerine yazılmaz ve tarihçe oluşur.
        String dateName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-output/reports/Report_" + dateName + ".html";

        spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Raporun genel ayarları
        extent.setSystemInfo("Tester", "Tango");
        extent.setSystemInfo("Proje", "Film Kesme Otomasyon");
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        spark.config().setDocumentTitle("TestNG Otomasyon Raporu");
        spark.config().setReportName("ExtentReports - Film Kesme");
        spark.config().setTheme(Theme.DARK);

        return extent;
    }

    public static void createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
    }

    /**
     * Mevcut thread'e ait olan ExtentTest nesnesini döndürür.
     * Test adımlarında loglama yapmak için bu metot kullanılmalıdır.
     * @return ExtentTest nesnesi
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}