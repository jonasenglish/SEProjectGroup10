package com.main.controller;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.pages.PageManager;
import com.main.tools.PasswordUtils;
import com.main.objects.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileViewController{

    private boolean err = false;

    @FXML
    private Label Label_ProfileName;

    @FXML
    private Label Label_UsernameError;

    @FXML
    private Label Label_EmailError;

    @FXML
    private Label Label_PasswordError;

    @FXML
    private TextField TextField_Email;

    @FXML
    private PasswordField PasswordField_Password;

    @FXML
    private TextField TextField_Username;

    private void resetErrorDisplay() {
        Label_UsernameError.setVisible(false);
        Label_EmailError.setVisible(false);
        Label_PasswordError.setVisible(false);
    }

    private void clearFields() {
        TextField_Username.clear();
        TextField_Email.clear();
        PasswordField_Password.clear();
    }

    @FXML
    void OnClick_Logout(ActionEvent event) {
        clearFields();
        PageManager.SetPage("Login", "Welcome!");

        resetErrorDisplay();
    }

    @FXML
    void OnClick_Cancel(ActionEvent event) {
        clearFields();
        resetErrorDisplay();
        App.AccountTypeView();
    }

    @FXML
    void OnClick_Edit(ActionEvent event) {
        //DatabaseManager dm = DatabaseManager.instance;
        //Account account = dm.FindAccountByUsername(App.currentUser.getUsername());

        TextField_Username.setText(App.currentUser.getUsername());
        TextField_Email.setText(App.currentUser.getEmail());

        //System.out.println(account.getPassword() + "\nsalt: " + account.getSalt());
    }

    @FXML
    void OnClick_Submit(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;
        Account account = dm.FindAccountByUsername(App.currentUser.getUsername());
        resetErrorDisplay();

        String new_username = TextField_Username.getText();
        String new_email = TextField_Email.getText();

        if(new_username.length() < 3 || new_username.length() > 20)
                usernameError_IncorrectFormat();
            
        if(!new_email.contains("@") || !new_email.contains("."))
                emailError_IncorrectFormat();

        if(err) return;

        if(PasswordUtils.verifyUserPassword(PasswordField_Password.getText(), account.getPassword(), account.getSalt()) ) {

            if(dm.FindAccountByUsername(new_username) != null) { // check database if username is taken
                if(new_username.equals(App.currentUser.getUsername())) { // check if username is taken but not theirs
                    // username stays the same, update email info
                    
                } 
                else { //error username is taken, not theirs either
                    usernameError_AlreadyExists();
                    return;
                }
            }

            if(dm.FindAccountByEmail(new_email) != null) { // check database if email is taken
                if(new_email.equals(App.currentUser.getEmail())) { // check if email is taken but not theirs
                    // email stays the same, update username

                }
                else {
                    emailError_AlreadyExists();
                    return;
                }
            }

            System.out.println("Account info updated!\n" + new_username);
            System.out.println(new_email);

            clearFields();
        }
        else {
            passwordError_Incorrect();
            PasswordField_Password.clear();
            return;
        }
    }

    private void usernameError_AlreadyExists() {
        err = true;
        Label_UsernameError.setVisible(true);
        Label_UsernameError.setText("This username has already been taken!");
    }

    private void usernameError_IncorrectFormat() {
        err = true;
        Label_UsernameError.setVisible(true);
        Label_UsernameError.setText("Usernames must be between 3 and 20 characters.");
    }

    private void emailError_AlreadyExists() {
        err = true;
        Label_EmailError.setVisible(true);
        Label_EmailError.setText("This email already belongs to another account!");
    }

    private void emailError_IncorrectFormat() {
        err = true;
        Label_EmailError.setVisible(true);
        Label_EmailError.setText("This is not a valid email!");
    }

    private void passwordError_Incorrect() {
        err = true;
        Label_PasswordError.setVisible(true);
        Label_PasswordError.setText("Incorrect Password");
    }

}



