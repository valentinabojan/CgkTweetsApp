package application;

import com.tweets.application.controller.TweetsController;
import com.tweets.application.transferobject.TweetTO;
import com.tweets.service.TweetsService;
import com.tweets.service.entity.Comment;
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

    @InjectMocks
    private TweetsController tweetsController;

    @Mock
    private TweetsService mockTweetsService;

    @Test
    public void givenAnInvalidTweet_createTweet_returns400BADREQUEST() {
        Tweet invalidTweet = TweetsFixture.createTweetWithoutTitle();
        Mockito.when(mockTweetsService.createNewTweet(invalidTweet)).thenThrow(ValidationException.class);

        ResponseEntity response = tweetsController.postNewTweet(invalidTweet);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenATweet_createTweet_returns201OK() {
        Tweet tweet = TweetsFixture.createTweetWithoutTitle();
        Mockito.when(mockTweetsService.createNewTweet(tweet)).thenReturn(new TweetTO(tweet));

        ResponseEntity response = tweetsController.postNewTweet(tweet);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void givenNoTweets_getTweets_returns404NOTFOUND() {
        List<TweetTO> expectedTweets = new ArrayList<>();
        Mockito.when(mockTweetsService.findTweets(new PageParams(0, 10))).thenReturn(expectedTweets);

        ResponseEntity response = tweetsController.getTweets(0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenATweet_getTweets_returns200OK() {
        List<TweetTO> expectedTweets = new ArrayList<>();
        expectedTweets.add(new TweetTO(TweetsFixture.createTweetWithoutTitle()));
        Mockito.when(mockTweetsService.findTweets(new PageParams(0, 10))).thenReturn(expectedTweets);

        ResponseEntity response = tweetsController.getTweets(0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenATweet_getTweets_returnsTheCorrectEntity() {
        List<TweetTO> expectedTweets = new ArrayList<>();
        expectedTweets.add(new TweetTO(TweetsFixture.createTweetWithoutTitle()));
        Mockito.when(mockTweetsService.findTweets(new PageParams(0, 10))).thenReturn(expectedTweets);

        ResponseEntity response = tweetsController.getTweets(0, 10);

        assertThat(response.getBody()).isEqualTo(expectedTweets);
    }

    @Test
    public void givenNoComments_getTweetComments_returns404NOTFOUND() {
        List<Comment> expectedComments = new ArrayList<>();
        Mockito.when(mockTweetsService.findTweetComments("1", new PageParams(0, 10))).thenReturn(expectedComments);

        ResponseEntity response = tweetsController.getTweetComments("1", 0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenAComment_findTweetComments_returns200OK() {
        List<Comment> expectedComments = new ArrayList<>();
        expectedComments.add(TweetsFixture.createCommentWithBody());
        Mockito.when(mockTweetsService.findTweetComments("1", new PageParams(0, 10))).thenReturn(expectedComments);

        ResponseEntity response = tweetsController.getTweetComments("1", 0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenATweet_findTweets_returnsTheCorrectEntity() {
        List<Comment> expectedComments = new ArrayList<>();
        expectedComments.add(TweetsFixture.createCommentWithBody());
        Mockito.when(mockTweetsService.findTweetComments("1", new PageParams(0, 10))).thenReturn(expectedComments);

        ResponseEntity response = tweetsController.getTweetComments("1", 0, 10);

        assertThat(response.getBody()).isEqualTo(expectedComments);
    }
}
