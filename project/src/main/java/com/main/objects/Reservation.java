package com.main.objects;

import java.util.Date;

import org.bson.Document;

public class Reservation {
    
    // The account that made the Reservation
    private Account reservee = null;

    // Start and End of Reservation
    private Date startDate = null;
    private Date endDate = null;

    // The Hotel and Room that has been reserved
    private Hotel hotel = null;
    private Room room = null;
    
/// ----------------------------------------------------
    public Account getReservee() {
        return reservee;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
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
    public void setReservee(Account reservee) {
        this.reservee = reservee;
    }
/// --------------------------------------------------------

    public boolean isWithinRange(Date testDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
    
    public Document ToDocument() {
        return null;
    }
}
