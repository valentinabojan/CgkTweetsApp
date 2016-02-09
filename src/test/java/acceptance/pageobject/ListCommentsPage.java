package acceptance.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ListCommentsPage {

    public ListCommentsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "commentsList")
    List<WebElement> commentsList;

    public List<WebElement> getListOfComments() {
        return commentsList;
    }

    public String getFirstCommentBody() {
        return commentsList.get(0).findElement(By.id("commentBody")).getText();
    }
}
