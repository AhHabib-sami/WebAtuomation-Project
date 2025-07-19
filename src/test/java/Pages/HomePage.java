package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver){
        super(driver);
    }



    private By signInButton = By.xpath("//a[normalize-space()='Sign In']");
    By closeButton = By.xpath("//div[contains(@class, 'modal_overlay')]/descendant::button[contains(@class, 'rounded-full')]");

    private By authorMenu = By.xpath("//a[contains(text(),'লেখক')]");

    public void ClickclosePopUp()
    {
        click(closeButton);
    }
    public void clickSignIn()
    {
        click(signInButton);
    }

    public void selectAuthorMenu() {
        click(authorMenu);
    }



}