package com.main.database;

import static com.mongodb.client.model.Filters.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.main.objects.Account;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DatabaseManager {

    public static DatabaseManager instance = null;

    private MongoClientURI uri = new MongoClientURI("mongodb+srv://Group10:FmOfxH67VfqhBs2E@projectcluster.nlgn5.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

    private MongoClient mongoClient = null;

    private MongoCollection<Document> accountCollection = null;
    private MongoCollection<Document> hotelCollection = null;
    private MongoCollection<Document> reservationCollection = null;

    public void Connect(){
        try {
            mongoClient = new MongoClient(uri);
            System.out.println("Connected to MongoDB.");
            SetAccountCollection();
            SetHotelCollection();
            SetReservationCollection();
        } catch (Exception e) {
            System.err.println("ERROR! Failed to connect to database! Perhaps Username or Password was incorrect?");
        }
    }

    public void SetAccountCollection(){
        accountCollection = GetDatabase().getCollection("Accounts");
    }

    public void SetHotelCollection(){
        hotelCollection = GetDatabase().getCollection("Hotels");
    }

    public void SetReservationCollection(){
        reservationCollection = GetDatabase().getCollection("Reservations");
    }

    public MongoClient GetMongoClient(){
        return mongoClient;
    }

    public MongoDatabase GetDatabase(){
        return mongoClient.getDatabase("ProjectDatabase");
    }

    // Sets the Database Manager instance so that Database manager can be called from other classes using DatabaseManager.instance
    public static void SetInstance(DatabaseManager newInstance){
        instance = newInstance;
    }

    // Finds
    public Account FindAccountByUsername(String username){
        Document doc = accountCollection.find(eq("username", username)).first();
        if(doc != null)
            return Account.ToAccount(doc);
        return null;
    }

    public Account FindAccountByEmail(String email){
        Document doc = accountCollection.find(eq("email", email)).first();
        if(doc != null)
            return Account.ToAccount(doc);
        return null;
    }

    public Hotel FindHotelByName(String hotelName) {
        Document doc = hotelCollection.find(eq("Name", hotelName)).first();
        if(doc != null)
            return Hotel.ToHotel(doc);
        return null;
    }

    public List<Hotel> FindHotelsWithAvailableRooms() {
        List<Hotel> matchingHotels = new LinkedList<>();

        for(Hotel h : FindAllHotels()){
            if(h.getTotalRooms() > 0)
                matchingHotels.add(h);
        }

        return matchingHotels;
    }

    public List<Reservation> FindReservationsByAccount(Account account){
        List<Reservation> reservations = new LinkedList<>();

        Bson filter = eq("_id", account.getID());
        FindIterable<Document> documentResults = reservationCollection.find(filter);
        for(Document doc : documentResults){
            reservations.add(Reservation.ToReservation(doc));
        }
        return reservations;
    }

    public List<Reservation> FindReservationsInDateRange(Date startDate, Date endDate){
        List<Reservation> matchingReservations = new LinkedList<>();

        for(Reservation reservation : FindAllReservations()){
            if(reservation.isWithinRange(startDate) || reservation.isWithinRange(endDate))
                matchingReservations.add(reservation);
        }
        
        return matchingReservations;
    }

    // Returns all the Reservations in the Collection
    public List<Reservation> FindAllReservations(){
        FindIterable<Document> documentResults = reservationCollection.find();
        List<Reservation> reservations = new LinkedList<>();
        for(Document doc : documentResults){
            reservations.add(Reservation.ToReservation(doc));
        }
        return reservations;
    }

    // Returns all the Hotels in the Collection
    public List<Hotel> FindAllHotels(){
        FindIterable<Document> documentResults = hotelCollection.find();
        List<Hotel> hotels = new LinkedList<>();
        if(documentResults == null)
            return hotels;

        for(Document doc : documentResults)
            hotels.add(Hotel.ToHotel(doc));

        return hotels;
    }

    // Returns all the Accounts in the Collection
    public List<Account> FindAllAccounts(){
        FindIterable<Document> documentResults = accountCollection.find();
        List<Account> accounts = new LinkedList<>();
        if(documentResults == null)
            return accounts;

        for(Document doc : documentResults)
            accounts.add(Account.ToAccount(doc));

        return accounts;
    }

    // Inserts
    public void InsertAccount(Account account){
        accountCollection.insertOne(account.ToDocument());
    }

    public void InsertHotel(Hotel hotel){
        hotelCollection.insertOne(hotel.ToDocument());
    }

    public void InsertReservation(Reservation reservation){
        reservationCollection.insertOne(reservation.ToDocument());
    }

    // Delete
    public void DeleteAccount(Account account){
        Bson filter = and(eq("_id", account.getID()));
        accountCollection.deleteOne(filter);
    }

    public void DeleteHotel(Hotel hotel){
        Bson filter = and(eq("_id", hotel.getID()));
        accountCollection.deleteOne(filter);
    }

    public void DeleteReservation(Reservation reservation){
        Bson filter = and(eq("_id", reservation.getID()));
        accountCollection.deleteOne(filter);
    }

    // Update
    public void UpdateAccount(Account account){
        Bson filter = and(eq("_id", account.getID()));
        accountCollection.updateOne(filter, account.ToDocument());
    }

    public void UpdateHotel(Hotel hotel){
        Bson filter = and(eq("_id", hotel.getID()));
        hotelCollection.updateOne(filter, hotel.ToDocument());
    }

    public void UpdateReservation(Reservation reservation){
        Bson filter = and(eq("_id", reservation.getID()));
        reservationCollection.updateOne(filter, reservation.ToDocument());
    }

}