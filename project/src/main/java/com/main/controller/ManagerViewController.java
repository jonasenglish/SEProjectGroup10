package com.main.controller;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//Controller for the manager View
public class ManagerViewController implements Initializable{

  public static ManagerViewController Instance;

	@FXML
  private Button Button_CreateHotel;

  @FXML
  private Button Button_Profile;

  @FXML
  private Label WelcomeLabel;

  @FXML 
  private TextArea Text_employees;


  
  public void Initialize() {
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


  @Override
  public void initialize(URL location, ResourceBundle resources) {
      

    Instance = this;
  }

  public void viewEmployees(){
    DatabaseManager dm = DatabaseManager.instance; 
    List<Account> employees = dm.FindEmployeeAccountsByHotelID(App.currentUser.getHotelID()); 
    String emp = "";
    for(Account account : employees){
      String currEmp = account.getUsername();
      emp += ("- " + currEmp + "\n");
    }

    Text_employees.setText(emp);
  }
  
  public void reinitialize(){
    
  }


}
