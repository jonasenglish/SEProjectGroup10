package com.main.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.main.App;
import com.main.pages.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class EmployeeViewController {

    @FXML
    private Button Button_CreateReservations;

    @FXML
    private Button Button_HotelOverview;

    @FXML
    private Button Button_Profile;

    @FXML
    private Button Button_ViewReservation;

    @FXML
    private Label WelcomeLabel;

    @FXML
    private BorderPane display_BorderPane;

    @FXML
    private ImageView profileImage;

    @FXML
    private Parent root;

    @FXML
    private URL url;

    @FXML
    void Initialize(ContextMenuEvent event) {

    }

    @FXML
    void showAvailabilityTable() throws IOException {
      url = new File("project/src/main/java/com/main/pages/AvailabilityView.fxml").toURI().toURL();
      root = FXMLLoader.load(url);
      display_BorderPane.setCenter(root);
    }

    @FXML
    void OnClick_CreateReservations(ActionEvent event) {
      PageManager.SetPage("CreateReservation", "Create Reservation");
    }

    @FXML
    void OnClick_HotelOverview(ActionEvent event) {
      PageManager.SetPage("HotelView", "Viewing current Hotel!");
    }

    @FXML
    void OnClick_Profile(ActionEvent event) {
      PageManager.SetPage("ProfileView", "Profile");
    }

    @FXML
    void OnClick_ViewReservation(ActionEvent event) {
      PageManager.SetPage("ReservationView", "Reservation");
    }

    @FXML
    void showscene(MouseEvent event) throws IOException {
      showAvailabilityTable();
    }
}
