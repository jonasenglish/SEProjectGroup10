package com.main.controller;

import com.main.App;
import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//Controller for the manager View
public class ManagerViewController {

	@FXML
  private Button Button_CreateHotel;

  @FXML
  private Button Button_Profile;

  @FXML
  private Label WelcomeLabel;

  @FXML
  public void Initialize(ActionEvent onContextMenuRequested) {
    WelcomeLabel.setText("Welcome");
  }

	@FXML
  void OnClick_CreateHotel(ActionEvent event) {
		PageManager.SetPage("ManagerHotelCreation", "Create Hotel");
  }

  // Added a profile action button to open the profile
  @FXML
  void OnClick_Profile(ActionEvent event) {
    PageManager.SetPage("ProfileView", "Profile");
  }
  
  @FXML
  void OnClick_Reserve(ActionEvent event) {
	  PageManager.SetPage("CreateReservation", "Create a Reservation!");
  }

}
