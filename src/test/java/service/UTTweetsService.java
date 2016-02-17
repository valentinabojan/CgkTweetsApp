package service;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.repository.mongo.TweetsRepositoryMongo;
import com.tweets.service.TweetsService;
import com.tweets.service.UserSecurityDetailsService;
import com.tweets.service.model.Tweet;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.exception.ValidationException;
import com.tweets.service.valueobject.PageParams;
import fixture.TweetsFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UTTweetsService {

    @InjectMocks
    private TweetsService service;

    @Mock
    private TweetsRepositoryMongo mockRepository;

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
        CommentMongo comment = TweetsFixture.createCommentWithoutBody();

        service.createNewComment(tweet.getId(), comment);
    }

    @Test
    public void givenATweetAndACommentWithBody_createComment_createsTheComment() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        CommentMongo comment = TweetsFixture.createCommentWithBody();
        tweet.setId("2");
        List<CommentMongo> comments = new ArrayList<>();
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
        List<CommentMongo> comments = new ArrayList<>();
        comments.add(TweetsFixture.createCommentWithBody());
        Mockito.when(mockRepository.findCommentsByTweet("1", new PageParams(0, 10))).thenReturn(comments);

        List<CommentMongo> foundComments = service.findTweetComments("1", new PageParams(0, 10));

        assertThat(foundComments.get(0).getBody()).isEqualTo(comments.get(0).getBody());
    }

    @Test
    public void givenATweetAlreadyLiked_likeTheTweet_theTweetDoesNotChange() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        tweet.getUsersWhoLiked().add(AUTHOR);
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);
        Mockito.when(mockRepository.findTweetById("1")).thenReturn(tweet);

        TweetTO likedTweet = service.likeTweet("1");

        assertThat(likedTweet).isEqualTo(new TweetTO(tweet));
    }

    @Test
    public void givenATweetAlreadyDisliked_likeTheTweet_theUserWhoLikedIsRemovedFromDislikes() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        tweet.getUsersWhoDisliked().add(AUTHOR);
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);
        Mockito.when(mockRepository.findTweetById("1")).thenReturn(tweet);
        Mockito.when(mockRepository.updateTweet(tweet)).thenReturn(tweet);

        TweetTO likedTweet = service.likeTweet("1");

        assertThat(likedTweet.getUsersWhoDislikedCount()).isEqualTo(0);
    }

    @Test
    public void givenATweet_likeTheTweet_theListWithUsersWhoLikedIsUpdated() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);
        Mockito.when(mockRepository.findTweetById("1")).thenReturn(tweet);
        Mockito.when(mockRepository.updateTweet(tweet)).thenReturn(tweet);

        TweetTO likedTweet = service.likeTweet("1");

        assertThat(likedTweet.getUsersWhoLikedCount()).isEqualTo(1);
    }

    @Test
    public void givenATweetAlreadyDisliked_dislikeTheTweet_theTweetDoesNotChange() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        tweet.getUsersWhoDisliked().add(AUTHOR);
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);
        Mockito.when(mockRepository.findTweetById("1")).thenReturn(tweet);

        TweetTO likedTweet = service.dislikeTweet("1");

        assertThat(likedTweet).isEqualTo(new TweetTO(tweet));
    }


    @Test
    public void givenATweetAlreadyLiked_disLikeTheTweet_theUserWhoDislikedIsRemovedFromLikes() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        tweet.getUsersWhoLiked().add(AUTHOR);
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);
        Mockito.when(mockRepository.findTweetById("1")).thenReturn(tweet);
        Mockito.when(mockRepository.updateTweet(tweet)).thenReturn(tweet);

        TweetTO dislikedTweet = service.dislikeTweet("1");

        assertThat(dislikedTweet.getUsersWhoLikedCount()).isEqualTo(0);
    }

    @Test
    public void givenATweet_dislikeTheTweet_theListWithUsersWhoDislikedIsUpdated() {
        Tweet tweet = TweetsFixture.createTweetWithTitleAndBody();
        Mockito.when(mockUserService.getPrincipalName()).thenReturn(AUTHOR);
        Mockito.when(mockRepository.findTweetById("1")).thenReturn(tweet);
        Mockito.when(mockRepository.updateTweet(tweet)).thenReturn(tweet);

        TweetTO dislikedTweet = service.dislikeTweet("1");

        assertThat(dislikedTweet.getUsersWhoDislikedCount()).isEqualTo(1);
    }
}
