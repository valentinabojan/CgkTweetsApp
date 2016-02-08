package repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.configuration.AppConfig;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Comment;
import com.tweets.service.entity.Tweet;
import com.tweets.service.valueobject.PageParams;
import fixture.TweetsFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ITTweetsRepository {

    @Autowired
    private TweetsRepository tweetsRepository;

    private Tweet tweet;
    private TweetTO newTweet;
    private Comment comment;

    @Before
    public void setUp() {
        tweet = TweetsFixture.createTweetWithTitleAndBody();
        TweetsFixture.createTweetWithTitleAndBody();
        comment = TweetsFixture.createCommentWithBody();

        newTweet = tweetsRepository.insert(tweet);
    }

    @Test
    public void givenANewTweet_createTweet_createsNewTweet() {
        assertThat(newTweet.getId()).isNotNull();
        assertThat(newTweet.getTitle()).isEqualTo(tweet.getTitle());
    }

    @Test
    public void givenATweetList_findTweets_findTheTweets() {
        List<TweetTO> foundTweets = tweetsRepository.findAllByOrderByDateDesc(new PageParams(0, 10));

        assertThat(foundTweets.size()).isGreaterThanOrEqualTo(2);
        assertThat(foundTweets.get(0).getId()).isNotNull();
        assertThat(foundTweets.get(0).getTitle()).isEqualTo(tweet.getTitle());
    }

    @Test
    public void givenATweetList_findTweets_findTheTweetsSortedInDescendingOrderByDate() {
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
        // TODO replace "1" with newTweet id, after posting 2 comments for it; it would be nice to post the comments in setup method

        List<Comment> foundComments = tweetsRepository.findCommentsByTweet("1", new PageParams(0, 10));

        assertThat(foundComments).hasSize(2);
    }

    @Test
    public void givenATweetId_findComments_findTheCommentsSortedInDescendingOrderByDate() {
        // TODO replace "1" with newTweet id, after posting 2 comments for it; it would be nice to post the comments in setup method

        List<Comment> foundComments = tweetsRepository.findCommentsByTweet("1", new PageParams(0, 10));

        Comment firstComment = foundComments.get(0);
        Comment secondComment = foundComments.get(1);
        assertThat(firstComment.getDate()).isAfter(secondComment.getDate());
    }

    @Test
    public void givenATweetId_findComments_findOnlyOnePageOfComments() {
        // TODO replace "1" with newTweet id, after posting 2 comments for it; it would be nice to post the comments in setup method

        List<Comment> foundComments = tweetsRepository.findCommentsByTweet("1", new PageParams(0, 1));

        assertThat(foundComments).hasSize(1);
    }

    @Test
    public void givenANewComment_createComment_createsNewComment() {
        Tweet newTweet = tweetsRepository.insertComment(tweet, TweetsFixture.createCommentWithBody());

        assertThat(newTweet.getComments().get(0)).isNotNull();
        assertThat(newTweet.getComments().get(0).getBody()).isEqualTo(comment.getBody());
    }
}
