package TestCase;

import Pages.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utility.DriverFactory;

public class TestPage extends DriverFactory {
    private static final Logger log = LoggerFactory.getLogger(TestPage.class);

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage login;
    private AuthorPage authorPage;
    private CategoryPage categoryPage;
    private AddToCartPage addToCartPage;

    @BeforeClass
    public void setUp() {
        setDriver("chrome");
        driver = getDriver();
        driver.get("https://www.rokomari.com/");

        homePage = new HomePage(driver);
        login = new LoginPage(driver);
        authorPage = new AuthorPage(driver);
        categoryPage = new CategoryPage(driver);
        addToCartPage = new AddToCartPage(driver);

        homePage.ClickclosePopUp();
        homePage.clickSignIn();
    }

    @Test
    public void testLoginWithManualInput() {
        System.out.println("Starting manual login test...");

        login.enterEmail("totoshomoo@gmail.com");
        login.clickNextButton();

        System.out.println("Waiting for OTP input...");
        login.submitOTPWithValidation();

        homePage.refreshPage();
    }

    @Test(dependsOnMethods = "testLoginWithManualInput")

    public void validateNavigateToAuthorPage() {
        homePage.refreshPage();
        authorPage.clickBoiMenu();
        homePage.refreshPage();
        authorPage.hoverOnLekhokMenu();
        authorPage.scrollUp();
        authorPage.HumayunAhmedClick();

        String pageSource = driver.getPageSource();
        String currentUrl = driver.getCurrentUrl();

        boolean isAuthorPresent = pageSource.toLowerCase().contains("হুমায়ূন আহমেদ".toLowerCase()) ||
                currentUrl.toLowerCase().contains("হুমায়ূন-আহমেদ".toLowerCase());

        if (!isAuthorPresent) {
            authorPage.takeScreenshot("validateNavigateToAuthorPage_failure");
        } else {
            authorPage.takeScreenshot("validateNavigateToAuthorPage_success");
        }

        Assert.assertTrue(isAuthorPresent, "Not navigated to হুমায়ূন আহমেদ author page");
        if (isAuthorPresent) {
            log.info("Successfully navigated to হুমায়ূন আহমেদ author page.");
        }
    }


    @Test(dependsOnMethods = "validateNavigateToAuthorPage")
    public void filterBooksByCategory() {
        log.info("Filtering by সমকালীন উপন্যাস and রচনা সংকলন ও সমগ্র");

        categoryPage.selectSomokalinoUponnash();
        categoryPage.selectRochonaSankolon();

        categoryPage.scrollUp();

        Assert.assertTrue(categoryPage.isSomokalinoUponnashSelected(),
                "সমকালীন উপন্যাস checkbox is not selected after clicking.");
        Assert.assertTrue(categoryPage.isRochonaSankolonSelected(),
                "রচনা সংকলন ও সমগ্র checkbox is not selected after clicking.");

        log.info("Category filters applied and verified successfully.");
    }
    @Test(dependsOnMethods = "filterBooksByCategory")
    public void validatePaginationPageTwoClick() {
        log.info("Scrolling for the Next Page");

        boolean result = categoryPage.scrollAndClickPageTwoIfExists();

        Assert.assertTrue(result, "next page was not found or not clickable.");
        log.info("next page found and clicked successfully.");
    }

    @Test(dependsOnMethods = "validatePaginationPageTwoClick")
    public void addSpecificBookToCart() {
        addToCartPage.clickKohenKobiKalidashBook();
        addToCartPage.switchToNewTab();  // switch driver to new tab// Opens in new tab
        addToCartPage.clickkohenKobiKalidashCart();   // Click add to cart

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Proceed to Checkout") || pageSource.contains("চেকআউট করুন"),
                "'Proceed to Checkout' button not found after adding to cart.");

        log.info("Book successfully added to cart.");

        addToCartPage.clickAddToCartSign();

    }

    @Test(dependsOnMethods = "addSpecificBookToCart")
    public void proceedToCheckout() {
        addToCartPage.clickAddToCartAndProceed();  // Click proceed
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("payment") || pageSource.contains("ঠিকানা দিন"),
                "Checkout step did not proceed to payment or address page.");
        log.info("Successfully proceeded to the final checkout step.");
    }





}





