package com.main.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Hotel;
import com.main.pages.PageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AvailabilityViewController implements Initializable {

    DatabaseManager dm = DatabaseManager.instance;

    @FXML
    private TableColumn<Hotel, Integer> Column_King;

    @FXML
    private TableColumn<Hotel, Double> Column_KingPrices;

    @FXML
    private TableColumn<Hotel, Integer> Column_Queen;

    @FXML
    private TableColumn<Hotel, Double> Column_QueenPrices;

    @FXML
    private TableColumn<Hotel, Integer> Column_Standard;

    @FXML
    private TableColumn<Hotel, Double> Column_StandardPrices;

    @FXML
    private Hotel userHotel;

    @FXML
    private TableView<Hotel> TableView_Hotels;

    @FXML
    private ObservableList<Hotel> list = FXCollections.observableArrayList();

    @FXML
    private Hotel tempHotel() {
       userHotel = dm.FindHotelByName("Current Functioning Hotel");
       return userHotel;
        
    }

    void showAvailable() {
        tempHotel();
        List<Hotel> results = new ArrayList<>();
        int value = userHotel.getTotalRooms();
        results = dm.FindHotelsByRooms(value);

        list.addAll(results);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Column_Standard.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("standardRooms"));
        Column_Queen.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("queenRooms"));
        Column_King.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("kingRooms"));
        
        Column_StandardPrices.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("roomPriceStandard"));
        Column_QueenPrices.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("roomPriceQueen"));
        Column_KingPrices.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("roomPriceKing"));

        TableView_Hotels.setItems(list);

        TableView_Hotels.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
