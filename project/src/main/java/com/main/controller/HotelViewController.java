package com.main.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.main.App;
import com.main.objects.Amenity;
import com.main.objects.Hotel;
import com.main.pages.PageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HotelViewController implements Initializable {

	public static HotelViewController Instance = null;

    public Date fromDate, toDate;

    private Hotel currentHotel = null;

    @FXML
    private Label Label_HotelName;

    @FXML
    private ImageView ImageView_HotelImage;

    @FXML
    private Label Label_Description;

    @FXML
    private Label Label_StandardRoomNum;

    @FXML
    private Label Label_QueenRoomNum;

    @FXML
    private Label Label_KingRoomNum;

    @FXML
    private Label Label_StandardRoomPrice;

    @FXML
    private Label Label_QueenRoomPrice;

    @FXML
    private Label Label_KingRoomPrice;

    @FXML
    private Label Label_WeekendDifferential;

    @FXML
    private Label Label_FromDate;

    @FXML
    private Label Label_ToDate;

    @FXML
    private TableView<Amenity> TableView_Amenities;

    @FXML
    private TableColumn<Amenity, String> name;

    @FXML
    private TableColumn<Amenity, String> amenityType;

    @FXML
    private TableColumn<Amenity, String> description;

    @FXML
    void OnClick_Back(ActionEvent event) {
        if(App.currentUser.isEmployee())
            PageManager.SetPage("EmployeeView", "Employee");
        else
		    PageManager.SetPage("HotelSearchView", "Search for a Hotel!");
    }

    @FXML
    void OnClick_BookRoom(ActionEvent event) {
		// Switch to reservation view page
        CreateReservationController.selectedHotel = currentHotel;
        PageManager.SetPage("CreateReservation", "Create Reservation");
		return;
    }

	ObservableList<Amenity> list = FXCollections.observableArrayList();

	@Override
    public void initialize(URL location, ResourceBundle resources) {
        TableView_Amenities.setEditable(false);
        name.setCellValueFactory(new PropertyValueFactory<Amenity, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Amenity, String>("description"));
        amenityType.setCellValueFactory(new PropertyValueFactory<Amenity, String>("amenityTypeString"));

        TableView_Amenities.setItems(list);

		Instance = this;
    }

	public void ViewHotel(Hotel viewedHotel) {
		list = getAmenities(viewedHotel);

		Label_HotelName.setText(viewedHotel.getName());
		Label_Description.setText(viewedHotel.getDescription());

		Label_KingRoomNum.setText(viewedHotel.getKingRooms() + "");
		Label_QueenRoomNum.setText(viewedHotel.getQueenRooms() + "");
		Label_StandardRoomNum.setText(viewedHotel.getStandardRooms() + "");

		Label_KingRoomPrice.setText(viewedHotel.getRoomPriceKing() + "$");
		Label_QueenRoomPrice.setText(viewedHotel.getRoomPriceQueen() + "$");
		Label_StandardRoomPrice.setText(viewedHotel.getRoomPriceStandard() + "$");

		Label_WeekendDifferential.setText(viewedHotel.getWeekendDifferential() + "%");

		String url = viewedHotel.getHotelImageURL();
		if(!(url == null) && !url.isEmpty()){
			Image image = new Image(url);

			ImageView_HotelImage.setImage(image);
		}

		TableView_Amenities.setItems(list);

        if(fromDate == null || toDate == null){
            Label_FromDate.setText("N/A");
            Label_ToDate.setText("N/A");
        }else{
            Label_FromDate.setText(fromDate.toString());
            Label_ToDate.setText(toDate.toString());
        }

        currentHotel = viewedHotel;
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

}
