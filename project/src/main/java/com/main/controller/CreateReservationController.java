package com.main.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.tools.PopupHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class CreateReservationController implements Initializable {

    public static CreateReservationController Instance;
    public Hotel selectedHotel;

    @FXML
    private Button Button_Cancel;

    @FXML
    private Button Button_MakeReservation;

    @FXML
    private DatePicker arrivalDate;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField customerEmail;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhone;

    @FXML
    private Label hotelName;

    @FXML
    private TextField numAdults;

    @FXML
    private TextField numChildren;

    @FXML
    private Label numNights;

    @FXML
    private TextField numRooms;

    @FXML
    private ChoiceBox<String> roomType;

    @FXML
    private Label standardRoomsAvailable;

    @FXML
    private Label queenRoomsAvailable;

    @FXML
    private Label kingRoomsAvailable;

    private String[] roomTypeArray = {"Standard", "King", "Queen"};

    public void reinitialize(){
        selectedHotel = DatabaseManager.instance.FindHotelByID(selectedHotel.getID()); // To get correct room values;
        hotelName.setText(selectedHotel.getName());
    }

    private void AvailableRooms(){
        Date to = Date.from(departureDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date from = Date.from(arrivalDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        int t_std = selectedHotel.getStandardRooms(), t_queen = selectedHotel.getQueenRooms(), t_king= selectedHotel.getKingRooms();

        List<Reservation> reservationsInRange = DatabaseManager.instance.FindReservationsInDateRange(to, from);
        
        for (Reservation reservation : reservationsInRange) {
            if(selectedHotel.getID().equals(reservation.getHotel())){
                if(reservation.getRoomType().equals("Standard"))
                    t_std -= reservation.getRooms();
                if(reservation.getRoomType().equals("Queen"))
                    t_queen -= reservation.getRooms();
                if(reservation.getRoomType().equals("King")){
                    t_king -= reservation.getRooms();
                }
            }
        }

        standardRoomsAvailable.setText(t_std + "");
        queenRoomsAvailable.setText(t_queen + "");
        kingRoomsAvailable.setText(t_king + "");
    }

    private int CalcDays(DatePicker arrivalDate, DatePicker departureDate){
        int d_day = departureDate.getValue().getDayOfYear();
        int a_day = arrivalDate.getValue().getDayOfYear();
        int d_year = departureDate.getValue().getYear();
        int a_year = departureDate.getValue().getYear();
        int year_dif = d_year - a_year;
        int days = d_day - (a_day + (year_dif * 365));

        return days;
    }

    @FXML
    void OnChange_ArrivalDate(ActionEvent event) {
        if(departureDate.getValue() != null){
            if(departureDate.getValue().isBefore(arrivalDate.getValue())){
                PopupHandler.ShowError("Departure Date cannot be before Arrival Date!");
                return;
            }
           numNights.setText(CalcDays(arrivalDate, departureDate) + "");
           AvailableRooms();
        }
    }

    @FXML
    void OnChange_DepartureDate(ActionEvent event) {
        if(arrivalDate.getValue() != null){
            if(departureDate.getValue().isBefore(arrivalDate.getValue())){
                PopupHandler.ShowError("Departure Date cannot be before Arrival Date!");
                return;
            }
            numNights.setText(CalcDays(arrivalDate, departureDate) + "");
            AvailableRooms();
        }
    }

    @FXML
    void OnClick_Cancel(ActionEvent event) {
		App.AccountTypeView();
    }

    @FXML
    void OnClick_MakeReservation(ActionEvent event) {

        DatabaseManager dm = DatabaseManager.instance;

        /*  Since DatePicker returns LocalDate and not Date, this converts 
        //  LocalDate to Date for it to be used with the 
        //  reservation.setStartDate();
        */
        
        //Start Date
        LocalDate myDate = arrivalDate.getValue();
		Instant instant = Instant.from(myDate.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
        //End Date
        LocalDate myDate2 = departureDate.getValue();
        Instant instant2 = Instant.from(myDate2.atStartOfDay(ZoneId.systemDefault()));
		Date date2 = Date.from(instant2);

		Reservation reservations = new Reservation();
       
        reservations.setReservee(App.currentUser.getID());

		reservations.setStartDate(date);
        reservations.setEndDate(date2);

		reservations.setNights(Integer.parseInt(numNights.getText()));
        reservations.setRooms(Integer.parseInt(numRooms.getText()));
        reservations.setAdults(Integer.parseInt(numAdults.getText()));
        reservations.setChildren(Integer.parseInt(numChildren.getText()));
        reservations.setRoomType(roomType.getValue());


        reservations.setCustomerName(customerName.getText());
        reservations.setCustomerEmail(customerEmail.getText());
        reservations.setCustomerPhoneNumber(customerPhone.getText());

        reservations.setHotel(selectedHotel.getID());

        switch(reservations.getRoomType()){
            case "Standard":
                if(reservations.getRooms() > Integer.parseInt(standardRoomsAvailable.getText())){
                    PopupHandler.ShowError("There are not enough rooms available for your selected room type and amount.");
                    return;
                }
                break;
            case "Queen":
                if(reservations.getRooms() > Integer.parseInt(queenRoomsAvailable.getText())){
                    PopupHandler.ShowError("There are not enough rooms available for your selected room type and amount.");
                    return;
                }
                break;
            case "King":
                if(reservations.getRooms() > Integer.parseInt(kingRoomsAvailable.getText())){
                    PopupHandler.ShowError("There are not enough rooms available for your selected room type and amount.");
                    return;
                }
                break;
        }

        dm.InsertReservation(reservations);

        App.AccountTypeView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        Instance = this;

        roomType.getItems().addAll(roomTypeArray);
        
    }   
}
