package com.main.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.main.App;
import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.objects.Amenity;
import com.main.objects.Hotel;
import com.main.objects.Amenity.AmenityType;
import com.main.pages.PageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class ManagerHotelCreationController implements Initializable {

    public static boolean isEdit = false;
    public Account newManager = null;

    public static ManagerHotelCreationController Instance = null;

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
    private TableColumn<Amenity, Amenity.AmenityType> amenityType;
    
    @FXML
    private TextField TextField_AmenityName;
    
    @FXML
    private TextArea TextArea_AmenityDescription;
    
    @FXML
    private ChoiceBox<Amenity.AmenityType> ChoiceBox_AmenityType;

    @FXML
    private TextField TextField_NumberOfStandardRooms;

    @FXML
    private TextField TextField_NumberOfQueenRooms;

    @FXML
    private TextField TextField_NumberOfKingRooms;

    @FXML
    private TextArea TextArea_HotelDescription;

    @FXML
    private TextField TextField_HotelImage;
    
    @FXML
    void OnClick_Submit(ActionEvent event) {
        DatabaseManager dm = DatabaseManager.instance;

        if(!isEdit && dm.FindHotelByName(TextField_HotelName.getText()) != null){
            System.err.println("Error: A hotel with that name already exists!");
            return;
        }

        Hotel hotel = new Hotel();
        hotel.setName(TextField_HotelName.getText());
        hotel.setDescription(TextArea_HotelDescription.getText());
        hotel.setRoomPriceStandard(Double.parseDouble(TextField_PriceStandard.getText()));
        hotel.setRoomPriceKing(Double.parseDouble(TextField_PriceKing.getText()));
        hotel.setRoomPriceQueen(Double.parseDouble(TextField_PriceQueen.getText()));
        hotel.setWeekendDifferential(Double.parseDouble(TextField_WeekendDifferential.getText()));
        hotel.setStandardRooms(Integer.parseInt(TextField_NumberOfStandardRooms.getText()));
        hotel.setQueenRooms(Integer.parseInt(TextField_NumberOfQueenRooms.getText()));
        hotel.setKingRooms(Integer.parseInt(TextField_NumberOfKingRooms.getText()));
        hotel.setHotelImageURL(TextField_HotelImage.getText());
        
        List<Amenity> amenities = TableView_Amenities.getItems();
        List<Amenity> standardAmenities = new ArrayList<>();
        List<Amenity> queenAmenities = new ArrayList<>();
        List<Amenity> kingAmenities = new ArrayList<>();

        for (Amenity amenity : amenities) {
            if(amenity.getAmenityType() == Amenity.AmenityType.STANDARD){
                amenity.setAmenityTypeString("Standard");
                standardAmenities.add(amenity);
            }
            if(amenity.getAmenityType() == Amenity.AmenityType.QUEEN){
                amenity.setAmenityTypeString("Queen");
                queenAmenities.add(amenity);
            }
            if(amenity.getAmenityType() == Amenity.AmenityType.KING){
                amenity.setAmenityTypeString("King");
                kingAmenities.add(amenity);
            }
        }

        hotel.setStandardAmenities(standardAmenities);
        hotel.setQueenAmenities(queenAmenities);
        hotel.setKingAmenities(kingAmenities);


        if(!isEdit){
            dm.InsertHotel(hotel);
            hotel = dm.FindHotelByName(hotel.getName());
            newManager.setHotelID(hotel.getID());
            dm.UpdateAccount(newManager);
            System.out.println("Hotel Created!");

            PageManager.SetPage("Login", "Login");
            return;
        }
        else{
            hotel.setID(App.currentHotel.getID());
            dm.UpdateHotel(hotel);
            System.out.println("Hotel Updated!");
            App.currentHotel = hotel;
        }

        ClearValues();

        PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
    }

    @FXML
    void OnClick_Cancel(ActionEvent event){
        ClearValues();

        PageManager.SetPage("ManagerView", "Welcome - " + App.currentUser.getUsername());
    }

    @FXML
    void OnClick_AddAmenity(ActionEvent event) {
        Amenity amenity = new Amenity();
        amenity.setName(TextField_AmenityName.getText());
        amenity.setDescription(TextArea_AmenityDescription.getText());
        amenity.setAmenityType(ChoiceBox_AmenityType.getValue());
        TableView_Amenities.getItems().add(amenity);
    }

    @FXML
    void OnClick_RemoveAmenity(ActionEvent event) {
        TableView_Amenities.getItems().remove(TableView_Amenities.getSelectionModel().getSelectedItem());
    }

    ObservableList<Amenity> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableView_Amenities.setEditable(true);
        name.setCellValueFactory(new PropertyValueFactory<Amenity, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Amenity, String>("description"));
        amenityType.setCellValueFactory(new PropertyValueFactory<Amenity, Amenity.AmenityType>("amenityType"));
        TableView_Amenities.setItems(list);
        ChoiceBox_AmenityType.getItems().addAll(AmenityType.STANDARD, AmenityType.QUEEN, AmenityType.KING);
        ChoiceBox_AmenityType.setValue(AmenityType.STANDARD);

        Instance = this;
    }

    public void reinitialize(){
        Hotel hotel = App.currentHotel;

        if(hotel == null){
            System.err.println("Critical Error: currentHotel is NULL!");
            return;
        }

        list = getAmenities(hotel);

        TextArea_HotelDescription.setText(hotel.getDescription());

        TextField_HotelName.setText(hotel.getName());
        TextField_NumberOfKingRooms.setText(hotel.getKingRooms() + "");
        TextField_NumberOfQueenRooms.setText(hotel.getQueenRooms() + "");
        TextField_NumberOfStandardRooms.setText(hotel.getStandardRooms() + "");
        TextField_PriceStandard.setText(hotel.getRoomPriceStandard() + "");
        TextField_PriceQueen.setText(hotel.getRoomPriceQueen() + "");
        TextField_PriceKing.setText(hotel.getRoomPriceKing() + "");
        TextField_WeekendDifferential.setText(hotel.getWeekendDifferential() + "");
        TextField_HotelImage.setText(hotel.getHotelImageURL() + "");

        TableView_Amenities.setItems(list);
    }

	private ObservableList<Amenity> getAmenities(Hotel hotel) {
        ObservableList<Amenity> amenities = FXCollections.observableArrayList();
        for(Amenity amenity : hotel.getStandardAmenities()){
			if(amenity.getAmenityTypeString() != null)
				amenity = new Amenity(amenity.getName(), amenity.getDescription(), amenity.getAmenityType(), amenity.getAmenityTypeString());
            amenities.add(amenity);
		}
        for(Amenity amenity : hotel.getQueenAmenities()){
			if(amenity.getAmenityTypeString() != null)	
				amenity = new Amenity(amenity.getName(), amenity.getDescription(), amenity.getAmenityType(), amenity.getAmenityTypeString());
            amenities.add(amenity);
		}
        for(Amenity amenity : hotel.getKingAmenities()){
			if(amenity.getAmenityTypeString() != null)
				amenity = new Amenity(amenity.getName(), amenity.getDescription(), amenity.getAmenityType(), amenity.getAmenityTypeString());
            amenities.add(amenity);
		}

        return amenities;
    }

    private void ClearValues(){
        TextArea_AmenityDescription.setText("");
        TextArea_HotelDescription.setText("");

        TextField_HotelName.setText("");
        TextField_NumberOfKingRooms.setText("");
        TextField_NumberOfQueenRooms.setText("");
        TextField_NumberOfStandardRooms.setText("");
        TextField_PriceStandard.setText("");
        TextField_PriceQueen.setText("");
        TextField_PriceKing.setText("");
        TextField_WeekendDifferential.setText("");
        TextField_AmenityName.setText("");
        TextField_HotelImage.setText("");

        ChoiceBox_AmenityType.setValue(AmenityType.STANDARD);

       // TableView_Amenities.setItems(FXCollections.observableArrayList());
    }

}
