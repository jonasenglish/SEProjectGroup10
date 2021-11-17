package com.main.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
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

public class ReservationViewController implements Initializable {

    public static ReservationViewController Instance;

    @FXML
    private TableView<Reservation> ReservationTable;

    @FXML
    private TableColumn<Reservation, ObjectId> Col_ReservationNum;

    @FXML
    private TableColumn<Reservation, String> Col_CustName;

    @FXML
    private TableColumn<Reservation, Date> Col_CheckIn;

    @FXML
    private TableColumn<Reservation, Integer> Col_Nights;

    @FXML
    private TableColumn<Reservation, String> Col_RoomType;

    @FXML
    private TableColumn<Reservation, Integer> Col_Rooms;

    @FXML
    private TableColumn<Reservation, Integer> Col_Adults;

    @FXML
    private TableColumn<Reservation, Integer> Col_Children;

    @FXML
    private TextField TextField_input;

    @FXML
    private Label Label_ReservationNum;

    @FXML
    private Button Button_Modify;

    @FXML
    private Button Button_Delete;

    @FXML
    private Button Button_Home;

    @FXML
    private Button Button_Checkout;

    @FXML
    void OnClick_CheckOut(ActionEvent event) {

    }

    @FXML
    void OnClick_Delete(ActionEvent event) {
      Reservation selected = ReservationTable.getSelectionModel().getSelectedItem();

      if(selected == null){
        PopupHandler.ShowError("Please select a reservation first.");
        return;
      }

      if(PopupHandler.ShowConfirmation("Are you sure you want to cancel the selected reservation?")){
        DatabaseManager.instance.DeleteReservation(selected);
        list.remove(selected);
        if(newList.contains(selected))
          newList.remove(selected);
        PopupHandler.ShowInfo("Reservation Cancelled.");
      }
    }

    @FXML
    void OnClick_Home(ActionEvent event) {
      App.AccountTypeView();
    }

    @FXML
    void OnClick_Modify(ActionEvent event) {
      Reservation selected = ReservationTable.getSelectionModel().getSelectedItem();

      if(selected == null){
        PopupHandler.ShowError("Please select a reservation first.");
        return;
      }

      CreateReservationController.Instance.edit(selected);
      PageManager.SetPage("CreateReservation", "Edit Reservation - ID #" + selected.getID().toString());
    }

    @FXML
    void OnInput_ReservationField(ActionEvent event) {
      for (Reservation reservation : list) {
        if(reservation.getID().toString().contains(TextField_input.getText()))
          newList.add(reservation);
      }

      ReservationTable.setItems(newList);
    }

    ObservableList<Reservation> newList = FXCollections.observableArrayList();
    ObservableList<Reservation> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      Instance = this;
      
      Col_ReservationNum.setCellValueFactory(new PropertyValueFactory<Reservation, ObjectId>("ID"));

      Col_CheckIn.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("startDate"));
      Col_CustName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
      
      Col_Adults.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("adults"));
      Col_Children.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("children"));
      Col_Nights.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("nights"));
      Col_Rooms.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("rooms"));
      Col_RoomType.setCellValueFactory(new PropertyValueFactory<Reservation, String>("roomType"));

      ReservationTable.setItems(list);

      ReservationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void init_customer(){
      list.clear();

      for (Reservation reservation : DatabaseManager.instance.FindReservationsByAccount(App.currentUser)) {
        list.add(reservation);
      }

      Button_Delete.setText("Cancel");

      Button_Modify.setDisable(true);
      Button_Modify.setVisible(false);

      Label_ReservationNum.setVisible(false);
      TextField_input.setVisible(false);
    }

    public void init_employee(){
      list.clear();

      for (Reservation reservation : DatabaseManager.instance.FindReservationsByEmployeeHotel()) {
        list.add(reservation);
      }

      Button_Delete.setText("Delete");

      Button_Modify.setDisable(false);
      Button_Modify.setVisible(true);

      Label_ReservationNum.setVisible(true);
      TextField_input.setVisible(true);
    }

}

