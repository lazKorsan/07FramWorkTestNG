package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;


public class WebAppPage {

    public WebAppPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(linkText = "Zero Bank")
    public WebElement zeroBankLogo;

    @FindBy(id = "signin_button")
    public WebElement signinButonu;

    @FindBy(id = "user_login")
    public WebElement loginKutusu;

    @FindBy(id = "user_password")
    public WebElement passwordKutusu;

    @FindBy(xpath = "//*[@name='submit']")
    public WebElement submitButonu;

    @FindBy(xpath = "(//a[@class='dropdown-toggle'])[2]")
    public WebElement girisYapilanKullanici;

    @FindBy(id = "onlineBankingMenu")
    public WebElement onlineBankingButonu;

    @FindBy(id = "pay_bills_link")
    public WebElement payBillsLinki;

    @FindBy(xpath = "//*[text()='Purchase Foreign Currency']")
    public WebElement foreignCurrencySekmesi;

    @FindBy (id = "pc_currency")
    public WebElement pcCurrencyDropdown;

}