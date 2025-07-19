package Pages;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.OutputType;
import java.text.SimpleDateFormat;
import org.openqa.selenium.*;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.io.File;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    public void click(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Click failed on element: " + locator.toString());
        }
    }

    public void type(By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return getElement(locator).getText();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    public WebElement findElement(String xpathPattern, String dynamicValue) {
        By locator = By.xpath(String.format(xpathPattern, dynamicValue));
        return getElement(locator);
    }

    public void applyFilter(String filterCheckboxXpath, String category) {
        WebElement checkbox = findElement(filterCheckboxXpath, category);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public WebElement getElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void hoverOverElement(By locator) {
        WebElement element = getElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public WebElement getVisibleElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void scrollUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollAndClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    public void takeScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        // Add timestamp to avoid overwriting
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = System.getProperty("user.dir") + "/screenshots/" + fileName + "_" + timestamp + ".png";

        try {
            FileHandler.createDir(new File(System.getProperty("user.dir") + "/screenshots"));
            FileHandler.copy(src, new File(path));
            System.out.println("Screenshot saved at: " + path);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    }






}
