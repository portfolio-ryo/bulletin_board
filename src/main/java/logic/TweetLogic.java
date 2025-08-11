package logic;

import java.util.List;

import dao.TweetListDAO;
import model.Tweet;

/**
 * 投稿や取得に関するビジネスロジックを扱うクラス。
 */
public class TweetLogic {
	public String validateTweet(String userName, String text) {
        if (userName == null || userName.isEmpty() || text == null || text.isEmpty()) {
            return "名前と投稿内容は必須です。";
        }
        return null;
    }

    /**
     * 投稿する。
     */
	public void postTweet(String userName, String text) {
        Tweet tweet = new Tweet(userName, text);
        TweetListDAO dao = new TweetListDAO();
        dao.insert(tweet);
    }

    /**
     * すべての投稿を取得。
     */
	public List<Tweet> getAllTweets() {
        TweetListDAO dao = new TweetListDAO();
        return dao.findAll();
    }
}


