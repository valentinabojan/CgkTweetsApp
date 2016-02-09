package acceptance.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ListTweetPage {

    public ListTweetPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "listTweets")
    List<WebElement> listAllTweets;

    @FindBy(how = How.ID, using = "commentsNumber")
    WebElement commentsNumber;

    @FindBy(how = How.ID, using = "showCommentsLink")
    WebElement showCommentsLink;

    public List<WebElement> getListOfTweets() {
        return listAllTweets;
    }

    public Integer getCommentsNumber() {
        return Integer.parseInt(commentsNumber.getText());
    }

    public void clickOnShowComments() {
        showCommentsLink.click();
    }

    public String getFirstTweetTitle() {
        return listAllTweets.get(0).findElement(By.id("title")).getText();
    }

    public String getFirstTweetBody() {
        return listAllTweets.get(0).findElement(By.id("body")).getText();
    }
}
