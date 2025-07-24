package pages;

import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class YoutubePage {



        public YoutubePage(){
            PageFactory.initElements(Driver.getDriver(), this);

        }
}
