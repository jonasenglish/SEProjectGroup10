package com.main.controller;

import com.main.App;
import com.main.objects.Hotel;
import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;

public class EmployeeViewController {

    @FXML
    private Button Button_CreateReservations;

    @FXML
    private Button Button_HotelOverview;

    @FXML
    private Button Button_Profile;

    @FXML
    private Button Button_ViewReservation;

    @FXML
    private TextField TextField_NumberOfPeople;

    @FXML
    private TextField TextField_NumberOfRooms;

    @FXML
    private TextField TextField_Total;

    @FXML
    private Label WelcomeLabel;

    @FXML
    private ChoiceBox<Hotel.RoomType> choice_RoomType;

    @FXML
    private DatePicker dp_CheckIn;

    @FXML
    private DatePicker dp_CheckOut;

    @FXML
    private ImageView profileImage;

    @FXML
    void Initialize(ContextMenuEvent event) {

    }

    @FXML
    void OnClick_CreateReservations(ActionEvent event) {
      PageManager.SetPage("CreateReservation", "Create a reservation!");

    }

    @FXML
    void OnClick_HotelOverview(ActionEvent event) {
      PageManager.SetPage("HotelOverview", "Welcome to " + App.currentHotel.getName() + "!");
    }

    @FXML
    void OnClick_Profile(ActionEvent event) {
      PageManager.SetPage("ProfileView", "Profile View for: " + App.currentHotel.getName());
    }

    @FXML
    void OnClick_ViewReservation(ActionEvent event) {
      PageManager.SetPage("ReservationView", "Reservations!");
    }

    @FXML
    void OnInput_CheckIn(ActionEvent event) {

    }

    @FXML
    void OnInput_Checkout(ActionEvent event) {

    }

    @FXML
    void OnInput_NumberOfPeople(ActionEvent event) {

    }

    @FXML
    void OnInput_NumberOfRooms(ActionEvent event) {

    }

    @FXML
    void getQuote() {

    }
}
