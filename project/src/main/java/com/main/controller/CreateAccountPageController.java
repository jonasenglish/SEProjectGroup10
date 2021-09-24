package com.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountPageController {

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
        
        
    }

}
