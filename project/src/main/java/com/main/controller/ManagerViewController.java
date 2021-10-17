package com.main.controller;

import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

//Controller for the manager View
public class ManagerViewController {

	@FXML
    private Button Button_CreateHotel;

	@FXML
    void OnClick_CreateHotel(ActionEvent event) {
		PageManager.SetPage("ManagerHotelCreation", "Create Hotel");
    }

}
