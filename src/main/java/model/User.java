package model;

import java.io.Serializable;

/**
 * ユーザー情報を表すクラス。
 * 
 * <p>ユーザー名、平文パスワード、ハッシュ化されたパスワードを保持する。</p>
 */
public class User implements Serializable {
    private String name;
    private String plainPass;
    private String hashedPass;

    /**
     * デフォルトコンストラクタ。
     */
    public User() {}

    /**
     * ユーザー名と平文パスワードを指定してユーザーを生成する。
     * 主にログインや登録時の入力データとして使用される。
     *
     * @param name      ユーザー名
     * @param plainPass 平文パスワード
     */
    public User(String name, String plainPass) {
        this.name = name;
        this.plainPass = plainPass;
    }

    /**
     * ユーザー名とハッシュ化されたパスワードを指定してユーザーを生成する。
     * 主にデータベースから取得した情報を表現するために使用される。
     *
     * @param name       ユーザー名
     * @param hashedPass ハッシュ化されたパスワード
     * @param dummy      オーバーロード区別用のダミーパラメータ（使用しない）
     */
    public User(String name, String hashedPass, boolean dummy) {
        this.name = name;
        this.hashedPass = hashedPass;
    }

    /**
     * ユーザー名を取得する。
     *
     * @return ユーザー名
     */
    public String getName() {
        return name;
    }

    /**
     * 平文パスワードを取得する。
     *
     * @return 平文パスワード
     */
    public String getPlainPass() {
        return plainPass;
    }

    /**
     * ハッシュ化されたパスワードを取得する。
     *
     * @return ハッシュ化されたパスワード
     */
    public String getHashedPass() {
        return hashedPass;
    }

    /**
     * ユーザー名を設定する。
     *
     * @param name ユーザー名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 平文パスワードを設定する。
     *
     * @param plainPass 平文パスワード
     */
    public void setPlainPass(String plainPass) {
        this.plainPass = plainPass;
    }

    /**
     * ハッシュ化されたパスワードを設定する。
     *
     * @param hashedPass ハッシュ化されたパスワード
     */
    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }
}
