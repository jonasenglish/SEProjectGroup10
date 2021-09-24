package com.main.objects;

// This Class Object represents a user account, and holds properties associated with that user, such as username, encrypted password, account id, ect.
public class Account {
    
    public Account(){
    }

    // Constructor to directly set username and password when creating an Account object;
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    // Incase accountID needs to be manually set.
    public Account(String username, String password, int accountID){
        this.username = username;
        this.password = password;
        this.accountID = accountID;
    }

    private String username = "";
    private String password = "";
    private int accountID = -1;

    public String GetUsername(){
        return username;
    }

    public String GetPassword(){
        return password;
    }

    public int GetAccountID(){
        return accountID;
    }

    public void SetUsername(String username){
        this.username = username;
    }

    public void SetPassword(String password){
        this.password = password;
    }

    public void SetAccountID(int accountID){
        this.accountID = accountID;
    }
}
