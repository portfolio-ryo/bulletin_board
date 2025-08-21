package logic;

import java.util.List;

import dao.TweetListDAO;
import model.Tweet;

/**
 * 投稿や取得に関するビジネスロジックを扱うクラス。
 * 
 * <p>投稿内容のバリデーション、投稿処理、投稿一覧の取得などを行う。</p>
 */
public class TweetLogic {
	
	/**
     * ユーザー名と投稿内容のバリデーションを行う。
     *
     * @param userName 投稿者の名前
     * @param text 投稿内容
     * @return エラーメッセージ（問題がなければ null）
     */
	public String validateTweet(String userName, String text) {
        if (userName == null || userName.isEmpty() || text == null || text.isEmpty()) {
            return "名前と投稿内容は必須です。";
        }
        return null;
    }

	/**
     * 投稿をデータベースに登録する。
     *
     * @param userName 投稿者の名前
     * @param text 投稿内容
     */
	public void postTweet(String userName, String text) {
        Tweet tweet = new Tweet(userName, text);
        TweetListDAO dao = new TweetListDAO();
        dao.insert(tweet);
    }

	/**
     * すべての投稿を取得する。
     *
     * @return 投稿のリスト
     */
	public List<Tweet> getAllTweets() {
        TweetListDAO dao = new TweetListDAO();
        return dao.findAll();
    }
}


