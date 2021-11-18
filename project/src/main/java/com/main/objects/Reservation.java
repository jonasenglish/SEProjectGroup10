package com.main.objects;

import java.util.Date;
import com.main.database.DatabaseManager;

import org.bson.Document;
import org.bson.types.ObjectId;

// The Reservations object
public class Reservation {
    
    // The ID of the account that made the Reservation
    public ObjectId reservee = null;

    // Start and End of Reservation
    private Date startDate = null;
    private Date endDate = null;

    // The ID of the Hotel and the Room that has been reserved
    private ObjectId hotel = null;
    private String roomType = "";

    //Number of Items given in the Reservation Window
    private int nights = 0;
    private int rooms = 0;
    private int adults = 0;
    private int children = 0;

    //Customer Name, Email, and Phone #
    private String customerName = "";
    private String customerEmail = "";
    private String customerPhoneNumber = "";
    
    private ObjectId ID; // Unique ID generated by MongoDB, this value is set when the object is retrieved from the Database.

/// ----------------------------------------------------
    public String getHotelName(){
        return DatabaseManager.instance.FindHotelByID(hotel).getName();
    }

    public ObjectId getReservee() {
        return reservee;
    }
    public void setReservee(ObjectId reservee) {
       this.reservee = reservee;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String room) {
        this.roomType = room;
    }

    public ObjectId getID() {
        return ID;
    }
    public void setID(ObjectId ID) {
        this.ID = ID;
    }

    public ObjectId getHotel() {
        return hotel;
    }
    public void setHotel(ObjectId hotel) {
        this.hotel = hotel;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNights() {
        return nights;
    }
    public void setNights(int nights) {
        this.nights = nights;
    }

    public int getRooms() {
        return rooms;
    }
    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getAdults() {
        return adults;
    }
    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }
    public void setChildren(int children) {
        this.children = children;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }


/// --------------------------------------------------------

    // Test to see if a given date is within a current reservation span. Useful for listing reservations on a certain day or within a date range.
    public boolean isWithinRange(Date testDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
    
    //Converts this Reservation into a Document to be Stored in the DataBase
    public Document ToDocument() {

        Document document = new Document();

        document.append("Reservee", this.reservee);
        document.append("Start Date", this.startDate);
        document.append("End Date", this.endDate);
        document.append("Hotel", this.hotel);
        document.append("Number of Nights", this.nights);
        document.append("Number of Rooms", this.rooms);
        document.append("Number of Adults", this.adults);
        document.append("Number of Children", this.children);
        document.append("Room Type",this.roomType);
        document.append("Customer Name", this.customerName);
        document.append("Customer Email", this.customerEmail);
        document.append("Customer Phone Number", this.customerPhoneNumber);
        
        return document;
    }

    //Convert given Document into a Reservation Object.
    public static Reservation ToReservation(Document document) {
        
        Reservation reservation = new Reservation();

        reservation.reservee = (ObjectId) document.get("Reservee");

        reservation.startDate = (Date) document.get("Start Date");
        reservation.endDate = (Date) document.get("End Date");

        reservation.hotel = (ObjectId) document.get("Hotel");

        reservation.nights = (int) document.get("Number of Nights");
        reservation.rooms = (int) document.get("Number of Rooms");
        reservation.adults = (int) document.get("Number of Adults");
        reservation.children = (int) document.get("Number of Children");
        reservation.roomType = (String) document.get("Room Type");
        reservation.customerName = (String) document.get("Customer Name");
        reservation.customerEmail = (String) document.get("Customer Email");
        reservation.customerPhoneNumber = (String) document.get("Customer Phone Number");

        reservation.ID = (ObjectId) document.get("_id");

        return reservation;
    }

    public String toString() {
        DatabaseManager dm = DatabaseManager.instance;
        Account reservee = dm.FindAccountByID(this.reservee);
        Hotel hotel = dm.FindHotelByID(this.hotel);
        String returnString = "Reservation ID: " + ID.toString() +
        "\nReservee Username: " + reservee.getUsername() + 
        "\nReservee ID: " + reservee.getID().toString() + 
        "\nHotel Name: " + hotel.getName() +
        "\nHotel ID: " + hotel.getID().toString() +
        "\nStart Date: " + startDate.toString() +
        "\nEnd Date: " + endDate.toString() +
        "\nNumber of Nights: " + getNights()+
        "\nNumber of Rooms: " + getRooms() +
        "\nNumber of Adults: " + getAdults() +
        "\nNumber of Children: " + getChildren() +
        "\nRoom Type " + getRoomType() +
        "\nCustomer Name: " + getCustomerName() +
        "\nCustomer Email: " + getCustomerEmail() +
        "\nCustomer Phone Number: " + getCustomerPhoneNumber();

        return returnString;
    }
}
