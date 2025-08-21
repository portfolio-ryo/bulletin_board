package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;
import model.User;
import util.DBUtil;


/**
 * ユーザー情報のデータベースアクセスを担当するクラス。
 * ユーザーの登録、重複チェック、ログイン認証処理を行う。
 */
public class UserDAO {
	private String jdbcUrl;
	private String dbUser;
	private String dbPass;
	
	/**
     * 環境変数(.env)からDB接続情報を読み込み、
     * JDBCドライバの初期化を行うデフォルトコンストラクタ。
     * 
     * @throws IllegalStateException DB_USERまたはDB_PASSが.envに設定されていない場合にスローされる
     */
	public UserDAO() {
		DBUtil.init();
		
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
     * 
     * @param name 確認したいユーザー名
     * @return 登録済みであればtrue、未登録ならfalse
     */
	public boolean duplication(String name) {
		try {

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
     * 
     * @param user 登録するユーザー情報（ユーザー名、ハッシュ化済みパスワードを含む）
     * @return 登録成功ならtrue、失敗した場合はfalse
     */
	public boolean insert(User user) {
		try {

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
     * 
     * @param user ログイン認証対象のユーザー情報（ユーザー名と平文パスワード）
     * @return 認証成功でtrue、失敗でfalse
     */
	public boolean login(User user) {
		boolean result = false;

		try {

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
