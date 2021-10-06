package com.main.objects;

import java.util.List;

import org.bson.Document;

public class Room {
    
    public static enum RoomType {STANDARD, QUEEN, KING};
    private RoomType roomType;

    private String roomID = "";
    private int floor = 0;

    // Some form of availability tracking should go here.

    private List<Amenity> amenities = null;

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

    public Document ToDocument(){
        Document document = new Document();
        document.append("RoomID", this.roomID);
        document.append("Floor", this.floor);

        // Availability here.

        document.append("RoomType", this.roomType);
        document.append("Amenities", this.amenities);
        return document;
    }

    public static Room FromDocument(Document document){
        Room room = new Room();
        room.roomID = (String)document.get("RoomID");
        room.floor = (int)document.get("Floor");

        // Availability here.

        room.roomType = (RoomType)document.get("RoomType");
        room.amenities = (List<Amenity>)document.get("Amenities");
        return room;
    }
}
