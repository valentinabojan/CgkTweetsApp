package fixture;

import com.tweets.service.entity.Comment;
import com.tweets.service.entity.Tweet;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TweetsFixture {

    public static String TWEET_TITLE = "Title";
    public static String TWEET_BODY = "Body";
    public static String AUTHOR = "Somebody";

    public static Tweet createTweetWithTitleAndBody() {
        return Tweet.TweetBuilder.tweet()
                .withTitle(TWEET_TITLE)
                .withBody(TWEET_BODY)
                .withComments(new ArrayList<>())
                .withUsersWhoLiked(new ArrayList<>())
                .withUsersWhoDisliked(new ArrayList<>())
                .withDate(LocalDateTime.now())
                .withAuthor(AUTHOR)
                .build();
    }

    public static Tweet createTweetWithoutTitle() {
        return Tweet.TweetBuilder.tweet()
                .withBody(TWEET_BODY)
                .withComments(new ArrayList<>())
                .withUsersWhoLiked(new ArrayList<>())
                .withUsersWhoDisliked(new ArrayList<>())
                .withDate(LocalDateTime.now())
                .withAuthor(AUTHOR)
                .build();
    }

    public static Tweet createTweetWithoutBody() {
        return Tweet.TweetBuilder.tweet()
                .withTitle(TWEET_TITLE)
                .withComments(new ArrayList<>())
                .withUsersWhoLiked(new ArrayList<>())
                .withUsersWhoDisliked(new ArrayList<>())
                .withDate(LocalDateTime.now())
                .withAuthor(AUTHOR)
                .build();
    }

    public static Comment createCommentWithoutBody() {
        return Comment.CommentBuilder.comment()
                .withDate(LocalDateTime.now())
                .withAuthor(AUTHOR)
                .build();
    }

    public static Comment createCommentWithBody() {
        return Comment.CommentBuilder.comment()
                .withDate(LocalDateTime.now())
                .withAuthor(AUTHOR)
                .withBody(TWEET_BODY)
                .build();
    }
}
