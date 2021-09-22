package com.project;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;


// The Controller for the Login Page
public class LoginPageController {

    @FXML
    private PasswordField TextField_Password;

    @FXML
    private TextField TextField_Username;

    @FXML
    private Button Button_Login;

    @FXML
    private ProgressIndicator ProgressIndicator_Login;

    @FXML
    private void OnClickButton_Login(Event e){
        ProgressIndicator_Login.setProgress(1);

        TextField_Username.setText("Hello World!");

        System.out.println("Hello World!");
    }
}