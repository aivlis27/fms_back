package com.cognizant.fms.bean;

public class User {
private long userId ;
private String fname;
private String lname;
private int age;
private String gender;

public long getUserId() {
	return userId;
}
public void setUserId(long userId) {
	this.userId = userId;
}
public String getFname() {
	return fname;
}
public void setFname(String fname) {
	this.fname = fname;
}
public String getLname() {
	return lname;
}
public void setLname(String lname) {
	this.lname = lname;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
} 
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}

}