package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;
import model.User;

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

	public boolean insert(User user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)) {
				String sql = "INSERT INTO users(NAME,PASS) VALUES ( ? , ?)";

				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, user.getName());
					ps.setString(2, user.getPass());
					int result = ps.executeUpdate();
					return result > 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

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
	                        result = util.PasswordUtil.checkPassword(user.getPass(), hashedPass);
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
