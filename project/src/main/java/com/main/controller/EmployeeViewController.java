package com.main.controller;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.objects.Amenity;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.objects.Hotel.RoomType;
import com.main.pages.PageManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;

import org.bson.types.ObjectId;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

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
    private ChoiceBox<?> choice_RoomType;

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
      PageManager.SetPage("HotelOverview", "Welcome to " + App.currentUser.getHotel().getName() + "!");
    }

    @FXML
    void OnClick_Profile(ActionEvent event) {
      PageManager.SetPage("ProfileView", "Profile View for: " + App.currentUser.getUsername());
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
