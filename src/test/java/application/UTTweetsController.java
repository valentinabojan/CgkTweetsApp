package application;

import com.tweets.application.controller.TweetsController;
import com.tweets.service.TweetsService;
import com.tweets.service.entity.Tweet;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.valueobject.PageParams;
import fixture.TweetsFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UTTweetsController {

    private final static String AUTHOR = "John Doe";

    @InjectMocks
    private TweetsController tweetsController;

    @Mock
    private TweetsService mockTweetsService;

    @Test
    public void givenAnInvalidTweet_createTweet_returns400BADREQUEST() {
        Tweet invalidTweet = TweetsFixture.createTweetWithoutTitle();
        Mockito.when(mockTweetsService.createNewTweet(invalidTweet, AUTHOR)).thenThrow(ValidationException.class);

        ResponseEntity response = tweetsController.postNewTweet(invalidTweet, AUTHOR);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

//    @Test
//    public void givenATweetAndNoAuthorLoggedIn_createTweet_returns401UNAUTHORIZED() {
//        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
//
//        ResponseEntity response = tweetsController.postNewTweet(tweet, "");
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }

    @Test
    public void givenATweet_createTweet_returns200OK() {
        Tweet tweet = TweetsFixture.createTweetWithoutTitle();
        Mockito.when(mockTweetsService.createNewTweet(tweet, AUTHOR)).thenReturn(tweet);

        ResponseEntity response = tweetsController.postNewTweet(tweet, AUTHOR);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void givenATweet_createTweet_returnsTheNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithoutTitle();
        Mockito.when(mockTweetsService.createNewTweet(tweet, AUTHOR)).thenReturn(tweet);

        ResponseEntity response = tweetsController.postNewTweet(tweet, AUTHOR);

        assertThat((Tweet)response.getBody()).isEqualTo(tweet);
    }

    @Test
    public void givenATweet_findTweet_returns200OK() {
        Tweet tweet = TweetsFixture.createTweetWithoutTitle();
        List<Tweet> expectedTweets = new ArrayList<>();
        expectedTweets.add(tweet);
        Mockito.when(mockTweetsService.findTweets(new PageParams(0, 10))).thenReturn(expectedTweets);

        ResponseEntity response = tweetsController.getTweets(AUTHOR, 0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
