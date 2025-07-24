package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class FacebookPage {

    public FacebookPage(){
        PageFactory.initElements(Driver.getDriver(), this);

    }

    @FindBy(id = "email")
    public WebElement emailKutusu;

    @FindBy(xpath = "//*[@id=\"pass\"]")
    public WebElement passwordKutusu;

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement loginButonu;
}
