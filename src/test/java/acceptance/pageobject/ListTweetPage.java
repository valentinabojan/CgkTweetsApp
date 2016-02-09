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

    public List<WebElement> getListOfTweets() {
        return listAllTweets;
    }

    public void clickOnShowComments() {
        for (WebElement element : getListOfTweets()) {
            WebElement commentsNumberElement = element.findElement(By.id("commentsNumber"));
            Integer commentsNumber = Integer.parseInt(commentsNumberElement.getText());

            if (commentsNumber > 0) {
                WebElement showCommentsLink = element.findElement(By.id("showCommentsLink"));

                showCommentsLink.click();
                break;
            }
        }
    }

    public String getFirstTweetTitle() {
        return listAllTweets.get(0).findElement(By.id("title")).getText();
    }

    public String getFirstTweetBody() {
        return listAllTweets.get(0).findElement(By.id("body")).getText();
    }
}
