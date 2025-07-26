package tests.K28_DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;
import pages.TestotomasyonuPage;
import utilities.Driver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelStokKontrolTesti2_anaDsoyaDegil {

    private static final String EXCEL_PATH = "C:\\Users\\Hp\\OneDrive\\Desktop\\stockKontrolu\\26.07.2925_stockKontrolu.xlsx";
    private static final String URL = "https://www.testotomasyonu.com";

    @DataProvider(name = "stokKodlari")
    public Object[][] stokKodlariProvider() throws IOException {
        // Excel dosyasını aç
        FileInputStream fis = new FileInputStream(EXCEL_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // İlk sayfayı al

        // Satır sayısını belirle (başlık hariç)
        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][1];

        // Excel'den stok kodlarını oku
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell stokKoduCell = row.getCell(0); // Stok kodunun olduğu sütun (A sütunu)
                data[i-1][0] = stokKoduCell.getStringCellValue();
            }
        }

        workbook.close();
        fis.close();

        return data;
    }

    @Test(dataProvider = "stokKodlari")
    public void stokKontrolTesti(String stokKodu) throws IOException {
        try {
            // Web sayfasını aç
            Driver.getDriver().get(URL);

            TestotomasyonuPage page = new TestotomasyonuPage();

            // Arama yap
            page.aramaKutusu.sendKeys(stokKodu + Keys.ENTER);

            // Bulunan ürün sayısını al
            int bulunanUrunSayisi = page.bulunanUrunElementleriList.size();

            // Excel dosyasını güncelle
            updateExcelWithStockResult(stokKodu, bulunanUrunSayisi);
        } finally {
            // Her test sonunda driver'ı kapat
            Driver.quitDriver();
        }
    }

    private void updateExcelWithStockResult(String stokKodu, int bulunanUrunSayisi) throws IOException {
        FileInputStream fis = new FileInputStream(EXCEL_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Renkleri tanımla
        CellStyle yesilStyle = workbook.createCellStyle();
        yesilStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        yesilStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle kirmiziStyle = workbook.createCellStyle();
        kirmiziStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        kirmiziStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Stok kodunu bul ve güncelle
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Başlık satırını atla

            Cell stokKoduCell = row.getCell(0);
            if (stokKoduCell != null && stokKoduCell.getStringCellValue().equals(stokKodu)) {
                // Stok adedi hücresini al veya oluştur (B sütunu olduğunu varsayıyorum)
                Cell stokAdediCell = row.getCell(1);
                if (stokAdediCell == null) {
                    stokAdediCell = row.createCell(1);
                }

                // Excel'deki stok adedini al
                int excelStokAdedi = (int) stokAdediCell.getNumericCellValue();

                // Sonuç hücresini oluştur veya güncelle (C sütunu olduğunu varsayıyorum)
                Cell sonucCell = row.getCell(2);
                if (sonucCell == null) {
                    sonucCell = row.createCell(2);
                }
                sonucCell.setCellValue(bulunanUrunSayisi);

                // Eşleşme durumuna göre renklendir
                if (excelStokAdedi == bulunanUrunSayisi) {
                    stokAdediCell.setCellStyle(yesilStyle);
                    sonucCell.setCellStyle(yesilStyle);
                } else {
                    stokAdediCell.setCellStyle(kirmiziStyle);
                    sonucCell.setCellStyle(kirmiziStyle);
                }
                break;
            }
        }

        // Değişiklikleri kaydet
        FileOutputStream fos = new FileOutputStream(EXCEL_PATH);
        workbook.write(fos);

        workbook.close();
        fos.close();
        fis.close();
    }
}