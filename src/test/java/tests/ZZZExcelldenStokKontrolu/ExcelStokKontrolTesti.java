package tests.ZZZExcelldenStokKontrolu;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;
import pages.TestotomasyonuPage;
import utilities.Driver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelStokKontrolTesti {

    private static final String EXCEL_PATH = "C:\\Users\\Hp\\OneDrive\\Desktop\\stockKontrolu\\26.07.2925_stockKontrolu.xlsx";
    private static final String URL = "https://www.testotomasyonu.com";

    @BeforeClass
    public void setup() {
        // Test başlangıcında bir kereliğine yapılacak işlemler
        System.out.println("Stok kontrol testi başlıyor...");
    }

    @DataProvider(name = "stokKodlari")
    public Object[][] stokKodlariProvider() throws IOException {
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            fis = new FileInputStream(EXCEL_PATH);
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = sheet.getLastRowNum();
            Object[][] data = new Object[rowCount][1];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell stokKoduCell = row.getCell(0);
                    if (stokKoduCell != null) {
                        data[i-1][0] = stokKoduCell.getStringCellValue();
                    }
                }
            }
            return data;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    @Test(dataProvider = "stokKodlari")
    public void stokKontrolTesti(String stokKodu) {
        try {
            // Web sayfasını aç
            Driver.getDriver().get(URL);

            TestotomasyonuPage page = new TestotomasyonuPage();

            // Arama yap
            page.aramaKutusu.sendKeys(stokKodu + Keys.ENTER);

            // Bulunan ürün sayısını güvenli şekilde al
            int bulunanUrunSayisi = getUrunSayisiSafely(page);

            // Excel dosyasını güncelle
            updateExcelWithStockResult(stokKodu, bulunanUrunSayisi);

        } catch (Exception e) {
            System.err.println(stokKodu + " işlenirken hata oluştu: " + e.getMessage());
        } finally {
            // Her durumda driver'ı kapat
            safelyQuitDriver();
        }
    }

    private int getUrunSayisiSafely(TestotomasyonuPage page) {
        try {
            return page.bulunanUrunElementleriList.size();
        } catch (Exception e) {
            System.out.println("Ürün bulunamadı, stok adedi 0 olarak kaydedilecek");
            return 0;
        }
    }

    private void safelyQuitDriver() {
        try {
            if (Driver.getDriver() != null) {
                Driver.quitDriver();
            }
        } catch (Exception e) {
            System.err.println("Driver kapatılırken hata oluştu: " + e.getMessage());
        }
    }

    private void updateExcelWithStockResult(String stokKodu, int bulunanUrunSayisi) throws IOException {
        FileInputStream fis = null;
        Workbook workbook = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(EXCEL_PATH);
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            CellStyle yesilStyle = createCellStyle(workbook, IndexedColors.GREEN);
            CellStyle kirmiziStyle = createCellStyle(workbook, IndexedColors.RED);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell stokKoduCell = row.getCell(0);
                if (stokKoduCell != null && stokKodu.equals(stokKoduCell.getStringCellValue())) {
                    processRow(row, bulunanUrunSayisi, yesilStyle, kirmiziStyle);
                    break;
                }
            }

            fos = new FileOutputStream(EXCEL_PATH);
            workbook.write(fos);
        } finally {
            if (workbook != null) workbook.close();
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }

    private CellStyle createCellStyle(Workbook workbook, IndexedColors color) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void processRow(Row row, int bulunanUrunSayisi, CellStyle yesilStyle, CellStyle kirmiziStyle) {
        Cell stokAdediCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell sonucCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        try {
            int excelStokAdedi = (int) stokAdediCell.getNumericCellValue();
            sonucCell.setCellValue(bulunanUrunSayisi);

            CellStyle style = (excelStokAdedi == bulunanUrunSayisi) ? yesilStyle : kirmiziStyle;
            stokAdediCell.setCellStyle(style);
            sonucCell.setCellStyle(style);
        } catch (Exception e) {
            System.err.println("Hücre değerleri işlenirken hata: " + e.getMessage());
            sonucCell.setCellValue(bulunanUrunSayisi);
            stokAdediCell.setCellStyle(kirmiziStyle);
            sonucCell.setCellStyle(kirmiziStyle);
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Stok kontrol testi tamamlandı.");
    }
}