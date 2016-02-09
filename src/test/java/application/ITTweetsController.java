package application;

import com.tweets.configuration.AppConfig;
import com.tweets.service.entity.Comment;
import com.tweets.service.entity.Tweet;
import fixture.TweetsFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppConfig.class)
@WebIntegrationTest(value = "server.port=9000")
public class ITTweetsController {

    private static String PATH = "http://localhost:9000";
    private HttpStatus postStatus;
    private HttpStatus postCommentStatus;
    private RestTemplate restTemplate;
    private Tweet mostRecentTweet;
    private Comment comment;

    @Before
    public void setUpTests() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        comment = TweetsFixture.createCommentWithBody();
        restTemplate = new RestTemplate();

        postStatus = restTemplate.postForEntity(PATH + "/tweets", new HttpEntity(tweet), Tweet.class).getStatusCode();

        ResponseEntity<Tweet[]> responseEntity = restTemplate.getForEntity(PATH + "/tweets?page=0&size=5", Tweet[].class);
        mostRecentTweet = responseEntity.getBody()[0];
        postCommentStatus = restTemplate.postForEntity(PATH + "/tweets/" + mostRecentTweet.getId() + "/comments", new HttpEntity(comment), Comment.class).getStatusCode();
    }

    @Test
    public void givenATweet_POST_createsANewTweet() {
        assertThat(postStatus).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void givenAComment_POST_createsANewComment() {
        assertThat(postCommentStatus).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void givenATweet_GET_getsTheListOfTweets() {
        ResponseEntity<Tweet[]> responseEntity = restTemplate.getForEntity(PATH + "/tweets?page=0&size=5", Tweet[].class);
        Tweet[] tweets = responseEntity.getBody();

        assertThat(tweets[0].getTitle()).isNotNull();
    }

    @Test
    public void givenATweetId_GET_getsTheListOfTweetComments() {
        ResponseEntity<Comment[]> responseEntity = restTemplate.getForEntity(PATH + "/tweets/" + mostRecentTweet.getId() + "/comments?page=0&size=5", Comment[].class);
        Comment[] comments = responseEntity.getBody();

        assertThat(comments[0].getBody()).isEqualTo(comment.getBody());
    }
}
