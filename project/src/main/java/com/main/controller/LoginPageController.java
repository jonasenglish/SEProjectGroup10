package com.main.controller;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.tools.PasswordUtils;

import org.bson.Document;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
        Parent root = null;

        try {
            root = App.pages.get(1); // Retrieving Create Account Page
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: CreateAccountPage.fxml Could not be loaded! Dumping Error:");
            System.err.println(e);
            return;
        }

        Scene scene = new Scene(root);
        
        Stage primaryStage = App.pStage; // Retrieving Primary Stage

        primaryStage.setTitle("Welcome! - Create An Account");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    @FXML
    void OnClickButton_Login(ActionEvent event) {
        // Attempt to Login with Given Credentials.
        err = false;

        String username = TextField_Username.getText();
        String password = TextField_Password.getText();

        Document accountDocument = DatabaseManager.instance.FindDocumentByUsername(username, DatabaseManager.instance.GetAccountCollection());
        if(accountDocument == null){
            Error_AccountWithUsernameNotFound();
        }

        if(err) return;

        Account account = Account.ToAccount(accountDocument);

        if(!PasswordUtils.verifyUserPassword(password, account.GetPassword(), account.GetSalt())){
            Error_PasswordIncorrect();
            return;
        }

        System.out.println("Welcome " + username + "!");
    }

    private void Error_AccountWithUsernameNotFound(){
        err = true;
        System.err.println("Could not find account with given username!");
    }

    private void Error_PasswordIncorrect(){
        err = true;
        System.err.println("Password Incorrect!");
    }

}