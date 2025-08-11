package logic;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

/**
 * ユーザー登録に関する処理を行うクラス。
 */
public class RegisterLogic {

	/**
	 * ユーザー名とパスワードの入力値を検証
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
	 * ユーザー名の重複の確認。
	 */
	public boolean isDuplicateUser(String name) {
		UserDAO dao = new UserDAO();
		return dao.duplication(name);
	}

	/**
	 * パスワードをハッシュ化してユーザーを登録。
	 */
	public boolean registerUser(String name, String plainPass) {
		String hashedPass = PasswordUtil.hashPassword(plainPass);
		User user = new User(name, hashedPass, true);

		UserDAO dao = new UserDAO();
		return dao.insert(user);
	}

}
