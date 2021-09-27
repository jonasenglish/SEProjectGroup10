package com.main.objects;

import org.bson.Document;

// This Class Object represents a user account, and holds properties associated with that user, such as username, encrypted password, account id, ect.
public class Account {
    
    public Account(){
    }

    // Constructor to directly set username and password when creating an Account object;
    public Account(String username, String password, String salt, String email){
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
    }

    private String username = "";
    private String password = "";
    private String salt = ""; // Used to encrypt and decrypt the user's password
    private String email = "";

    public String GetUsername(){
        return username;
    }

    public String GetPassword(){
        return password;
    }

    public String GetSalt(){
        return salt;
    }

    public String GetEmail(){
        return email;
    }

    public void SetUsername(String username){
        this.username = username;
    }

    public void SetPassword(String password){
        this.password = password;
    }

    public void SetSalt(String salt){
        this.salt = salt;
    }

    public void SetEmail(String email){
        this.email = email;
    }

    public Document ToDocument(){
        Document document = new Document();

        document.append("username", username);
        document.append("password", password);
        document.append("salt", salt);
        document.append("email", email);

        return document;
    }

    public static Account ToAccount(Document document){
        Account account = new Account();

        account.SetUsername((String)document.get("username"));
        account.SetPassword((String)document.get("password"));
        account.SetSalt((String)document.get("salt"));
        account.SetEmail((String)document.get("email"));

        return account;
    }
}
