package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 投稿情報を表すクラス。
 * 
 * <p>投稿のID、投稿者の名前、内容、投稿日時を保持する。</p>
 */
public class Tweet implements Serializable{
	private int id;
	private String userName;
	private String text;
	private LocalDateTime postTime;
	
	/**
	 * デフォルトコンストラクタ。
	 */
	public Tweet() {}
	
	/**
	 * ユーザー名と投稿内容を指定してTweetを生成する。
	 * 主に投稿フォームの入力から作成される。
	 *
	 * @param userName 投稿者の名前
	 * @param text 投稿内容
	 */
	public Tweet(String userName,String text) {
		this.userName=userName;
		this.text=text;
	}
	
	/**
	 * すべての情報を指定してTweetを生成する。
	 * 主にデータベースから取得したデータの再現に使用される。
	 *
	 * @param id 投稿ID
	 * @param userName 投稿者の名前
	 * @param text 投稿内容
	 * @param postTime 投稿日時
	 */
	public Tweet(int id,String userName,String text,LocalDateTime postTime) {
		this.id=id;
		this.userName=userName;
		this.text=text;
		this.postTime=postTime;
	}
	
	/**
	 * 投稿IDを取得する。
	 *
	 * @return 投稿ID
	 */
	public int getId() {return id;}
	
	/**
	 * 投稿者の名前を取得する。
	 *
	 * @return 投稿者の名前
	 */
	public String getUserName() {return userName;}
	
	/**
	 * 投稿内容を取得する。
	 *
	 * @return 投稿内容
	 */
	public String getText() {return text;}
	
	/**
	 * 投稿日時を取得する。
	 *
	 * @return 投稿日時
	 */
	public LocalDateTime getPostTime() {return postTime;}

}
