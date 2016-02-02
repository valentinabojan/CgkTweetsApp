package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "username")
    WebElement usernameField;

    @FindBy(how = How.ID, using = "password")
    WebElement passwordField;

    @FindBy(how = How.ID, using = "login")
    WebElement loginButton;

    public void enterTextIntoUsernameField(String username){
        usernameField.sendKeys(username);
    }

    public void enterTextIntoPasswordField(String password){
        passwordField.sendKeys(password);
    }

    public void clickOnLoginButton(){
        loginButton.click();
    }
}
