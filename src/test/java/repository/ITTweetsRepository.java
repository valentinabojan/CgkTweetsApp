package repository;

import com.tweets.application.transferobject.TweetTO;
import com.tweets.configuration.AppConfig;
import com.tweets.repository.TweetsRepository;
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

    @Before
    public void setUp() {
        tweet = TweetsFixture.createTweetWithTitleAndBody();
    }

    @Test
    public void givenANewTweet_createTweet_createsNewTweet() {
        TweetTO newTweet = tweetsRepository.insert(tweet);

        assertThat(newTweet.getId()).isNotNull();
        assertThat(newTweet.getTitle()).isEqualTo(tweet.getTitle());
    }

    @Test
    public void givenATweet_findByDateOrderByDateDesc_findTweet() {
        List<TweetTO> foundTweet = tweetsRepository.findAllByOrderByDateDesc(new PageParams(0, 10));

        assertThat(foundTweet.get(0).getId()).isNotNull();
        assertThat(foundTweet.get(0).getTitle()).isEqualTo(tweet.getTitle());
    }

}
