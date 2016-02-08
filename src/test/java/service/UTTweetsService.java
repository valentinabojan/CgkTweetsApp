package service;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.TweetsService;
import com.tweets.service.UserSecurityDetailsService;
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
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UTTweetsService {

    @InjectMocks
    private TweetsService service;

    @Mock
    private TweetsRepository mockRepository;

    @Mock
    private UserSecurityDetailsService mockUserService;

    private static String AUTHOR = "John Doe";

    @Test
    public void givenAValidTweet_createTweet_returnsTheNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockRepository.insert(tweet)).thenReturn(tweet);

        TweetTO newTweet = service.createNewTweet(tweet);

        assertThat(newTweet.getTitle()).isEqualTo(tweet.getTitle());
        assertThat(newTweet.getBody()).isEqualTo(tweet.getBody());
    }

    @Test
    public void givenAValidTweet_createTweet_setsDateForNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockRepository.insert(tweet)).thenReturn(tweet);

        TweetTO newTweet = service.createNewTweet(tweet);

        assertThat(newTweet.getDate()).isNotNull();
    }

    @Test
    public void givenAValidTweet_createTweet_setsAuthorForNewTweet() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockRepository.insert(tweet)).thenReturn(tweet);
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);

        TweetTO newTweet = service.createNewTweet(tweet);

        assertThat(newTweet.getAuthor()).isNotEmpty();
    }

    @Test(expected = ValidationException.class)
    public void givenATweetWithoutTitle_createTweet_throwsValidationException() {
        Tweet tweet = TweetsFixture.createTweetWithoutTitle();

        service.createNewTweet(tweet);
    }

    @Test(expected = ValidationException.class)
    public void givenATweetWithoutBody_createTweet_throwsValidationException() {
        Tweet tweet = TweetsFixture.createTweetWithoutBody();

        service.createNewTweet(tweet);
    }

    @Test(expected = ValidationException.class)
    public void givenATweetAndACommentWithoutBody_createComment_throwsValidationException() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Comment comment = TweetsFixture.createCommentWithoutBody();

        service.createNewComment(tweet.getId(), comment);
    }

    @Test
    public void givenATweetAndACommentWithBody_createComment_createsTheComment() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Comment comment = TweetsFixture.createCommentWithBody();
        tweet.setId("2");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        tweet.setComments(comments);
        Mockito.when(mockRepository.insertComment(tweet.getId(), comment)).thenReturn(tweet);

        service.createNewComment(tweet.getId(), comment);

        assertThat(tweet.getComments().get(0).getBody()).isEqualTo(comment.getBody());
    }

    @Test
    public void givenATweet_findAllByOrderByDateDesc_returnTweet() {
        List<TweetTO> tweets = new ArrayList<>();
        TweetTO tweet = new TweetTO(TweetsFixture.createTweetWithTitleAndBody());
        tweets.add(tweet);
        Mockito.when(mockRepository.findAllByOrderByDateDesc(new PageParams(0, 10))).thenReturn(tweets);

        List<TweetTO> foundTweets = service.findTweets(new PageParams(0, 10));

        assertThat(foundTweets.get(0).getTitle()).isEqualTo(tweet.getTitle());
        assertThat(foundTweets.get(0).getBody()).isEqualTo(tweet.getBody());
    }

    @Test
    public void givenATweetId_findAllTweetComments_returnTheComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(TweetsFixture.createCommentWithBody());
        Mockito.when(mockRepository.findCommentsByTweet("1", new PageParams(0, 10))).thenReturn(comments);

        List<Comment> foundComments = service.findTweetComments("1", new PageParams(0, 10));

        assertThat(foundComments.get(0).getBody()).isEqualTo(comments.get(0).getBody());
    }

}
