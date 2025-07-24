package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // Properties nesnesini static olarak oluşturuyoruz, böylece tüm projede tek bir nesne kullanılır.
    private static Properties properties;

    // static blok, bu sınıf kullanıldığı anda ilk olarak çalışır ve konfigürasyon dosyasını yükler.
    static {
        // Dosya adı, resources klasörünün kökünde olduğu için sadece ismi yeterlidir.
        String dosyaYolu = "configuration.properties";
        properties = new Properties();

        try {
            // ClassLoader kullanarak dosyayı classpath'ten bir stream olarak okuyoruz.
            // Bu yöntem, dosya yolunun nerede olduğundan bağımsız çalışır.
            InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(dosyaYolu);

            if (inputStream != null) {
                properties.load(inputStream); // Stream'den özellikleri yüklüyoruz.
                inputStream.close(); // Stream'i kapatıyoruz.
            } else {
                // Dosya bulunamazsa, daha anlaşılır bir hata fırlatıyoruz.
                throw new RuntimeException(dosyaYolu + " dosyası classpath'te bulunamadı!");
            }

        } catch (IOException e) {
            // Dosya okunurken bir hata olursa, hatayı yazdırıyoruz.
            System.out.println("configuration.properties dosyası okunurken hata oluştu.");
            e.printStackTrace();
        }
    }

    /**
     * Verilen key'e ait değeri configuration.properties dosyasından getirir.
     * @param key Alınmak istenen verinin anahtarı (key)
     * @return İstenen anahtarın değeri (value)
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Verilen key'e ait değer bulunamazsa, varsayılan değeri döndürür.
     * @param key Alınmak istenen verinin anahtarı (key)
     * @param defaultValue Anahtar bulunamazsa döndürülecek varsayılan değer
     * @return İstenen anahtarın değeri veya varsayılan değer
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}