package com.main.objects;

import org.bson.Document;
import org.bson.types.ObjectId;

// This Class Object represents a user account, and holds properties associated with that user, such as username, encrypted password, account id, ect.
public class Account {
    
    public Account(){
    }

    private String username = "";
    private String password = "";
    private String salt = ""; // Used to encrypt and decrypt the user's password
    private String email = "";
    private boolean isManager = false;
    private boolean isDeveloper = false; // Whether or not this is a Developer account.
    private boolean isEmployee = false;
    
    private ObjectId hotelID; // Unique ID generated by MongoDB, this value is set when the object is retrieved from the Database.

    private ObjectId ID; // Unique ID generated by MongoDB, this value is set when the object is retrieved from the Database.

    public Account(String username, String password, String salt, String email, boolean isManager) {
        this.username = username;
        this.password = password;
        this.salt =salt;
        this.email = email;
        this.isManager = isManager;
    }
    
    public Boolean getDeveloper(){
        return isDeveloper;
    }
    public void setDeveloper(boolean isDeveloper){
        this.isDeveloper = isDeveloper;
    }

    public boolean isManager() {
        return isManager;
    }
    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }
    
    public boolean isEmployee() {
        return isEmployee;
    }
    public void setEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public ObjectId getHotelID() {
        return hotelID;
    }
    public void setHotelID(ObjectId hotelID) {
        this.hotelID = hotelID;
    }
    
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getSalt(){
        return salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public ObjectId getID() {
        return ID;
    }
    public void setID(ObjectId iD) {
        this.ID = iD;
    }

    public Document ToDocument(){
        Document document = new Document();
        
        document.append("username", username);
        document.append("password", password);
        document.append("salt", salt);
        document.append("email", email);
        document.append("isManager", isManager);
        document.append("isEmployee", isEmployee);
        document.append("isDeveloper", isDeveloper);
        if(isManager || isEmployee)
            document.append("hotelID", hotelID);
        
        return document;
    }
    
    public static Account ToAccount(Document document){
        Account account = new Account();

        account.setUsername((String)document.get("username"));
        account.setPassword((String)document.get("password"));
        account.setSalt((String)document.get("salt"));
        account.setEmail((String)document.get("email"));
        account.setManager((boolean)document.get("isManager"));
        account.setDeveloper((boolean)document.get("isDeveloper"));
        account.setEmployee((boolean)document.get("isEmployee"));
        if(account.isManager || account.isEmployee)
            account.setHotelID(((ObjectId)document.get("hotelID")));

        account.setID((ObjectId)document.get("_id"));

        return account;
    }

    public String toString(){
        String returnString = "Username: " + username +
        "\nEmail: " + email +
        "\nAccount ID: " + ID.toString();

        return returnString;
    }
}
