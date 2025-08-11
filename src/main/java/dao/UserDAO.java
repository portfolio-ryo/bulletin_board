package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;
import model.User;

/**
 * ユーザー情報のデータベースアクセスを担当するクラス。
 * ユーザーの登録、重複チェック、ログイン認証処理を行う。
 */
public class UserDAO {
	private String jdbcUrl;
	private String dbUser;
	private String dbPass;
	
	public UserDAO() {
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
	
	public UserDAO(String jdbcUrl, String dbUser, String dbPass) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

	/**
     * 指定されたユーザー名がすでに登録されているかを確認する。
     */
	public boolean duplication(String name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {
				String sql = "SELECT COUNT(*) FROM users WHERE name = ?";

				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, name);

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							return rs.getInt(1) > 0;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); // その他のエラー
		}
		return false;
	}

	/**
     * ユーザー情報をデータベースに登録する。
     */
	public boolean insert(User user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {
				String sql = "INSERT INTO users(NAME,PASS) VALUES ( ? , ?)";

				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, user.getName());
					ps.setString(2, user.getHashedPass());
					int result = ps.executeUpdate();
					return result > 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
     * ユーザー名とパスワードを照合し、ログイン認証を行う。
     */
	public boolean login(User user) {
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {
				String sql = "SELECT pass FROM users WHERE name = ?";

				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, user.getName());

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							String hashedPass = rs.getString("pass");

	                        // パスワード照合（平文 vs ハッシュ）
	                        result = util.PasswordUtil.checkPassword(user.getPlainPass(), hashedPass);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // 本番環境ではログに出すべき
		}

		return result;

	}
}
