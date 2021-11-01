package com.main.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.objects.Amenity;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.pages.PageManager;

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

public class CreateReservationController {

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
    private TextField numNights;

    @FXML
    private TextField numRooms;

    @FXML
    private ChoiceBox<?> roomType;


	@FXML
    void getArrivalDate(ActionEvent event) {
		
    }

    @FXML
    void OnClick_Cancel(ActionEvent event) {
		if(App.currentUser.isManager() == false) {
            PageManager.SetPage("CustomerView", "Welcome - " + App.currentUser.getUsername());
        } else {
            PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
        }
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
       
        reservations.setReservee(App.currentUser); //FOR SOME REASON THIS LINE OF CODE DOESN'T WORK AND I'VE BEEN WORKING ON THIS FOR 5 HOURS !! :( so 
        // Reservee in the database is null. I would like some assistance when we meet up :)

		reservations.setStartDate(date);
        reservations.setEndDate(date2);

		reservations.setNights(Integer.parseInt(numNights.getText()));
        reservations.setRooms(Integer.parseInt(numRooms.getText()));
        reservations.setAdults(Integer.parseInt(numAdults.getText()));
        reservations.setChildren(Integer.parseInt(numChildren.getText()));

        reservations.setCustomerName(customerName.getText());
        reservations.setCustomerEmail(customerEmail.getText());
        reservations.setCustomerPhoneNumber(customerPhone.getText());

        dm.InsertReservation(reservations);

        if(App.currentUser.isManager() == false) {
            PageManager.SetPage("CustomerView", "Welcome - " + App.currentUser.getUsername());
        } else {
            PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
        }
    }   
}
