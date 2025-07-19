package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCartPage extends BasePage {

    public AddToCartPage(WebDriver driver) {
        super(driver);

    }

    By kohenKobiKalidashCart = By.xpath("//div[@class='col-span-2']//button[@id='ts--desktop-button']");
    By kohenKobiKalidash = By.xpath("//img[@alt='Kohen Kobi Kalidas image']");
    By proceedToCheckoutButton = By.xpath("//span[normalize-space()='Proceed to Checkout']");
    By addToCartSign = By.cssSelector("a[class='navigation_cartContainer__9oZWv'] svg");

    public void clickKohenKobiKalidashBook(){
        click(kohenKobiKalidash);
    }
    public void clickAddToCartSign(){
        click(addToCartSign);
    }


    public void clickkohenKobiKalidashCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Scroll down slightly until the cart button is in view
        WebElement cartButton = wait.until(ExpectedConditions.presenceOfElementLocated(kohenKobiKalidashCart));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", cartButton);

        // Wait for it to become clickable after scrolling
        cartButton = wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
    }

    public void clickAddToCartAndProceed() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(kohenKobiKalidashCart));
        cartButton.click();

        // Wait for modal or success indication
        WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
        proceedBtn.click();
    }

    public void switchToNewTab() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }


}