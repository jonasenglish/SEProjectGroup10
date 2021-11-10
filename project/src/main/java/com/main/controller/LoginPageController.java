package com.main.controller;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.pages.PageManager;
import com.main.tools.PasswordUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;


// The Controller for the Login Page
public class LoginPageController {

    private boolean err = false;

    @FXML
    private PasswordField TextField_Password;

    @FXML
    private TextField TextField_Username;

    @FXML
    private Button Button_Login;

    @FXML
    private Button Button_CreateAccount;

    @FXML
    private ProgressIndicator ProgressIndicator_Login;

    @FXML
    void OnClickButton_CreateAccount(ActionEvent event) {
        // Open Account Creation Page.
        PageManager.SetPage("CreateAccount", "Welcome! - Create An Account");
    }

    @FXML
    void OnClickButton_Login(ActionEvent event) {
        // Attempt to Login with Given Credentials.
        DatabaseManager dm = DatabaseManager.instance;

        err = false;

        String username = TextField_Username.getText();
        String password = TextField_Password.getText();

        Account account = dm.FindAccountByUsername(username);
        if(account == null){
            Error_AccountWithUsernameNotFound();
        }
        if(err) return;

        if(!PasswordUtils.verifyUserPassword(password, account.getPassword(), account.getSalt())){
            Error_PasswordIncorrect();
        }
        if(err) return;

        System.out.println("Welcome " + username + "!");
        
        App.currentUser = account;
        
        //Displays page based on account type
        if(account.isManager())
        {
            PageManager.SetPage("ManagerView", "Welcome " + username +"!");
        }
        else if(account.isEmployee())
        {
            PageManager.SetPage("EmployeeView", "Welcome " + username + "!");
        }
        else
        {
            PageManager.SetPage("CustomerView", "Welcome " + username + "!");
        }   

        TextField_Username.clear();
        TextField_Password.clear();
    }

    private void Error_AccountWithUsernameNotFound() {
        err = true;
        System.err.println("Could not find account with given username!");
    }

    private void Error_PasswordIncorrect(){
        err = true;
        System.err.println("Password Incorrect!");
    }
}