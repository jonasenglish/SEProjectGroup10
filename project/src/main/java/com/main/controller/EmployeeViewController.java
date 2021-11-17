package com.main.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Date;
import java.util.EventListener;

import com.main.App;
import com.main.pages.PageManager;
import com.main.objects.Account;
import com.main.database.DatabaseManager;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.tools.PopupHandler;
import com.main.controller.CreateReservationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;

public class EmployeeViewController implements Initializable{

    Hotel thisHotel = null;

    @FXML
    private Button Button_Calculate;

    @FXML
    private Button Button_CreateReservations;

    @FXML
    private Button Button_HotelOverview;

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
    private ChoiceBox<String> choice_RoomType;

    @FXML
    private DatePicker dp_CheckIn;

    @FXML
    private DatePicker dp_CheckOut;

    @FXML
    private ImageView profileImage;

    /////////////////////////////////////////
    
    ////////////////////////////////////////

    @FXML
    void Initialize(ContextMenuEvent event) {
    }

    @FXML
    public long numberOfNights() {
	  LocalDate dateBefore = dp_CheckIn.getValue();
    LocalDate dateAfter = dp_CheckOut.getValue();
    long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
    return noOfDaysBetween;
    }


    @FXML
    void OnClick_Calculate(ActionEvent event) {

      DatabaseManager dm = DatabaseManager.instance;
      Account account = App.currentUser;
      Hotel hotel = dm.FindHotelByID(account.getHotelID());
      App.currentHotel = hotel;

      int numRooms = Integer.parseInt(TextField_NumberOfRooms.getText());

      String roomType = choice_RoomType.getValue();
      double roomTypePrice = 0.00;

      if(roomType.equals("King"))
        roomTypePrice = hotel.getRoomPriceKing();
      else if(roomType.equals("Queen"))
        roomTypePrice = hotel.getRoomPriceQueen();
      else if(roomType.equals("Standard"))
        roomTypePrice = hotel.getRoomPriceStandard();
      
      int numPeople = Integer.parseInt(TextField_NumberOfPeople.getText());
      
      long numOfNights = numberOfNights(); 
      //Math
      double total = (((numRooms*roomTypePrice)*numOfNights)*numPeople);
      
      String formatTotal;

      formatTotal = String.format("$%.2f", total);

      TextField_Total.setText(formatTotal);

    }

    @FXML
    void OnClick_CreateReservations(ActionEvent event) {
      CreateReservationController.Instance.reinitialize();
      PageManager.SetPage("CreateReservation", "Create your reservation!");
    }

    @FXML
    void OnClick_HotelOverview(ActionEvent event) {
      HotelViewController.Instance.ViewHotel(App.currentHotel);
      PageManager.SetPage("HotelView", "Welcome to " + App.currentHotel.getName() + "!");
    }

    @FXML
    void OnClick_ViewReservation(ActionEvent event) {
      ReservationViewController.Instance.init_employee();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      // TODO Auto-generated method stub
      choice_RoomType.getItems().addAll("King", "Queen", "Standard");
    }
}
