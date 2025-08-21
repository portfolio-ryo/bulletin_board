package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * パスワードのハッシュ化および照合を行うユーティリティクラス。
 *
 * <p>ユーザー登録時のパスワードのハッシュ化や、ログイン時のパスワード認証に使用する。</p>
 */
public class PasswordUtil {
	
	/**
     * 平文のパスワードをハッシュ化する。
     *
     * @param plainPassword ハッシュ化する平文のパスワード
     * @return ハッシュ化されたパスワード
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // セキュリティ強度：12
    }

    /**
     * 平文のパスワードとハッシュ済みパスワードを照合する。
     *
     * @param plainPassword  入力された平文のパスワード
     * @param hashedPassword 保存されているハッシュ済みパスワード
     * @return 一致する場合は true、そうでない場合は false
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

