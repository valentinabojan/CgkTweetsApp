package repository;

import com.tweets.configuration.AppConfig;
import com.tweets.repository.TweetsRepository;
import com.tweets.service.entity.Tweet;
import fixture.TweetsFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        Tweet newTweet = tweetsRepository.insert(tweet);

        assertThat(newTweet.getId()).isNotNull();
        assertThat(newTweet.getTitle()).isEqualTo(tweet.getTitle());
    }
}
