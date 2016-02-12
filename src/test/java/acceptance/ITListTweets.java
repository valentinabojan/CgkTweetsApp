package acceptance;

import acceptance.pageobject.AddNewTweetPage;
import acceptance.pageobject.ListTweetPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;


public class ITListTweets extends BaseAcceptance {
    private static WebDriver driver = getDriver();
    private static ListTweetPage listTweetPage;
    private static AddNewTweetPage addNewTweetPage;

    private static String TITLE = "Default title";
    private static String BODY = "Default body";

    @BeforeClass
    public static void setup() {
        driver.get(baseUrl);

        login();

        listTweetPage = new ListTweetPage(driver);
        addNewTweetPage = new AddNewTweetPage(driver, getWait());

        addNewTweetPage.enterTextIntoTitleField(TITLE);
        addNewTweetPage.enterTextIntoBodyField(BODY);
        addNewTweetPage.clickOnSubmitButton();
    }

    @AfterClass
    public static void tearDown() {
        addNewTweetPage.clickOnLogoutButton();
    }

    @Test
    public void tweetsAreListed() throws InterruptedException {
        assertThat(listTweetPage.getListOfTweets()).isNotEmpty();
    }

    @Test
    public void whenLikeATweet_increaseNumberOfLikes() throws InterruptedException {
        Integer initialLikesNumber = listTweetPage.getFirstTweetLikesNumber();

        listTweetPage.clickOnFirstTweetLikeButton();

        Integer likesNumber = listTweetPage.getFirstTweetLikesNumber();
        assertThat(likesNumber).isEqualTo(initialLikesNumber + 1);
    }

    @Test
    public void whenDislikeATweet_increaseNumberOfDislikes() throws InterruptedException {
        Integer initialDislikesNumber = listTweetPage.getFirstTweetDislikesNumber();

        listTweetPage.clickOnFirstTweetDislikeButton();

        Integer dislikesNumber = listTweetPage.getFirstTweetDislikesNumber();
        assertThat(dislikesNumber).isEqualTo(initialDislikesNumber + 1);
    }
}
