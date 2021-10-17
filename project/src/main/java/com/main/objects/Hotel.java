package com.main.objects;

import java.util.List;

import org.bson.Document;

public class Hotel {
    
    private String name = "Hotel Name";
    private String description = "";
    
    private String ownerUsername = "";
    private String ownerEmail = "";

    private List<String> managerUsernames = null;
    private List<String> managerEmails = null;
    
    private List<Amenity> amenities = null;

    private List<Room> rooms = null;

    private float roomPriceStandard = 100.00f;
    private float roomPriceQueen = 150.00f;
    private float roomPriceKing = 250.00f;

    private float weekendDifferential = .25f;

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public float getWeekendDifferential() {
        return weekendDifferential;
    }

    public void setWeekendDifferential(float weekendDifferential) {
        this.weekendDifferential = weekendDifferential;
    }

    public float getRoomPriceKing() {
        return roomPriceKing;
    }

    public void setRoomPriceKing(float roomPriceKing) {
        this.roomPriceKing = roomPriceKing;
    }

    public float getRoomPriceQueen() {
        return roomPriceQueen;
    }

    public void setRoomPriceQueen(float roomPriceQueen) {
        this.roomPriceQueen = roomPriceQueen;
    }

    public float getRoomPriceStandard() {
        return roomPriceStandard;
    }

    public void setRoomPriceStandard(float roomPriceStandard) {
        this.roomPriceStandard = roomPriceStandard;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<String> getManagerEmails() {
        return managerEmails;
    }

    public void setManagerEmails(List<String> managerEmails) {
        this.managerEmails = managerEmails;
    }

    public List<String> getManagerUsernames() {
        return managerUsernames;
    }

    public void setManagerUsernames(List<String> managerUsernames) {
        this.managerUsernames = managerUsernames;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerUserName() {
        return ownerUsername;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUsername = ownerUserName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    // Convert this Hotel into a Document to be stored in the Database
    public Document ToDocument(){
        Document document = new Document();

        document.append("Name", this.name);
        document.append("Description", this.description);
        
        document.append("OwnerUsername", this.ownerUsername);
        document.append("OwnerEmail", this.ownerEmail);

        document.append("ManagerUsernames", this.managerUsernames);
        document.append("ManagerEmails", this.managerEmails);

        document.append("Amenities", this.amenities);

        document.append("Rooms", this.rooms);

        document.append("RoomPriceStandard", this.roomPriceStandard);
        document.append("RoomPriceQueen", this.roomPriceQueen);
        document.append("RoomPriceKing", this.roomPriceKing);

        document.append("WeekendDifferential", this.weekendDifferential);

        return document;
    }

    // Convert given Document into a Hotel Object.
    public static Hotel FromDocument(Document document){
        Hotel hotel = new Hotel();

        hotel.name = (String) document.get("Name");
        hotel.description = (String) document.get("Description");
        
        hotel.ownerUsername = (String) document.get("OwnerUsername");
        hotel.ownerEmail = (String) document.get("OwnerEmail");

        hotel.managerUsernames = (List<String>) document.get("ManagerUsernames");
        hotel.managerEmails = (List<String>) document.get("ManagerEmails");

        hotel.amenities = (List<Amenity>) document.get("Amenities");

        hotel.rooms = (List<Room>) document.get("Rooms");

        hotel.roomPriceStandard = (float) document.get("RoomPriceStandard");
        hotel.roomPriceQueen = (float) document.get("RoomPriceQueen");
        hotel.roomPriceKing = (float) document.get("RoomPriceKing");

        hotel.weekendDifferential = (float) document.get("WeekendDifferential");

        return hotel;
    }
}
