package application;

import com.tweets.configuration.AppConfig;
import com.tweets.service.entity.Tweet;
import fixture.TweetsFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
@WebIntegrationTest(value = "server.port=9000")
public class ITTweetsController {

    private static String PATH = "http://localhost:9000";
    private Tweet tweet;
    private RestTemplate restTemplate;

    @Before
    public void setUpTests() {
        tweet = TweetsFixture.createTweetWithTitleAndBody();
        restTemplate = new RestTemplate();
    }

    @Test
    public void givenATweet_POST_createsANewTweet() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "username=" + "John Doe");
        HttpEntity requestEntity = new HttpEntity(tweet, requestHeaders);

        Tweet newTweet = restTemplate.postForEntity(PATH + "/tweets", requestEntity, Tweet.class).getBody();

        assertThat(newTweet.getId()).isNotNull();
    }

    @Test
    public void givenATweet_GET_getsTheListOfTweets() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "username=" + "John Doe");
        HttpEntity requestEntity = new HttpEntity(tweet, requestHeaders);

        ResponseEntity<Tweet[]> responseEntity = restTemplate.exchange(PATH + "/tweets?page=0&size=5", HttpMethod.GET, requestEntity, Tweet[].class);
        Tweet[] tweets = responseEntity.getBody();

        assertThat(tweets[0].getTitle()).isNotNull();
    }
}