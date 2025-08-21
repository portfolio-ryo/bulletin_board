package logic;

import dao.UserDAO;
import model.User;

/**
 * ログインに関するビジネスロジックを扱うクラス。
 *
 * <p>ユーザー入力の検証と、認証処理を担当する。</p>
 */
public class LoginLogic {

    /**
     * ユーザー名とパスワードの入力が空かどうかをチェックする。
     *
     * @param name ユーザー名
     * @param pass パスワード
     * @return true：いずれかが空、false：両方入力されている
     */
    public boolean isInvalidInput(String name, String pass) {
        return name == null || name.isEmpty() || pass == null || pass.isEmpty();
    }

    /**
     * 入力されたユーザー情報が正しいかを認証する。
     *
     * @param user 入力されたユーザー情報（名前とプレーンパスワード）
     * @return 認証成功：true、失敗：false
     */
    public boolean authenticate(User user) {
        UserDAO dao = new UserDAO();
        return dao.login(user);
    }
}
