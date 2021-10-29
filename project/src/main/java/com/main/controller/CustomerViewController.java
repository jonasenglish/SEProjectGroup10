package com.main.controller;

import com.main.pages.PageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CustomerViewController {

	//@FXML	
	//private Button Button_Profile;
	
	@FXML
	private Text numRooms;
	
	@FXML
	private Text numAdults;
	
	@FXML
	private Text numChildren;
	
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

	//display more stuff here probably 
	
}
