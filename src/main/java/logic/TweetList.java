package logic;

import java.util.List;

import dao.TweetListDAO;
import model.Tweet;

/**
 * 投稿一覧の取得を行うクラス。
 */
public class TweetList {
	public List<Tweet> getAllTweets() {
        TweetListDAO dao = new TweetListDAO();
        return dao.findAll();
    }

}
