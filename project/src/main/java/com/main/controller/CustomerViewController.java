package com.main.controller;

import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    private Button Button_roomsPlus;

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
    private Text welcomeHeader;

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
		PageManager.SetPage("ReservationView", "View reservations");
	}

    @FXML
    void OnClick_RoomsMinus(ActionEvent event) {

    }

    @FXML
    void OnClick_Search(ActionEvent event) {

    }

    @FXML
    void OnClick_adultMinus(ActionEvent event) {

    }

    @FXML
    void OnClick_adultPlus(ActionEvent event) {

    }

    @FXML
    void OnClick_childMinus(ActionEvent event) {

    }

    @FXML
    void OnClick_childPlus(ActionEvent event) {

    }

    @FXML
    void OnClick_idkwhat(ActionEvent event) {

    }

    @FXML
    void OnClick_plus(ActionEvent event) {

    }

}

