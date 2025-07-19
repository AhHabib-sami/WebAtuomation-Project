package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorPage extends BasePage {

    By boiMenu = By.xpath("//a[contains(text(), 'বই')]");
    By HumayunAhmed = By.xpath("//a[contains(text(),'হুমায়ূন আহমেদ')]");
    By lekhokMenu = By.xpath("//a[contains(text(),'লেখক')]");
    By nextPageBtn = By.xpath("//a[contains(text(),'Next')]");

    // Optional future work:
    // private By humayunAhmedLink = By.xpath("//a[contains(text(),'হুমায়ূন আহমেদ')]");

    public void HumayunAhmedClick(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement authorLink = wait.until(ExpectedConditions.elementToBeClickable(HumayunAhmed));
        authorLink.click();

    }
    public AuthorPage(WebDriver driver) {
        super(driver);
    }

    public void clickBoiMenu() {
        click(boiMenu);
    }

    public void hoverOnLekhokMenu() {
        hoverOverElement(lekhokMenu);

    }

    public boolean hasNextPage() {
        try {
            WebElement nextBtn = getElement(nextPageBtn);
            return nextBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void goToNextPage() {
        click(nextPageBtn);
    }
}
