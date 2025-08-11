package model;

import java.io.Serializable;

/**
 * ユーザー情報を表すクラス。
 */
public class User implements Serializable {
    private String name;
    private String plainPass;
    private String hashedPass;

    public User() {}

    public User(String name, String plainPass) {
        this.name = name;
        this.plainPass = plainPass;
    }

    public User(String name, String hashedPass, boolean dummy) {
        this.name = name;
        this.hashedPass = hashedPass;
    }

    public String getName() {
        return name;
    }

    public String getPlainPass() {
        return plainPass;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlainPass(String plainPass) {
        this.plainPass = plainPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }
}
