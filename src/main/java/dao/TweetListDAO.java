package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import model.Tweet;

/**
 * 投稿情報をデータベースから操作するDAOクラス。
 * 投稿の登録および全件取得の処理を担当する。
 */
public class TweetListDAO {
	private String jdbcUrl;
    private String dbUser;
    private String dbPass;
    
    public TweetListDAO() {
    	Dotenv dotenv = Dotenv.configure()
                .directory("") 
                .load();

        this.jdbcUrl = "jdbc:mysql://localhost:3306/bulletin_board?serverTimezone=Asia/Tokyo";
        this.dbUser = dotenv.get("DB_USER");
        this.dbPass = dotenv.get("DB_PASS");

        if (dbUser == null || dbPass == null) {
            throw new IllegalStateException("環境変数が読み込めませんでした");
        }
    }
	
    public TweetListDAO(String jdbcUrl, String dbUser, String dbPass) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }
    
    /**
     * 投稿情報をデータベースに挿入する。
     */
	public void insert(Tweet tweet) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {
                String sql = "INSERT INTO tweets (NAME, TEXT) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, tweet.getUserName());
                    ps.setString(2, tweet.getText());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
     * 投稿を全件取得し、投稿日時の降順で返す。
     */
	public List<Tweet> findAll() {
        List<Tweet> tweetList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {
                String sql = "SELECT ID, NAME, TEXT, POST_TIME FROM tweets ORDER BY id DESC";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String name = rs.getString("NAME");
                        String text = rs.getString("TEXT");
                        Timestamp ts = rs.getTimestamp("POST_TIME");
                        LocalDateTime postTime = ts.toLocalDateTime();
                        Tweet tweet = new Tweet(id, name, text, postTime);
                        tweetList.add(tweet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tweetList;
    }
}

