package com.main.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.pages.PageManager;
import com.main.tools.PopupHandler;

import org.bson.types.ObjectId;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerViewController implements Initializable {

    public static CustomerViewController Instance;

    @FXML
    private Button Button_Profile;

    @FXML
    private Button Button_Reservation;

    @FXML
    private TableView<Reservation> TableView_Reservations;

    @FXML
    private TableColumn<Reservation, ObjectId> Col_ReservationNum;

    @FXML
    private TableColumn<Reservation, String> Col_HotelName;

    @FXML
    private TableColumn<Reservation, String> Col_CustName;

    @FXML
    private TableColumn<Reservation, Integer> Col_Nights;

    @FXML
    private TableColumn<Reservation, String> Col_RoomType;

    @FXML
    private TableColumn<Reservation, Integer> Col_Rooms;

    @FXML
    private TextField Location;

    @FXML
    private TextField PropertyName;

    @FXML
    private Label welcomeLabel;

    //Go to the profile view (should have the same options as the manager)
	@FXML
	void OnClick_Profile(ActionEvent event) {
		PageManager.SetPage("ProfileView", "Profile");
	}

    //Supposed to take you to see the reservations you made
	@FXML
	void OnClick_Reserve(ActionEvent event) {
    ReservationViewController.Instance.init_customer();
		PageManager.SetPage("ReservationView", "View reservations");
	}

    @FXML
    void OnClick_HotelSearch(ActionEvent event) {
        PageManager.SetPage("HotelSearchView", "Search for a Hotel!");
    }

    @FXML
    void OnClick_ViewSelected(ActionEvent event) {
        Reservation selected = TableView_Reservations.getSelectionModel().getSelectedItem();

        if(selected == null){
            PopupHandler.ShowError("Please select a Reservation!");
            return;
        }

        Hotel viewedHotel = DatabaseManager.instance.FindHotelByID(selected.getHotel());

        HotelViewController.Instance.fromDate = selected.getStartDate();
        HotelViewController.Instance.toDate = selected.getEndDate();
        HotelViewController.Instance.ViewHotel(viewedHotel);

        PageManager.SetPage("HotelView", "You are Viewing - " + viewedHotel.getName());
    }

    ObservableList<Reservation> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Instance = this;

        Col_ReservationNum.setCellValueFactory(new PropertyValueFactory<Reservation, ObjectId>("ID"));

        Col_HotelName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("hotelName"));
        Col_CustName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
        
        Col_Nights.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("nights"));
        Col_Rooms.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("rooms"));
        Col_RoomType.setCellValueFactory(new PropertyValueFactory<Reservation, String>("roomType"));
  
        TableView_Reservations.setItems(list);
  
        TableView_Reservations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void reinitialize(){
        list.clear();
        list.addAll(DatabaseManager.instance.FindReservationsByAccount(App.currentUser));
    }
}

