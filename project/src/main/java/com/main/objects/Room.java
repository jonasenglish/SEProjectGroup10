package com.main.objects;

import java.util.List;

import org.bson.Document;

public class Room {
    
    public static enum RoomType {STANDARD, QUEEN, KING};
    private RoomType roomType;

    private String roomID = "";
    private int floor = 0;

    private List<Amenity> amenities = null;

    private List<Reservation> reservations = null;

/// ------------- Gets and Sets -----------------
    public List<Reservation> getReservations(){
        return reservations;
    }

    public void setReservations(List<Reservation> reservations){
        this.reservations = reservations;
    }

    public String getRoomID() {
        return roomID;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public int getFloor() {
        return floor;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRoomID(String roomId) {
        this.roomID = roomId;
    }
/// ---------------------------------------

    public Document ToDocument(){
        Document document = new Document();

        document.append("Floor", this.floor);
        document.append("RoomID", this.roomID);
        document.append("RoomType", this.roomType);
        document.append("Amenities", this.amenities);
        document.append("Reservations", this.amenities);

        return document;
    }

    public static Room FromDocument(Document document){
        Room room = new Room();

        room.floor = (int)document.get("Floor");
        room.roomID = (String)document.get("RoomID");
        room.roomType = (RoomType)document.get("RoomType");
        room.amenities = (List<Amenity>)document.get("Amenities");
        room.reservations = (List<Reservation>)document.get("Reservations");

        return room;
    }
}
