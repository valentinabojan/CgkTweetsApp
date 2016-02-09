package acceptance.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddNewCommentPage {

    private WebDriverWait wait;

    public AddNewCommentPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    @FindBy(how = How.ID, using = "bodyComment")
    WebElement bodyField;

    @FindBy(how = How.ID, using = "bodyCommentRequiredMsg")
    WebElement bodyCommentRequiredMsg;

    @FindBy(how = How.ID, using = "submitComment")
    WebElement submitCommentButton;

    @FindBy(how = How.ID, using = "logout")
    WebElement logoutButton;

    public void enterTextIntoBodyField(String body){
        bodyField.clear();
        bodyField.sendKeys(body);
        wait.until(ExpectedConditions.textToBePresentInElementValue(bodyField, body));
    }

    public void clickOnSubmitButton(){
        submitCommentButton.click();
    }

    public String getBodyRequiredMessage(){
        wait.until(ExpectedConditions.visibilityOf(bodyCommentRequiredMsg));
        return bodyCommentRequiredMsg.getText();
    }

    public void clickOnLogoutButton(){
        logoutButton.click();
    }
}
