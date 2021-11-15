package com.main.controller;

import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.pages.PageManager;
import com.main.tools.PasswordUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
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
    private Button Button_Cancel;

    @FXML
    private Label Label_PasswordError;

    @FXML
    private Label Label_UsernameError;

    @FXML
    private Label Label_RetypePasswordError;

    @FXML
    private Label Label_EmailError;

    @FXML
    private RadioButton RadioButton_Manager;


    @FXML
    private void OnClickButton_CreateAccount(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;

        err = false;
        ResetErrorText();

        String username = TextField_Username.getText();
        String password = TextField_Password.getText();
        String retypePassword = TextField_RetypePassword.getText();
        String email = TextField_Email.getText();
        boolean isManager = RadioButton_Manager.isSelected();

        // Checking user input against our policies.
        if(!password.equals(retypePassword))
            Error_RetypePasswordMismatch();

        if(!email.contains("@") || !email.contains("."))
            Error_EmailFormatIncorrect();

        if(username.length() < 3 || username.length() > 20)
            Error_UsernameLength();
 
        if(password.length() < 5 || password.length() > 20)
            Error_PasswordLength();

        if(dm.FindAccountByUsername(username) != null){
            Error_AccountExists_Username();
        }

        if(dm.FindAccountByEmail(email) != null){
            Error_AccountExists_Email();
        }

        if(err) return; // Do Not Execute further if there was an error.

        // Making Secure Password
        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password, salt);

        // Verifying that the secure password was generated correctly
        if(!PasswordUtils.verifyUserPassword(password, securePassword, salt)){
            System.err.println("CRITICAL ERROR: Could not verify password with salt.");
            return;
        }

        // Creating account object
        Account newAccount = new Account(username, securePassword, salt, email, isManager);

        // Adding Account to database
        dm.InsertAccount(newAccount);

        System.out.println("Account Created!");

        if(newAccount.isManager()){
            ManagerHotelCreationController.isEdit = false;
            ManagerHotelCreationController.Instance.newManager = dm.FindAccountByUsername(newAccount.getUsername());
            ManagerHotelCreationController.Instance.reinitialize();
            PageManager.SetPage("ManagerHotelCreation", "Create your Hotel!");
            return;
        }

        PageManager.SetPage("Login", "Welcome! - Login");
    }

    @FXML
    void OnClick_Cancel(ActionEvent event) {
        PageManager.SetPage("Login", "Welcome! - Login");
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
