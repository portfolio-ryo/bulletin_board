package model;

import java.io.Serializable;

public class User implements Serializable{
	private String name;
	private String hashedPass;
	
	public User() {}
	public User(String name,String hashedPass) {
	this.name=name;
	this.hashedPass=hashedPass;
	}
	public String getName() {
		return name;
	}
	public String getPass() {
		return hashedPass;
	}
}
