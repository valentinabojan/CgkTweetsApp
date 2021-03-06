package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddNewTweetPage {

    private WebDriverWait wait;

    public AddNewTweetPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    @FindBy(how = How.ID, using = "title")
    WebElement titleField;

    @FindBy(how = How.ID, using = "body")
    WebElement bodyField;

    @FindBy(how = How.ID, using = "titleRequiredMsg")
    WebElement titleRequiredMsg;

    @FindBy(how = How.ID, using = "bodyRequiredMsg")
    WebElement bodyRequiredMsg;

    @FindBy(how = How.ID, using = "submitTweet")
    WebElement submitTweetButton;

    @FindBy(how = How.ID, using = "logout")
    WebElement logoutButton;

    public void enterTextIntoTitleField(String title){
        titleField.clear();
        titleField.sendKeys(title);
        wait.until(ExpectedConditions.textToBePresentInElementValue(titleField, title));
    }

    public void enterTextIntoBodyField(String body){
        bodyField.clear();
        bodyField.sendKeys(body);
        wait.until(ExpectedConditions.textToBePresentInElementValue(bodyField, body));
    }

    public void clickOnSubmitButton(){
        submitTweetButton.click();
    }

    public String getTitleRequiredMessage(){
        wait.until(ExpectedConditions.visibilityOf(titleRequiredMsg));
        return titleRequiredMsg.getText();
    }

    public String getBodyRequiredMessage(){
        wait.until(ExpectedConditions.visibilityOf(bodyRequiredMsg));
        return bodyRequiredMsg.getText();
    }

    public void clickOnLogoutButton(){
        logoutButton.click();
    }
}
