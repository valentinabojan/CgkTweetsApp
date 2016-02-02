package acceptance;

import acceptance.pageobject.AddNewTweetPage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAddNewTweet extends BaseAcceptance {

    private static WebDriver driver = getDriver();
    private static AddNewTweetPage addNewTweetPage;

    private static String TITLE = "Default title";
    private static String BODY = "Default body";

    @BeforeClass
    public static void setup() {
        driver.get(baseUrl + "#/login");

        login();

        addNewTweetPage = new AddNewTweetPage(driver, getWait());
    }

    @Test
    public void givenATweetWithoutTitle_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addNewTweetPage.clickOnSubmitButton();

        assertThat(addNewTweetPage.getTitleRequiredMessage()).isNotNull();
    }

    @Test
    public void givenATweetWithoutBody_whenTheFormIsSubmitted_ThenSubmitFormFails() {
        addNewTweetPage.enterTextIntoTitleField(TITLE);

        addNewTweetPage.clickOnSubmitButton();

        assertThat(addNewTweetPage.getBodyRequiredMessage()).isNotNull();
    }

    @Test
    public void givenAValidTweet_whenTheFormIsSubmitted_ThenItIsAdded() throws InterruptedException {
        addNewTweetPage.enterTextIntoTitleField(TITLE);
        addNewTweetPage.enterTextIntoBodyField(BODY);

        addNewTweetPage.clickOnSubmitButton();

        // TODO Assert that the new tweet appears in the list above the form
//        assertThat(memberDetailsPage.getNameText()).isEqualTo("John Doe");
    }
}
