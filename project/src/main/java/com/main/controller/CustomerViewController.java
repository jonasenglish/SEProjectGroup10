package com.main.controller;

import com.main.App;
import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CustomerViewController {

    @FXML
    private Button Button_Favorites;

    @FXML
    private Button Button_Profile;

    @FXML
    private Button Button_Reservation;

    @FXML
    private Button Button_RoomsMinus;

    @FXML
    private Button Button_roomsPlus;

    @FXML
    private Button Button_Search;

    @FXML
    private Button Button_adultMinus;

    @FXML
    private Button Button_adultPlus;

    @FXML
    private Button Button_childMinus;

    @FXML
    private Button Button_childPlus;

    @FXML
    private Button Button_idkwhat;


    @FXML
    private TextField Location;

    @FXML
    private TextField PropertyName;

    @FXML
    private DatePicker dp_CheckInDate;

    @FXML
    private DatePicker dp_CheckOutDate;

    @FXML
    private Text numAdults;

    @FXML
    private Text numChildren;

    @FXML
    private Text numRooms;

    @FXML
    private Label welcomeLabel;

    private int numR = 0;
    private int numA = 0;
    private int numC = 0;


  public void Initialize() {
    welcomeLabel.setText("Welcome " + App.currentUser.getUsername() + "!");
  }

  @FXML
  void OnClick_Favorites(ActionEvent event) {

  }

    //Go to the profile view (should have the same options as the manager)
	@FXML
	void OnClick_Profile(ActionEvent event) {
		PageManager.SetPage("ProfileView", "Profile");
	}

    //Supposed to take you to see the reservations you made
	@FXML
	void OnClick_Reserve(ActionEvent event) {
    ReservationViewController.Instance.init_customer();
		PageManager.SetPage("ReservationView", "View reservations");
	}

    @FXML
    void OnClick_RoomsPlus(ActionEvent event){
        numR += 1;
        String s = String.valueOf(numR);
        numRooms.setText(s);
    }

    @FXML
    void OnClick_RoomsMinus(ActionEvent event) {
        if(numR > 0){
            numR -= 1;
            String s = String.valueOf(numR);
            numRooms.setText(s);
        }
    }

    @FXML
    void OnClick_Search(ActionEvent event) {

    }

    @FXML
    void OnClick_adultMinus(ActionEvent event) {
        if(numA > 0){
            numA -= 1;
            String s = String.valueOf(numA);
            numAdults.setText(s);
        }
    }

    @FXML
    void OnClick_adultPlus(ActionEvent event) {
        numA += 1;
        String s = String.valueOf(numA);
        numAdults.setText(s);
    }

    @FXML
    void OnClick_childMinus(ActionEvent event) {
        if(numC > 0){
            numC -= 1;
            String s = String.valueOf(numC);
            numChildren.setText(s);
        }
    }

    @FXML
    void OnClick_childPlus(ActionEvent event) {
        numC += 1;
        String s = String.valueOf(numC);
        numChildren.setText(s);
    }

    @FXML
    void OnClick_HotelSearch(ActionEvent event) {
        PageManager.SetPage("HotelSearchView", "Search for a Hotel!");
    }

    @FXML
    void OnClick_plus(ActionEvent event) {

    }

}

