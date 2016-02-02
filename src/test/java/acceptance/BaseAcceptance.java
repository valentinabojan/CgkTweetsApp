package acceptance;

import acceptance.pageobject.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseAcceptance {

    protected static String baseUrl = "http://localhost:8080/";
    private static final WebDriver driver = new FirefoxDriver();
    private static final WebDriverWait wait = new WebDriverWait(driver, 10);

    public static WebDriver getDriver(){
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    protected static void login() {
        LoginPage login = new LoginPage(driver);

        login.enterTextIntoUsernameField("John Doe");
        login.enterTextIntoPasswordField("John Doe");

        login.clickOnLoginButton();
    }

    public static void tearDown() {
        driver.quit();
    }
}
