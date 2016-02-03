package service;

import com.tweets.repository .TweetsRepository;
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
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UTTweetsService {

    @InjectMocks
    private TweetsService service;

    @Mock
    private TweetsRepository mockRepository;

    @Test
    public void givenAValidTweet_createTweet_returnsTheNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockRepository.insert(tweet)).thenReturn(tweet);

        Tweet newTweet = service.createNewTweet(tweet, "John Doe");

        assertThat(newTweet.getTitle()).isEqualTo(tweet.getTitle());
        assertThat(newTweet.getBody()).isEqualTo(tweet.getBody());
    }

    @Test
    public void givenAValidTweet_createTweet_setsDateForNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockRepository.insert(tweet)).thenReturn(tweet);

        Tweet newTweet = service.createNewTweet(tweet, "John Doe");

        assertThat(newTweet.getDate()).isNotNull();
    }

    @Test
    public void givenAValidTweet_createTweet_setsAuthorForNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockRepository.insert(tweet)).thenReturn(tweet);

        Tweet newTweet = service.createNewTweet(tweet, "John Doe");

        assertThat(newTweet.getAuthor()).isNotEmpty();
    }

    @Test(expected = ValidationException.class)
    public void givenATweetWithoutTitle_createTweet_throwsValidationException() {
        Tweet tweet = TweetsFixture.createTweetWithoutTitle();

        service.createNewTweet(tweet, "John Doe");
    }

    @Test(expected = ValidationException.class)
    public void givenATweetWithoutBody_createTweet_throwsValidationException() {
        Tweet tweet = TweetsFixture.createTweetWithoutBody();

        service.createNewTweet(tweet, "John Doe");
    }

    @Test
    public void givenATweet_findAllByOrderByDateDesc_returnTweet() {
        List<Tweet> tweets = new ArrayList<>();
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        tweets.add(tweet);
        Mockito.when(mockRepository.findAllByOrderByDateDesc(new PageRequest(0, 10))).thenReturn(tweets);

        service.findTweets(new PageParams(0, 10));

        assertThat(tweets.get(0).getTitle()).isEqualTo(tweet.getTitle());
        assertThat(tweets.get(0).getBody()).isEqualTo(tweet.getBody());
    }

}
