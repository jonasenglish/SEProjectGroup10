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
        resetErrorDisplay();
        PageManager.SetPage("Login", "Welcome!");
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
        resetErrorDisplay();
        TextField_Username.setVisible(true);
        TextField_Email.setVisible(true);
        PasswordField_Password.setVisible(true);

        TextField_Username.setText(App.currentUser.getUsername());
        TextField_Email.setText(App.currentUser.getEmail());
    }

    @FXML
    void OnClick_Submit(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;
        Account account = dm.FindAccountByUsername(App.currentUser.getUsername());

        String new_username = TextField_Username.getText();
        String new_email = TextField_Email.getText();
        resetErrorDisplay();

        if(new_username.length() > 3 && new_username.length() < 20) {
            if(new_email.contains("@") && new_email.contains(".")) {
                if(PasswordUtils.verifyUserPassword(PasswordField_Password.getText(), account.getPassword(), account.getSalt()) ) {
                    if(dm.FindAccountByUsername(new_username) != null) { // check database if username is taken
                        if(new_username.equals(App.currentUser.getUsername())) { // check if username is taken but not theirs
                            // username stays the same or new username was entered
                            account.setUsername(new_username);
                            account.setEmail(new_email);
                            dm.UpdateAccount(account);

                            System.out.println("Account info updated!\n" + new_username);
                            System.out.println(new_email);
                            clearFields();
                        } 
                        else { //error username is taken, not theirs either
                            usernameError_AlreadyExists();
                            return;
                        }
                    }
                    
                    if(dm.FindAccountByEmail(new_email) != null) { // check database if email is taken
                        if(new_email.equals(App.currentUser.getEmail())) { // check if email is taken but not theirs
                            // email stays the same or new email was entered
                            account.setUsername(new_username);
                            account.setEmail(new_email);
                            dm.UpdateAccount(account);

                            System.out.println("Account info updated!\n" + new_username);
                            System.out.println(new_email);
                            clearFields();
                        }
                        else {
                            emailError_AlreadyExists();
                            return;
                        }
                    }
                }
                else {
                    passwordError_Incorrect();
                    PasswordField_Password.clear();
                }
            }
            else {
                emailError_IncorrectFormat();
            }
        }
        else {
            usernameError_IncorrectFormat();
        }
    }
    
    private void usernameError_AlreadyExists() {
        Label_UsernameError.setVisible(true);
        Label_UsernameError.setText("This username has already been taken!");
    }

    private void usernameError_IncorrectFormat() {
        Label_UsernameError.setVisible(true);
        Label_UsernameError.setText("Usernames must be between 3 and 20 characters.");
    }

    private void emailError_AlreadyExists() {
        Label_EmailError.setVisible(true);
        Label_EmailError.setText("This email already belongs to another account!");
    }

    private void emailError_IncorrectFormat() {
        Label_EmailError.setVisible(true);
        Label_EmailError.setText("This is not a valid email!");
    }

    private void passwordError_Incorrect() {
        Label_PasswordError.setVisible(true);
        Label_PasswordError.setText("Incorrect Password");
    }

}



