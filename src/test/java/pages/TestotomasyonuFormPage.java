package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TestotomasyonuFormPage {

    public TestotomasyonuFormPage(){
        PageFactory .initElements(utilities.Driver.getDriver(), this);
    }

    @FindBy(xpath = "(//*[@class='form-control'])[3]")
    public WebElement gunDropdownElementi;

    @FindBy(xpath = "(//*[@class='form-control'])[4]")
    public WebElement ayDropdownElementi;

    @FindBy(xpath = "(//*[@class='form-control'])[5]")
    public WebElement yilDropdownElementi;

    @FindBy(xpath = "//*[@for='gridCheck5']")
    public WebElement sirtAgrisiYaziElementi;

    @FindBy(id = "gridCheck5")
    public WebElement sirtAgrisiCheckboxElementi;

    @FindBy(id = "gridCheck4")
    public WebElement carpintiCheckboxElementi;

    @FindBy(id = "hastalikCheck2")
    public WebElement sekerCheckboxElementi;

    @FindBy(id = "hastalikCheck7")
    public WebElement epilepsiCheckboxElementi;

}
