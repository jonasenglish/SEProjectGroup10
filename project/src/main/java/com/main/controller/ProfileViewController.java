package com.main.controller;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.pages.PageManager;
import com.main.objects.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileViewController {

    @FXML
    private Label Label_ProfileName;

    @FXML
    private TextField TextField_Email;

    @FXML
    private TextField TextField_Password;

    @FXML
    private TextField TextField_Username;

    @FXML
    void OnClick_Cancel(ActionEvent event) {
        if(App.currentUser.isManager() == false) {
            PageManager.SetPage("CustomerView", "Welcome - " + App.currentUser.getUsername());
        } else {
            PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
        }
    }

    @FXML
    void OnClick_Submit(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;
        Account account = new Account();

        TextField_Username.clear();
        TextField_Email.clear();
        TextField_Password.clear();

        //System.out.println(App.currentUser.getUsername());
        //System.out.println(App.currentUser.getPassword());
        //System.out.println(App.currentUser.getSalt());
        //System.out.println(App.currentUser.getEmail());
    }

}



