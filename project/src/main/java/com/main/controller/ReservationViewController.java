package com.main.controller;
import com.main.pages.PageManager;
import com.main.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ReservationViewController {

    @FXML
    private Button Button_Checkout;

    @FXML
    private Button Button_Delete;

    @FXML
    private Button Button_Home;

    @FXML
    private Button Button_Modify;

    @FXML
    private TextField TextField_input;

    @FXML
    void OnClick_CheckOut(ActionEvent event) {

    }

    @FXML
    void OnClick_Delete(ActionEvent event) {

    }

    @FXML
    void OnClick_Home(ActionEvent event) {
      if(App.currentUser.isManager() == false) {
        PageManager.SetPage("CustomerView", "Welcome - " + App.currentUser.getUsername());
    } else {
        PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
    }
    }

    @FXML
    void OnClick_Modify(ActionEvent event) {

    }

    @FXML
    void OnInput_ReservationField(ActionEvent event) {

    }

}

