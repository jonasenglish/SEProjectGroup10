package com.main.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Amenity;
import com.main.objects.Hotel;
import com.main.pages.PageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class ManagerHotelCreationController implements Initializable {

    @FXML
    private TextField TextField_NumberOfRooms;

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
    private TableView<Amenity> TableView_Amenities;

    @FXML
    private TableColumn<Amenity, String> name;

    @FXML
    private TableColumn<Amenity, String> description;

    @FXML
    private TextField TextField_AmenityName;

    @FXML
    private TextArea TextArea_AmenityDescription;

    @FXML
    void OnClick_Submit(ActionEvent event) {

        Hotel hotel = new Hotel();
        hotel.setName(TextField_HotelName.getText());
        hotel.setRoomPriceStandard(Double.parseDouble(TextField_PriceStandard.getText()));
        hotel.setRoomPriceKing(Double.parseDouble(TextField_PriceKing.getText()));
        hotel.setRoomPriceQueen(Double.parseDouble(TextField_PriceQueen.getText()));
        hotel.setWeekendDifferential(Double.parseDouble(TextField_WeekendDifferential.getText()));
        
        List<Amenity> amenities = TableView_Amenities.getItems();

        hotel.setStandardAmenities(amenities);

        DatabaseManager dm = DatabaseManager.instance;

        dm.InsertHotel(hotel);

        PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());

    }

    @FXML
    void OnClick_Cancel(ActionEvent event){

        PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());

    }

    @FXML
    void OnClick_AddAmenity(ActionEvent event) {
        Amenity amenity = new Amenity();
        amenity.setName(TextField_AmenityName.getText());
        amenity.setDescription(TextArea_AmenityDescription.getText());
        TableView_Amenities.getItems().add(amenity);
    }

    @FXML
    void OnClick_RemoveAmenity(ActionEvent event) {
        TableView_Amenities.getItems().remove(TableView_Amenities.getSelectionModel().getSelectedItem());
    }

    ObservableList<Amenity> list = FXCollections.observableArrayList(
        new Amenity("Test", "Testing"), 
        new Amenity("Test 2", "Testing 2"), 
        new Amenity("Test 3", "Testing 3")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableView_Amenities.setEditable(true);
        name.setCellValueFactory(new PropertyValueFactory<Amenity, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Amenity, String>("description"));
        TableView_Amenities.setItems(list);
    }

    private ObservableList<Amenity> getAmenities(Hotel hotel) {
        ObservableList<Amenity> amenities = FXCollections.observableArrayList();
        for(Amenity amenity : hotel.getStandardAmenities())
            amenities.add(amenity);
        for(Amenity amenity : hotel.getQueenAmenities())
            amenities.add(amenity);
        for(Amenity amenity : hotel.getKingAmenities())
            amenities.add(amenity);
        return amenities;
    }

}
