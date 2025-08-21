package util;

/**
 * データベース関連のユーティリティクラス。
 * JDBCドライバの初期化や共通的な接続処理に使用。
 */
public class DBUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Loaded");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBCドライバのロードに失敗しました", e);
        }
    }

    // JDBCドライバを強制的にロードさせたい場合に呼ぶ（何もしない）
    public static void init() {
        // do nothing
    }
}
