package com.main.controller;

import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.tools.PasswordUtils;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountPageController {

    private boolean err = false; // Tracks if there was an Error when creating the account;

    @FXML
    private PasswordField TextField_Password;

    @FXML
    private PasswordField TextField_RetypePassword;

    @FXML
    private TextField TextField_Email;

    @FXML
    private TextField TextField_Username;

    @FXML
    private Button Button_CreateAccount;

    @FXML
    private Label Label_PasswordError;

    @FXML
    private Label Label_UsernameError;

    @FXML
    private Label Label_RetypePasswordError;

    @FXML
    private Label Label_EmailError;

    @FXML
    private void OnClickButton_CreateAccount(ActionEvent event) {
        err = false;
        ResetErrorText();

        String username = TextField_Username.getText();
        String password = TextField_Password.getText();
        String retypePassword = TextField_RetypePassword.getText();
        String email = TextField_Email.getText();
        
        // Checking user input against our policies.
        if(!password.equals(retypePassword))
            Error_RetypePasswordMismatch();

        if(!email.contains("@") || !email.contains("."))
            Error_EmailFormatIncorrect();

        if(username.length() < 3 || username.length() > 20)
            Error_UsernameLength();
 
        if(password.length() < 5 || password.length() > 20)
            Error_PasswordLength();

        MongoCollection<Document> accountCollection = DatabaseManager.instance.GetAccountCollection();

        if(DatabaseManager.instance.FindDocumentByUsername(username, accountCollection) != null){
            Error_AccountExists_Username();
        }

        if(DatabaseManager.instance.FindDocumentByEmail(email, accountCollection) != null){
            Error_AccountExists_Email();
        }

        if(err) return; // Do Not Execute further if there was an error.

        String salt = PasswordUtils.getSalt(30);

        String securePassword = PasswordUtils.generateSecurePassword(password, salt);

        if(!PasswordUtils.verifyUserPassword(password, securePassword, salt)){
            System.err.println("CRITICAL ERROR: Could not verify password with salt.");
            return;
        }

        Account newAccount = new Account(username, securePassword, salt, email);

        accountCollection.insertOne(newAccount.ToDocument());

        System.out.println("Account Created!");
    }

    private void ResetErrorText(){
        Label_EmailError.setVisible(false);
        Label_PasswordError.setVisible(false);
        Label_RetypePasswordError.setVisible(false);
        Label_UsernameError.setVisible(false);
    }

    private void Error_RetypePasswordMismatch(){
        err = true;
        Label_RetypePasswordError.setText("The passwords do not match! Try retyping the passwords.");
        Label_RetypePasswordError.setVisible(true);
    }

    private void Error_EmailFormatIncorrect(){
        err = true;
        Label_EmailError.setText("The Email Address given is not in a valid format.");
        Label_EmailError.setVisible(true);
    }

    private void Error_UsernameLength(){
        err = true;
        Label_UsernameError.setText("Usernames must be between 3 and 20 characters.");
        Label_UsernameError.setVisible(true);
    }

    private void Error_PasswordLength(){
        err = true;
        Label_PasswordError.setText("Passwords must be between 5 and 20 characters.");
        Label_PasswordError.setVisible(true);
    }

    private void Error_AccountExists_Username(){
        err = true;
        Label_UsernameError.setText("An Account with that Username already Exists!");
        Label_UsernameError.setVisible(true);
    }

    private void Error_AccountExists_Email(){
        err = true;
        Label_EmailError.setText("An Account with that Email already Exists!");
        Label_EmailError.setVisible(true);
    }

}
