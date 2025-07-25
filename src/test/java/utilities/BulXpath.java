package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

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