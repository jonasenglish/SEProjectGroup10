package com.main.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Amenity;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.pages.PageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class CreateReservationController {

	@FXML
	private DatePicker arrivalDate;
	
	//@FXML
	//private DatePicker departureDate;
	
	@FXML
	private ChoiceBox roomType;
	
	@FXML
	private TextField customerPhone;
	
	@FXML
	private TextField customerEmail;
	
	@FXML
	private Label hotelName;
	
	@FXML
	private TextField numNights;
	
	@FXML
	private TextField numRooms;
	
	@FXML
	private TextField numAdults;
	
	@FXML
	private TextField numChildren;
	
	
	
	@FXML
	void OnClick_Submit(ActionEvent event) {
		//Nothing happening here yet
		Reservation reservation = new Reservation();
		
	}
	
	@FXML
	void OnClick_Cancel(ActionEvent event) {
		PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
	}
	
}
