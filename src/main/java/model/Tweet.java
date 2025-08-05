package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Tweet implements Serializable{
	private int id;
	private String userName;
	private String text;
	private LocalDateTime postTime;
	
	public Tweet() {}
	public Tweet(String userName,String text) {
		this.userName=userName;
		this.text=text;
	}
	public Tweet(int id,String userName,String text,LocalDateTime postTime) {
		this.id=id;
		this.userName=userName;
		this.text=text;
		this.postTime=postTime;
	}
	public int getId() {return id;}
	public String getUserName() {return userName;}
	public String getText() {return text;}
	public LocalDateTime getPostTime() {return postTime;}

}
