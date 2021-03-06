package com.main.controller;

import java.util.List;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.objects.Hotel;
import com.main.pages.PageManager;
import com.main.tools.PasswordUtils;
import com.main.tools.PopupHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


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

       if(account.isManager() || account.isEmployee()) {
            Hotel hotel = dm.FindHotelByID(account.getHotelID());
            if(hotel == null && account.isManager()){
                ManagerHotelCreationController.isEdit = false;
                ManagerHotelCreationController.Instance.newManager = account;
                ManagerHotelCreationController.Instance.reinitialize();
                PageManager.SetPage("ManagerHotelCreation", "Create your Hotel!");
                return;
            }

            App.currentHotel = hotel;
        }
        
        //Displays page based on account type
        App.AccountTypeView();

        TextField_Username.clear();
        TextField_Password.clear();
    }

    
    @FXML
    void OnEnter_Login(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
            OnClickButton_Login(null);
    }

    private void Error_AccountWithUsernameNotFound() {
        err = true;
        PopupHandler.ShowError("Could not find account with given Username!");
        System.err.println("Could not find account with given username!");
    }

    private void Error_PasswordIncorrect(){
        err = true;
        PopupHandler.ShowError("Password does not match given Username!");
        System.err.println("Password Incorrect!");
    }
}