package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

     By somokalinoUponnashCheckbox = By.xpath("//label[contains(text(),'সমকালীন উপন্যাস')]");
     By rochonaSankolonCheckbox = By.xpath("//label[contains(text(),'রচনা সংকলন ও সমগ্র')]");
     By somokalinoUponnashCheckboxInput = By.xpath("//label[contains(text(),'সমকালীন উপন্যাস')]/preceding-sibling::input[@type='checkbox']");
     By rochonaSankolonCheckboxInput = By.xpath("//label[contains(text(),'রচনা সংকলন ও সমগ্র')]/preceding-sibling::input[@type='checkbox']");

    public void selectSomokalinoUponnash() {
        scrollDown();
        click(somokalinoUponnashCheckbox);
    }

    public void selectRochonaSankolon() {
        scrollDown();
        click(rochonaSankolonCheckbox);
    }
    public boolean isSomokalinoUponnashSelected() {
        return driver.findElement(somokalinoUponnashCheckboxInput).isSelected();
    }

    public boolean isRochonaSankolonSelected() {
        return driver.findElement(rochonaSankolonCheckboxInput).isSelected();
    }
    public boolean scrollAndClickPageTwoIfExists() {
        try {
            // Scroll to pagination
            WebElement paginationDiv = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='pagination']")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", paginationDiv);


            Thread.sleep(1000);


            List<WebElement> pageTwoLinks = paginationDiv.findElements(By.xpath(".//a[normalize-space()='2']"));

            if (!pageTwoLinks.isEmpty()) {
                WebElement pageTwoLink = pageTwoLinks.get(0);

                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(pageTwoLink))
                        .click();

                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
