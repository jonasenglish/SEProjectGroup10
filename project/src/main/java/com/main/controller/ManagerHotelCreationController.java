package com.main.controller;

import com.main.database.DatabaseManager;
import com.main.objects.Hotel;
import com.main.pages.PageManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ManagerHotelCreationController {

    @FXML
    private TextField TextField_Amenities;

    @FXML
    private TextField TextField_NumberOfRooms;

    @FXML
    private TextField TextField_PricePerNight;

    @FXML
    private TextField TextField_PriceStandard;

    @FXML
    private TextField TextField_PriceQueen;

    @FXML
    private TextField TextField_PriceKing;

    @FXML
    private TextField TextField_WeekendDifferential;

    @FXML
    private TextField TextField_HotelName;

    @FXML
    private Button Button_Submit;

    @FXML
    void OnClick_Submit(ActionEvent event) {
        Hotel hotel = new Hotel();
        hotel.setName(TextField_HotelName.getText());
        hotel.setRoomPriceStandard(Float.parseFloat(TextField_PriceStandard.getText()));
        hotel.setRoomPriceKing(Float.parseFloat(TextField_PriceKing.getText()));
        hotel.setRoomPriceQueen(Float.parseFloat(TextField_PriceQueen.getText()));
        hotel.setWeekendDifferential(Float.parseFloat(TextField_WeekendDifferential.getText()));

        DatabaseManager dm = DatabaseManager.instance;

        dm.InsertHotel(hotel);

        PageManager.SetPage("ManagerView", "Welcome");
    }

}
