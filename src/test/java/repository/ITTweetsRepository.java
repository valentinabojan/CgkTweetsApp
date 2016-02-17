package repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.configuration.AppConfig;
import com.tweets.repository.TweetsRepository;
import com.tweets.repository.cassandra.TweetsRepositoryCassandra;
import com.tweets.service.entity.Tweet;
import com.tweets.service.entity.mongo.CommentMongo;
import com.tweets.service.entity.cassandra.TweetCassandra;
import com.tweets.service.valueobject.PageParams;
import fixture.TweetsFixture;
import org.apache.xpath.operations.Bool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@ActiveProfiles("cassandra")
public class ITTweetsRepository {

    @Autowired
    private TweetsRepository tweetsRepository;

    private Tweet tweet, newTweet;
    private CommentMongo comment1, comment2;

    @Before
    public void setUp() {
        tweet = TweetsFixture.createTweetWithTitleAndBody();
        newTweet = tweetsRepository.insert(tweet);

        comment1 = TweetsFixture.createCommentWithBody();
        comment2 = TweetsFixture.createCommentWithBody();
        comment2.setDate(LocalDateTime.now().plus(10, ChronoUnit.MILLIS));
//        newTweet = tweetsRepository.insertComment(newTweet.getId(), comment1);
//        newTweet = tweetsRepository.insertComment(newTweet.getId(), comment2);
    }

    @Test
    public void givenANewTweet_createTweet_createsNewTweet() {
        assertThat(newTweet.getId()).isNotNull();
        assertThat(newTweet.getTitle()).isEqualTo(tweet.getTitle());
    }

    @Test
    public void givenATweet_updateTweet_updatesTheTweet() {
        newTweet.getUsersWhoLiked().add("john");
        Tweet updatedTweet = tweetsRepository.updateTweet(newTweet);
        assertThat(updatedTweet.getUsersWhoLiked()).contains("john");
    }

    @Test
    public void givenATweetList_findTweets_findTheTweets() {
        List<TweetTO> foundTweets = tweetsRepository.findAllByOrderByDateDesc(new PageParams(0, 10));

        assertThat(foundTweets.size()).isGreaterThanOrEqualTo(1);
        assertThat(foundTweets.get(0).getId()).isNotNull();
        assertThat(foundTweets.get(0).getTitle()).isEqualTo(tweet.getTitle());
    }

    @Test
    public void givenATweetList_findTweets_findTheTweetsSortedInDescendingOrderByDate() {
        tweetsRepository.insert(TweetsFixture.createTweetWithTitleAndBody());

        List<TweetTO> foundTweets = tweetsRepository.findAllByOrderByDateDesc(new PageParams(0, 10));

        TweetTO firstTweet = foundTweets.get(0);
        TweetTO secondTweet = foundTweets.get(1);
        assertThat(firstTweet.getDate()).isAfter(secondTweet.getDate());
    }

    @Test
    public void givenATweetList_findTweets_findOnlyOnePageOfTweets() {
        List<TweetTO> foundTweets = tweetsRepository.findAllByOrderByDateDesc(new PageParams(0, 1));

        assertThat(foundTweets).hasSize(1);
    }

    @Test
    public void givenATweetId_findComments_findTheComments() {
        List<CommentMongo> foundComments = tweetsRepository.findCommentsByTweet(newTweet.getId(), new PageParams(0, 10));

        assertThat(foundComments).hasSize(2);
    }

    @Test
    public void givenATweetId_findComments_findTheCommentsSortedInDescendingOrderByDate() {
        List<CommentMongo> foundComments = tweetsRepository.findCommentsByTweet(newTweet.getId(), new PageParams(0, 10));

        CommentMongo firstComment = foundComments.get(0);
        CommentMongo secondComment = foundComments.get(1);
        assertThat(firstComment.getDate()).isAfter(secondComment.getDate());
    }

    @Test
    public void givenATweetId_findComments_findOnlyOnePageOfComments() {
        List<CommentMongo> foundComments = tweetsRepository.findCommentsByTweet(newTweet.getId(), new PageParams(0, 1));

        assertThat(foundComments).hasSize(1);
    }

    @Test
    public void givenANewComment_createComment_createsNewComment() {
        Tweet newTweetWithComments = tweetsRepository.insertComment(newTweet.getId(), TweetsFixture.createCommentWithBody());

        assertThat(newTweetWithComments.getComments().get(0)).isNotNull();
        assertThat(newTweetWithComments.getComments().get(0).getBody()).isEqualTo(comment2.getBody());
    }

    @Test
    public void givenATweetAndAUsernameWhoLikedTheTweet_isTweetLikedByUser_returnsTrue() {
        newTweet.setUsersWhoLiked(Arrays.asList("John Doe"));
        Tweet likedTweet = tweetsRepository.updateTweet(newTweet);

        Boolean isTweetLiked = tweetsRepository.isTweetLiked(likedTweet.getId(), "John Doe");

        assertThat(isTweetLiked).isTrue();
    }

    @Test
    public void givenATweetAndAUsernameWhoDidNotLikedTheTweet_isTweetLikedByUser_returnsFalse() {
        newTweet.setUsersWhoLiked(Arrays.asList());
        Tweet likedTweet = tweetsRepository.updateTweet(newTweet);

        Boolean isTweetLiked = tweetsRepository.isTweetLiked(likedTweet.getId(), "John Doe");

        assertThat(isTweetLiked).isFalse();
    }

    @Test
    public void givenATweetAndAUsernameWhoDislikedTheTweet_isTweetDislikedByUser_returnsTrue() {
        newTweet.setUsersWhoDisliked(Arrays.asList("John Doe"));
        Tweet dislikedTweet = tweetsRepository.updateTweet(newTweet);

        Boolean isTweetDisliked = tweetsRepository.isTweetDisliked(dislikedTweet.getId(), "John Doe");

        assertThat(isTweetDisliked).isTrue();
    }

    @Test
    public void givenATweetAndAUsernameWhoDidNotDislikedTheTweet_isTweetDislikedByUser_returnsFalse() {
        newTweet.setUsersWhoDisliked(Arrays.asList());
        Tweet dislikedTweet = tweetsRepository.updateTweet(newTweet);

        Boolean isTweetDisliked = tweetsRepository.isTweetDisliked(dislikedTweet.getId(), "John Doe");

        assertThat(isTweetDisliked).isFalse();
    }
}
