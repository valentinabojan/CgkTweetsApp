package acceptance;

import acceptance.pageobject.AddNewTweetPage;
import acceptance.pageobject.ListCommentsPage;
import acceptance.pageobject.ListTweetPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITListComments extends BaseAcceptance {
    private static WebDriver driver = getDriver();
    private static ListTweetPage listTweetPage;
    private static ListCommentsPage listCommentsPage;
    private static AddNewTweetPage addNewTweetPage;

    @BeforeClass
    public static void setup() {
        driver.get(baseUrl);

        login();

        listTweetPage = new ListTweetPage(driver);
        listCommentsPage = new ListCommentsPage(driver);
        addNewTweetPage = new AddNewTweetPage(driver, getWait());

        // TODO Add a new tweet and comments to the new tweet
    }

    @AfterClass
    public static void tearDown() {
        addNewTweetPage.clickOnLogoutButton();
    }

    @Test
    public void commentsAreListed() throws InterruptedException {
        listTweetPage.clickOnShowComments();

        assertThat(listCommentsPage.getListOfComments()).isNotEmpty();
    }
}