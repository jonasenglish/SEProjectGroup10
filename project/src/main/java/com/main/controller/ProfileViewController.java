package com.main.controller;

import com.main.App;
import com.main.pages.PageManager;

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
        PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
    }

    //@FXML
    //public void initialize(){
    //    Label_ProfileName.setText(App.currentUser.getUsername());
    //}
    
}
