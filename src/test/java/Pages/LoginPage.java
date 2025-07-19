package Pages;
import org.testng.Assert;
import org.checkerframework.checker.index.qual.PolyUpperBound;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }


    private By emailIdField = By.xpath("//input[@placeholder='Email or phone']");
    public By NextButton = By.xpath("//button[@id='js--btn-next']");
    public By LoginButton = By.xpath("//form[@id='otp-login-form']//button[@type='submit'][normalize-space()='Login']");
    public By secondModal = By.xpath("//div[contains(@class, 'modal_overlay')]/descendant::button[contains(@class, 'rounded-full')]");
    public By googleButton = By.xpath("//button[normalize-space()='Google']");
    public By ResendButton = By.xpath("//button[@id='resend-otp']");
    public By PopUpMessage = By. xpath("//div[@id='js--message']");

    public void enterEmail(String email){
        System.out.println("Typing email: " + email);
        type(emailIdField, email);
    }


    public void clickNextButton(){
        click(NextButton);
    }


    public void clickLoginButton(){
        click(LoginButton);
    }

    public void clickSecondModal()
    {
       click(secondModal);
    }
    public void ClickGoogleButton()
    {
        click(googleButton);
    }
    public void ClickResendButton()
    {
        click(ResendButton);
    }


    public boolean submitOTPWithValidation() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            By otpInputLocator = By.xpath("//input[@placeholder='OTP']");
            By resendBtnLocator = By.xpath("//button[@id='resend-otp']");
            By loginBtnLocator = By.xpath("//form[@id='otp-login-form']//button[@type='submit'][normalize-space()='Login']");

            // Wait for OTP input visible initially
            wait.until(ExpectedConditions.visibilityOfElementLocated(otpInputLocator));

            boolean otpFilled = false;
            long endTime = System.currentTimeMillis() + 60000; // 60 seconds timeout

            while (System.currentTimeMillis() < endTime) {
                try {
                    WebElement otpInput = driver.findElement(otpInputLocator);
                    String value = otpInput.getAttribute("value");
                    if (value != null && value.matches("^\\d{4}$")) {
                        otpFilled = true;
                        break;
                    }
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    // Element disappeared, retry in next iteration
                }

                Thread.sleep(500);
            }

            if (otpFilled) {
                WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginBtnLocator));
                loginBtn.click();
                System.out.println("Login submitted with valid OTP.");
            } else {
                WebElement resendBtn = wait.until(ExpectedConditions.elementToBeClickable(resendBtnLocator));
                resendBtn.click();
                System.out.println("OTP not provided in time. Resend clicked.");
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception during OTP processing: " + e.getMessage());
            return false;
        }
    }







}





