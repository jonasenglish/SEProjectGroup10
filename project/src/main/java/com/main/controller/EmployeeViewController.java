package com.main.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.time.LocalDate;

import com.main.App;
import com.main.pages.PageManager;
import com.main.tools.PopupHandler;
import com.main.objects.Account;
import com.main.database.DatabaseManager;
import com.main.objects.Hotel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Button Button_Logout;

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

      boolean isBeforeTime = dp_CheckIn.getValue().isBefore(LocalDate.now());
      boolean isBeforeTime2 = dp_CheckOut.getValue().isBefore(LocalDate.now());
      boolean checkOutBeforeCheckin = dp_CheckOut.getValue().isBefore(dp_CheckIn.getValue());
      boolean checkInAfterCheckOut = dp_CheckIn.getValue().isAfter(dp_CheckOut.getValue());

      DatabaseManager dm = DatabaseManager.instance;
      Account account = App.currentUser;
      Hotel hotel = dm.FindHotelByID(account.getHotelID());
      App.currentHotel = hotel;

      int numRooms = Integer.parseInt(TextField_NumberOfRooms.getText());
      String roomType = choice_RoomType.getValue();
      double roomTypePrice = 0.00;

      if(dp_CheckIn.getValue() == null && dp_CheckOut.getValue() == null && choice_RoomType.getValue() == null && TextField_NumberOfRooms.getText() == null && TextField_NumberOfPeople.getText() == null)
        PopupHandler.ShowError("Please input values!");

      if(isBeforeTime || isBeforeTime2 || checkOutBeforeCheckin || checkInAfterCheckOut)
      {
        PopupHandler.ShowError("Error: Invalid Date Range! Please Try Again!");
        TextField_NumberOfRooms.clear();
        TextField_NumberOfPeople.clear();
        TextField_Total.clear();
        dp_CheckOut.getEditor().clear();
        dp_CheckIn.getEditor().clear();
      }
      
      if(!(TextField_NumberOfRooms.getText().matches("[0-9]+")) && !(TextField_NumberOfPeople.getText().matches("[0-9]")))
      {
        PopupHandler.ShowError("Error: Characters are not allowed! Please only use numbers!");
        TextField_NumberOfRooms.clear();
        TextField_NumberOfPeople.clear();
        TextField_Total.clear();
        dp_CheckOut.getEditor().clear();
        dp_CheckIn.getEditor().clear();
      }

      if(choice_RoomType.getValue() == null)
      {
        PopupHandler.ShowError("Please select a room type!");
        TextField_NumberOfRooms.clear();
        TextField_NumberOfPeople.clear();
        TextField_Total.clear();
        dp_CheckOut.getEditor().clear();
        dp_CheckIn.getEditor().clear();
      }
       
      if(roomType.equals("King"))
        roomTypePrice = hotel.getRoomPriceKing();
      else if(roomType.equals("Queen"))
        roomTypePrice = hotel.getRoomPriceQueen();
      else if(roomType.equals("Standard"))
        roomTypePrice = hotel.getRoomPriceStandard();
      
      int numPeople = Integer.parseInt(TextField_NumberOfPeople.getText());
      
      long numOfNights = numberOfNights(); 
      double weekendDif = App.currentHotel.getWeekendDifferential();
      //Math
      double total = 0.00;

      DayOfWeek checkinday = DayOfWeek.of(dp_CheckIn.getValue().get(ChronoField.DAY_OF_WEEK));
      DayOfWeek checkoutday = DayOfWeek.of(dp_CheckOut.getValue().get(ChronoField.DAY_OF_WEEK));

      switch (checkinday) {
        case SATURDAY:
        case SUNDAY:
          total = (((numRooms*roomTypePrice)*numOfNights)*weekendDif);
        default:
          total = (((numRooms*roomTypePrice)*numOfNights));
      }

      switch (checkoutday) {
        case SATURDAY:
        case SUNDAY:
          total = (((numRooms*roomTypePrice)*numOfNights)*weekendDif);
        default:
          total = (((numRooms*roomTypePrice)*numOfNights));
      }

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

    @FXML
    void OnClickButton_Logout(ActionEvent event){
      PageManager.SetPage("Login", "Welcome!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      // TODO Auto-generated method stub
      choice_RoomType.getItems().addAll("King", "Queen", "Standard");
    }
}
