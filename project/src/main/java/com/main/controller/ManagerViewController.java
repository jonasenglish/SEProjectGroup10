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
    WelcomeLabel.setText("Welcome " + App.currentUser.getUsername() + "!");
    WelcomeLabel.setVisible(true);
    //WelcomeLabel.setText("Welcome " + App.currentUser.getUsername() + "!");
  }

	@FXML
  void OnClick_EditHotel(ActionEvent event) {
    ManagerHotelCreationController.isEdit = true;
    ManagerHotelCreationController.Instance.reinitialize();
		PageManager.SetPage("ManagerHotelCreation", "Edit Hotel");
  }

  // Added a profile action button to open the profile
  @FXML
  void OnClick_Profile(ActionEvent event) {
    PageManager.SetPage("ProfileView", "Profile");
  }
  
  @FXML
  void OnClick_Reserve(ActionEvent event) {
    CreateReservationController.Instance.selectedHotel = App.currentHotel;
    CreateReservationController.Instance.reinitialize();
	  PageManager.SetPage("CreateReservation", "Create a Reservation!");
  }

  @FXML
  void OnClick_Employees(ActionEvent event) {
    PageManager.SetPage("CreateEmployeeAccount", "Create an Employee Account");
  }

}
