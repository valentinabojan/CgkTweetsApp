package acceptance;

import acceptance.pageobject.ListTweetPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;


public class ITListTweets extends BaseAcceptance {
    private static WebDriver driver = getDriver();
    private static ListTweetPage listTweetPage;

    @BeforeClass
    public static void setup() {
        driver.get(baseUrl);

        login();

        listTweetPage = new ListTweetPage(driver);
    }

    @Test
    public void tweetsAreListed() throws InterruptedException {
        assertThat(listTweetPage.getListOfTweets()).isNotEmpty();
    }
}
