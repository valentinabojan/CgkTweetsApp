package acceptance;

import acceptance.pageobject.AddNewCommentPage;
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
    private static AddNewCommentPage addNewCommentPage;

    private static String TITLE = "Default title";
    private static String BODY = "Default body";

    @BeforeClass
    public static void setup() {
        driver.get(baseUrl);

        login();

        listTweetPage = new ListTweetPage(driver);
        listCommentsPage = new ListCommentsPage(driver);
        addNewTweetPage = new AddNewTweetPage(driver, getWait());
        addNewCommentPage = new AddNewCommentPage(driver, getWait());

        addNewTweetPage.enterTextIntoTitleField(TITLE);
        addNewTweetPage.enterTextIntoBodyField(BODY);
        addNewTweetPage.clickOnSubmitButton();

        addNewCommentPage.enterTextIntoBodyField(BODY);
        listTweetPage.clickOnShowComments();
        addNewCommentPage.clickOnSubmitButton();
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