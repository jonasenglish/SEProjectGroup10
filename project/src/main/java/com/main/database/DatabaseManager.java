package com.main.database;

import static com.mongodb.client.model.Filters.*;

import com.main.objects.Account;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

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

    public static void SetInstance(DatabaseManager newInstance){
        instance = newInstance;
    }

    public Document FindDocumentByUsername(String username){
        Document document = accountCollection.find(eq("username", username)).first();
        return document;
    }

    public Document FindDocumentByEmail(String email){
        Document document = accountCollection.find(eq("email", email)).first();
        return document;
    }

    public Account FindAccountByUsername(String username){
        Document doc = FindDocumentByUsername(username);
        if(doc != null)
            return Account.ToAccount(doc);
        return null;
    }

    public Account FindAccountByEmail(String email){
        Document doc = FindDocumentByUsername(email);
        if(doc != null)
            return Account.ToAccount(doc);
        return null;
    }

    public void InsertAccount(Account account){
        accountCollection.insertOne(account.ToDocument());
    }

    public void InsertHotel(Hotel hotel){
        hotelCollection.insertOne(hotel.ToDocument());
    }

    public void InsertReservation(Reservation reservation){
        reservationCollection.insertOne(reservation.ToDocument());
    }

}