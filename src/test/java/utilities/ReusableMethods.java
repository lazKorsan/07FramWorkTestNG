package utilities;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReusableMethods {

    /**
     * Test sırasında belirli bir süre beklemek için kullanılır.
     * NOT: Bu, 'explicit wait' yerine geçmez. Sadece geçici veya demo amaçlı kullanılmalıdır.
     *
     * @param saniye Beklenecek süre (saniye cinsinden).
     */
    public static void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000L);
        } catch (InterruptedException e) {
            // InterruptedException durumunda, thread'in kesintiye uğradığını belirtmek iyi bir pratiktir.
            Thread.currentThread().interrupt();
            System.err.println("Thread bekleme sırasında kesintiye uğradı: " + e.getMessage());
        }
    }

    /**
     * Verilen bir WebElement listesindeki her bir elementin metnini alarak
     * bu metinlerden oluşan bir String listesi döndürür.
     *
     * @param webElementList Metinleri alınacak WebElement'leri içeren liste.
     * @return Elementlerin metinlerini içeren yeni bir String listesi.
     */
    public static List<String> getElementsText(List<WebElement> webElementList) {
        return webElementList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Belirtilen WebElement'in ekran görüntüsünü alır ve Masaüstündeki 'testNG' klasörüne kaydeder.
     * Dosya adı, zaman damgası ile eşsiz hale getirilir.
     *
     * @param element  Ekran görüntüsü alınacak WebElement.
     * @param fileName Kaydedilecek dosyanın temel adı.
     */
    public static void getWebElementScreenshot(WebElement element, String fileName) {
        try {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = fileName + "_" + timeStamp + ".png";
            // Not: Bu yol, sadece sizin bilgisayarınızda çalışacaktır.
            String directoryPath = "C:/Users/Hp/OneDrive/Desktop/testNG";
            Path fullPath = Paths.get(directoryPath, finalFileName);

            Files.createDirectories(Paths.get(directoryPath));
            File sourceFile = element.getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), fullPath);
            System.out.println("Element ekran görüntüsü başarıyla kaydedildi: " + fullPath);
        } catch (IOException e) {
            System.err.println("Element ekran görüntüsü alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tüm sayfanın ekran görüntüsünü alır ve Masaüstündeki 'testNG' klasörüne kaydeder.
     * Dosya adı, zaman damgası ile eşsiz hale getirilir.
     *
     * @param fileName Kaydedilecek ekran görüntüsü için verilecek temel isim.
     */
    public static void takeFullPageScreenshot(String fileName) {
        try {
            TakesScreenshot tss = (TakesScreenshot) Driver.getDriver();
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String finalFileName = fileName + "_" + timeStamp + ".png";
            // Not: Bu yol, sadece sizin bilgisayarınızda çalışacaktır.
            String directoryPath = "C:/Users/Hp/OneDrive/Desktop/testNG";
            Path fullPath = Paths.get(directoryPath, finalFileName);

            Files.createDirectories(Paths.get(directoryPath));
            File sourceFile = tss.getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), fullPath);
            System.out.println("Tam sayfa ekran görüntüsü başarıyla kaydedildi: " + fullPath);
        } catch (IOException e) {
            System.err.println("Tam sayfa ekran görüntüsü alınırken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Hata durumunda ExtentReports'a eklemek için ekran görüntüsü alır.
     * Görüntüyü projenin altındaki 'test-output/Screenshots' klasörüne kaydeder
     * ve raporlama için dosya yolunu String olarak döndürür.
     *
     * @param testName Raporlanacak testin adı, dosya adında kullanılacak.
     * @return Kaydedilen ekran görüntüsünün dosya yolu.
     */
    public static String addScreenshotToReport(String testName) {
        try {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timeStamp + ".png";
            String directoryPath = System.getProperty("user.dir") + "/test-output/Screenshots/";
            Path fullPath = Paths.get(directoryPath, fileName);

            Files.createDirectories(Paths.get(directoryPath));

            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);

            Files.copy(source.toPath(), fullPath);
            // Rapora eklemek için göreceli yolu döndürmek daha taşınabilir olabilir.
            return "Screenshots/" + fileName;
        } catch (IOException e) {
            System.err.println("Rapora ekran görüntüsü eklenirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Açık olan pencereler arasında, hedef URL'e sahip olan pencereye geçiş yapar.
     *
     * @param hedefUrl Geçiş yapılmak istenen pencerenin tam URL'i.
     */
    public static void switchWindowByUrl(String hedefUrl) {
        Set<String> allWindowHandles = Driver.getDriver().getWindowHandles();
        for (String eachHandle : allWindowHandles) {
            Driver.getDriver().switchTo().window(eachHandle);
            if (Driver.getDriver().getCurrentUrl().equals(hedefUrl)) {
                break;
            }
        }
    }

    /**
     * Açık olan pencereler arasında, hedef başlığa (title) sahip olan pencereye geçiş yapar.
     *
     * @param hedefTitle Geçiş yapılmak istenen pencerenin tam başlığı.
     */
    public static void switchWindowByTitle(String hedefTitle) {
        Set<String> allWindowHandles = Driver.getDriver().getWindowHandles();
        for (String eachHandle : allWindowHandles) {
            Driver.getDriver().switchTo().window(eachHandle);
            if (Driver.getDriver().getTitle().equals(hedefTitle)) {
                break;
            }
        }
    }

    public static String[][] getExcelData(String dosyaYolu, String sayfaAdi) {
        String[][] excelData = null;
        try (FileInputStream fileInputStream = new FileInputStream(dosyaYolu)) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheet(sayfaAdi);

            int satirSayisi = sheet.getLastRowNum(); // 0'dan başlar, bu yüzden +1'e gerek yok
            int sutunSayisi = sheet.getRow(0).getLastCellNum();

            excelData = new String[satirSayisi][sutunSayisi];

            for (int i = 1; i <= satirSayisi; i++) { // i=1, başlık satırını atlamak için
                for (int j = 0; j < sutunSayisi; j++) {
                    // Hücre boşsa NullPointerException'ı önlemek için kontrol
                    if (sheet.getRow(i).getCell(j) != null) {
                        excelData[i - 1][j] = sheet.getRow(i).getCell(j).toString();
                    } else {
                        excelData[i - 1][j] = ""; // Boş hücreler için boş string ata
                    }
                }
            }
            workbook.close();
        } catch (IOException e) {
            System.err.println("Excel dosyası okunurken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
        return excelData;
    }

    public static List<String> readTxtFileAsList(String dosyaYolu) {
        try {
            return Files.readAllLines(Paths.get(dosyaYolu));
        } catch (IOException e) {
            System.err.println("TXT dosyası okunurken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList(); // Hata durumunda boş liste döndürerek NullPointerException'ı önle
        }
    }
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
    public class BulXpath {


        public static void printXpathFormulas(WebElement element) {
            System.out.println("Metin (getText()): " + element.getText());
            System.out.println("Tag adı: " + element.getTagName());
            System.out.println("ID attribute: " + element.getAttribute("id"));
            System.out.println("Class attribute: " + element.getAttribute("class"));
            System.out.println("Placeholder attribute: " + element.getAttribute("placeholder"));

            // XPath formülleri
            String tag = element.getTagName();
            String id = element.getAttribute("id");
            String classAttr = element.getAttribute("class");
            String placeholder = element.getAttribute("placeholder");
            String name = element.getAttribute("name");

            if (id != null && !id.isEmpty()) {
                System.out.println("//" + tag + "[@id='" + id + "']");
                System.out.println("//*[@id='" + id + "']");
                System.out.println("//*[starts-with(@id,'" + id.substring(0, Math.min(3, id.length())) + "')]");
                System.out.println("//*[contains(@id,'" + id + "')]");
            }
            if (classAttr != null && !classAttr.isEmpty()) {
                System.out.println("//" + tag + "[@class='" + classAttr + "']");
            }
            if (placeholder != null && !placeholder.isEmpty()) {
                System.out.println("//" + tag + "[@placeholder='" + placeholder + "']");
            }
            if (name != null && !name.isEmpty()) {
                System.out.println("//" + tag + "[@name='" + name + "']");
            }
            // Kombinasyonlar
            if (id != null && !id.isEmpty() && classAttr != null && !classAttr.isEmpty()) {
                System.out.println("//" + tag + "[@id='" + id + "' and @class='" + classAttr + "']");
            }
            if (id != null && !id.isEmpty() && name != null && !name.isEmpty()) {
                System.out.println("//" + tag + "[@id='" + id + "' and @name='" + name + "']");
            }
        }
    }
}
