package acceptance;

import acceptance.pageobject.AddNewCommentPage;
import acceptance.pageobject.ListCommentsPage;
import acceptance.pageobject.ListTweetPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAddNewComment extends BaseAcceptance{

    private static WebDriver driver = getDriver();
    private static AddNewCommentPage addNewCommentPage;
    private static ListCommentsPage listCommentsPage;
    private static ListTweetPage listTweetPage;

    private static String BODY = "Default body";

    @BeforeClass
    public static void setup() {
        driver.get(baseUrl);

        login();

        addNewCommentPage = new AddNewCommentPage(driver, getWait());
        listCommentsPage = new ListCommentsPage(driver);
        listTweetPage = new ListTweetPage(driver);
    }

    @AfterClass
    public static void tearDown() {
        addNewCommentPage.clickOnLogoutButton();
    }


    @Test
    public void givenACommentWithoutBody_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addNewCommentPage.enterTextIntoBodyField("");
        addNewCommentPage.clickOnSubmitButton();

        assertThat(addNewCommentPage.getBodyRequiredMessage()).isNotNull();
    }

    @Test
    public void givenAValidComment_whenTheFormIsSubmitted_ThenItIsAdded() throws InterruptedException {
        addNewCommentPage.enterTextIntoBodyField(BODY);
        listTweetPage.clickOnShowComments();

        addNewCommentPage.clickOnSubmitButton();

        assertThat(listCommentsPage.getFirstCommentBody()).isEqualTo(BODY);
    }
}
