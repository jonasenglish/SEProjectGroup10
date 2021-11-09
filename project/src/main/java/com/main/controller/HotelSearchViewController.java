package com.main.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

public class HotelSearchViewController implements Initializable {

    DatabaseManager dm = null;

    @FXML
    private CheckBox CheckBox_AvailableOnly;

    @FXML
    private ChoiceBox<String> ChoiceBox_SearchBy;

    @FXML
    private TableColumn<Hotel, String> Column_Description;

    @FXML
    private TableColumn<Hotel, String> Column_Hotel;

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
    private TextField TextField_Search;

    @FXML
    private TableView<Hotel> TableView_Hotels;

    @FXML
    void OnClick_AvailableOnly(ActionEvent event) {
        if(!CheckBox_AvailableOnly.selectedProperty().get()){
            OnClick_Search(event);
            return;
        }

        List<Hotel> results = new ArrayList<>();

        results.addAll(list);
        list.clear();

        for (Hotel hotel : results) {
            if(hotel.getTotalRooms() > 0)
                list.add(hotel);
        }
    }

    @FXML
    void OnClick_Search(ActionEvent event) {
        if(dm == null)
            dm = DatabaseManager.instance;

        list.clear();

        if(TextField_Search.getText().equals("")){ SearchBy_HotelName(); return; } // No input returns all hotels

        switch(ChoiceBox_SearchBy.getValue()){
            case "Hotel Name":
                SearchBy_HotelName();
                break;

            case "Rooms Available":
                SearchBy_RoomsAvailable();
                break;

            case "Standard Prices <":
                SearchBy_RoomPricesLT(Hotel.RoomType.STANDARD);
                break;

            case "Standard Prices >":
                SearchBy_RoomPricesGT(Hotel.RoomType.STANDARD);
                break;

            case "Queen Prices <":
                SearchBy_RoomPricesLT(Hotel.RoomType.QUEEN);
                break;

            case "Queen Prices >":
                SearchBy_RoomPricesGT(Hotel.RoomType.QUEEN);
                break;

            case "King Prices <":
                SearchBy_RoomPricesLT(Hotel.RoomType.KING);
                break;

            case "King Prices >":
                SearchBy_RoomPricesGT(Hotel.RoomType.KING);
                break;
        }
    }

    private void SearchBy_RoomPricesGT(Hotel.RoomType roomType) {
        double value = -1;

        try{
           value = Integer.parseInt(TextField_Search.getText());
        }catch(Exception e){
            System.err.println("Input was not a Double");
            return;
        }

        List<Hotel> results = dm.FindHotelsByPriceGT(value, roomType);

        if(CheckBox_AvailableOnly.selectedProperty().get())
            results = RemoveNoAvailable(results);

        list.addAll(results);
    }

    private void SearchBy_RoomPricesLT(Hotel.RoomType roomType) {
        double value = -1;

        try{
           value = Integer.parseInt(TextField_Search.getText());
        }catch(Exception e){
            System.err.println("Input was not a Double");
            return;
        }

        List<Hotel> results = dm.FindHotelsByPriceLT(value, roomType);

        if(CheckBox_AvailableOnly.selectedProperty().get())
            results = RemoveNoAvailable(results);

        list.addAll(results);
    }

    private void SearchBy_RoomsAvailable() {
        int value = -1;

        try{
           value = Integer.parseInt(TextField_Search.getText());
        }catch(Exception e){
            System.err.println("Input was not an Integer");
            return;
        }

        List<Hotel> results = dm.FindHotelsByRooms(value);

        if(CheckBox_AvailableOnly.selectedProperty().get())
            results = RemoveNoAvailable(results);

        list.addAll(results);
    }

    private void SearchBy_HotelName() {
        List<Hotel> results = dm.FindHotelsByName(TextField_Search.getText());

        if(CheckBox_AvailableOnly.selectedProperty().get())
            results = RemoveNoAvailable(results);

        list.addAll(results);
    }

    private List<Hotel> RemoveNoAvailable(List<Hotel> hotels){
        List<Hotel> results = new ArrayList<>();

        for (Hotel hotel : hotels) {
            if(hotel.getTotalRooms() > 0)
                results.add(hotel);
        }

        return results;
    }

    @FXML
    void OnClick_ViewSelected(ActionEvent event) {

        Hotel viewedHotel = TableView_Hotels.getSelectionModel().getSelectedItem();

        HotelViewController.Instance.ViewHotel(viewedHotel);

        PageManager.SetPage("HotelView", "You are Viewing - " + viewedHotel.getName());

    }

    @FXML
    void OnClick_Back(ActionEvent event) {
        PageManager.SetPage("ManagerView", "Manager View");
    }

    ObservableList<Hotel> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChoiceBox_SearchBy.getItems().addAll("Hotel Name", "Rooms Available", "Standard Prices <", "Standard Prices >", "Queen Prices <", "Queen Prices >", "King Prices <", "King Prices >");
        ChoiceBox_SearchBy.setValue("Hotel Name");

        Column_Hotel.setCellValueFactory(new PropertyValueFactory<Hotel, String>("name"));
        Column_Description.setCellValueFactory(new PropertyValueFactory<Hotel, String>("description"));

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
