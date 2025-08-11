package logic;

import dao.UserDAO;
import model.User;

/**
 * ログインに関する処理を行うクラス。
 */
public class LoginLogic {
	/**
     * 入力チェック
     */
    public boolean isInvalidInput(String name, String pass) {
        return name == null || name.isEmpty() || pass == null || pass.isEmpty();
    }

    /**
     * 認証チェック
     */
    public boolean authenticate(User user) {
        UserDAO dao = new UserDAO();
        return dao.login(user);
    }
}


