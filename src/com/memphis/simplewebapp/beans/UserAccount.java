package com.memphis.simplewebapp.beans;

public class UserAccount {

    public static final String GENDER_MALE = "M";
    public static final String GENDER_FEMALE = "F";

    public UserAccount(String userName, String gender, String password) {
        this.userName = userName;
        this.gender = gender;
        this.password = password;
    }

    private String userName;
    private String gender;
    private String password;


    public UserAccount() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
