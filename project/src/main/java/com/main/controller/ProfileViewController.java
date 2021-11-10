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
    private TextField TextField_Email;

    @FXML
    private PasswordField PasswordField_Password;

    @FXML
    private TextField TextField_Username;

    @FXML
    void OnClick_Logout(ActionEvent event) {
        PageManager.SetPage("Login", "Welcome!");
    }

    @FXML
    void OnClick_Cancel(ActionEvent event) {
        if(App.currentUser.isManager() == false) {
            PageManager.SetPage("CustomerView", "Welcome - " + App.currentUser.getUsername());
        } else {
            PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
        }
    }

    @FXML
    void OnClick_Edit(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;
        Account account = dm.FindAccountByUsername(App.currentUser.getUsername());

        TextField_Username.setText(App.currentUser.getUsername());
        TextField_Email.setText(App.currentUser.getEmail());

        System.out.println(account.getPassword() + "\nsalt: " + account.getSalt());
    }

    @FXML
    void OnClick_Submit(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;
        Account account = dm.FindAccountByUsername(App.currentUser.getUsername());

        if(PasswordUtils.verifyUserPassword(PasswordField_Password.getText(), account.getPassword(), account.getSalt()) ) {
            String new_username = TextField_Username.getText();
            String new_email = TextField_Email.getText();

            System.out.println("Account info updated!\n" + new_username);
            System.out.println(new_email);
        }
        else {
            System.out.println("Wrong Password! Unable to change user info");
        }

        TextField_Username.clear();
        TextField_Email.clear();
        PasswordField_Password.clear();
    }

}



