package logic;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

/**
 * ユーザー登録に関するビジネスロジックを扱うクラス。
 *
 * <p>入力値のバリデーション、重複チェック、登録処理などを行う。</p>
 */
public class RegisterLogic {

    /**
     * ユーザー名とパスワードの入力値を検証する。
     *
     * @param name ユーザー名
     * @param plainPass 入力されたプレーンパスワード
     * @return エラーメッセージ（正しければ null）
     */
    public String validate(String name, String plainPass) {
        if (name == null || name.isEmpty() || plainPass == null || plainPass.isEmpty()) {
            return "ユーザー名とパスワードは必須です。";
        }
        if (plainPass.length() < 4) {
            return "パスワードは4文字以上で入力してください。";
        }
        return null;
    }

    /**
     * 同じユーザー名がすでに存在するかを確認する。
     *
     * @param name チェック対象のユーザー名
     * @return true：重複あり、false：重複なし
     */
    public boolean isDuplicateUser(String name) {
        UserDAO dao = new UserDAO();
        return dao.duplication(name);
    }

    /**
     * パスワードをハッシュ化して、ユーザーをデータベースに登録する。
     *
     * @param name ユーザー名
     * @param plainPass 入力されたプレーンパスワード
     * @return 登録成功なら true、失敗なら false
     */
    public boolean registerUser(String name, String plainPass) {
        String hashedPass = PasswordUtil.hashPassword(plainPass);
        User user = new User(name, hashedPass, true);

        UserDAO dao = new UserDAO();
        return dao.insert(user);
    }
}
