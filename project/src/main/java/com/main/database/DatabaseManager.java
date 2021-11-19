package com.main.database;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.main.App;
import com.main.objects.Account;
import com.main.objects.Hotel;
import com.main.objects.Reservation;
import com.main.tools.PopupHandler;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

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

    public Account FindAccountByID(ObjectId ID){
        Document doc = accountCollection.find(eq("_id", ID)).first();
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

    public Hotel FindHotelByID(ObjectId hotelID) {
        Document doc = hotelCollection.find(eq("_id", hotelID)).first();
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

    public Reservation FindReservationByID(ObjectId ID){
        Document doc = reservationCollection.find(eq("_id", ID)).first();
        if(doc != null)
            return Reservation.ToReservation(doc);
        return null;
    }

    public List<Reservation> FindReservationsByAccount(Account account){
        List<Reservation> reservations = new LinkedList<>();

        Bson filter = eq("Reservee", account.getID());
        FindIterable<Document> documentResults = reservationCollection.find(filter);
        for(Document doc : documentResults){
            reservations.add(Reservation.ToReservation(doc));
        }
        return reservations;
    }

    public List<Reservation> FindReservationsInDateRange(Date startDate, Date endDate){
        List<Reservation> matchingReservations = new LinkedList<>();

        for(Reservation reservation : FindAllReservations()){
            if(reservation.isWithinRange(startDate) || reservation.isWithinRange(endDate)){
                matchingReservations.add(reservation);
                //System.out.println(reservation.toString());
            }
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
        List<Account> accounts = new ArrayList<Account>();
        if(documentResults == null)
            return accounts;

        for(Document doc : documentResults)
            accounts.add(Account.ToAccount(doc));

        return accounts;
    }

    // Get all reservations of the account currently logged in. Returns null if no account is logged in.
    public List<Reservation> FindCurrentUserReservations(){
        if(App.currentUser == null){
            System.err.println("ERROR: No Current User!\nPlease Log in first.");
            return null;
        }

        List<Reservation> reservations = FindAllReservations();
        List<Reservation> reservationsFound = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if(reservation.getReservee().equals(App.currentUser))
                reservationsFound.add(reservation);
        }

        return reservationsFound;
    }

    // Get all reservations of the account currently logged in that contains the given date.
    public List<Reservation> FindCurrentUserReservationsByDate(Date date){
        List<Reservation> reservations = FindCurrentUserReservations();

        if(reservations == null){
            System.err.println("ERROR: No Current User!\nPlease Log in first.");
            return null;
        }

        List<Reservation> reservationsFound = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if(reservation.isWithinRange(date)){
                reservationsFound.add(reservation);
            }
        }
        
        return reservationsFound;
    }

    // Get all reservations of the account currently logged in that contains the given hotel.
    public List<Reservation> FindCurrentUserReservationsByHotel(Hotel hotel){
        List<Reservation> reservations = FindCurrentUserReservations();

        if(reservations == null){
            System.err.println("ERROR: No Current User!\nPlease Log in first.");
            return null;
        }

        List<Reservation> reservationsFound = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if(reservation.getHotel().equals(hotel))
                reservationsFound.add(reservation);
        }

        return reservationsFound;
    }

    // Get all reservations of the account currently logged in that contains the given hotel name.
    public List<Reservation> FindCurrentUserReservationsByHotel(String hotelName){
        List<Reservation> reservations = FindCurrentUserReservations();

        if(reservations == null){
            System.err.println("ERROR: No Current User!\nPlease Log in first.");
            return null;
        }

        List<Reservation> reservationsFound = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Hotel hotel = FindHotelByID(reservation.getHotel());
            if(hotel.getName().equals(hotelName))
                reservationsFound.add(reservation);
        }

        return reservationsFound;
    }

    public List<Reservation> FindReservationsByEmployeeHotel(){
        if(!(App.currentUser.isEmployee() || App.currentUser.isManager())){
            PopupHandler.ShowError("FindReservationsByEmployeeHotel Called by a Customer account!");
            return null;
        }

        List<Reservation> reservations = FindAllReservations();

        List<Reservation> reservationsFound = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if(reservation.getHotel().equals(App.currentHotel.getID()))
                reservationsFound.add(reservation);
        }

        return reservationsFound;
    }

    public List<Account> FindEmployeeAccountsByHotelID(ObjectId hotelID){
        List<Account> accounts = FindAllAccounts();
        List<Account> finalAccounts = new ArrayList<Account>();

        for (Account account : accounts) {
            if(account.isEmployee() && account.getHotelID() != null && account.getHotelID().equals(hotelID))
                finalAccounts.add(account);
        }

        return finalAccounts;
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
        hotelCollection.deleteOne(filter);
    }

    public void DeleteReservation(Reservation reservation){
        System.out.println(reservation.getID());
        Bson filter = and(eq("_id", reservation.getID()));
        DeleteResult result = reservationCollection.deleteOne(filter);
        System.out.println(result.getDeletedCount());
    }

    // Update
    public void UpdateAccount(Account account){
        Bson filter = and(eq("_id", account.getID()));
        accountCollection.replaceOne(filter, account.ToDocument());
    }

    public void UpdateHotel(Hotel hotel){
        Bson filter = and(eq("_id", hotel.getID()));
        hotelCollection.replaceOne(filter, hotel.ToDocument());
    }

    public void UpdateReservation(Reservation reservation){
        Bson filter = and(eq("_id", reservation.getID()));
        reservationCollection.replaceOne(filter, reservation.ToDocument());
    }

    public List<Hotel> FindHotelsByName(String text) {
        if(text.equals("")) return FindAllHotels();
        
        List<Hotel> results = new ArrayList<>();

        for (Hotel hotel : FindAllHotels()) {
            if(hotel.getName().toLowerCase().contains(text.toLowerCase()))
                results.add(hotel);
        }
        
        return results;
    }

    public List<Hotel> FindHotelsByRooms(int value) {
        List<Hotel> results = new ArrayList<>();

        for (Hotel hotel : FindAllHotels()) {
            if(hotel.getTotalRooms() > value)
                results.add(hotel);
        }

        return results;
    }

    public List<Hotel> FindHotelsByPriceLT(double value, Hotel.RoomType roomType) {
        List<Hotel> results = new ArrayList<>();

        for (Hotel hotel : FindAllHotels()) {
            if(roomType == Hotel.RoomType.STANDARD)
                if(hotel.getRoomPriceStandard() < value) results.add(hotel);
            if(roomType == Hotel.RoomType.QUEEN)
                if(hotel.getRoomPriceQueen() < value) results.add(hotel);
            if(roomType == Hotel.RoomType.KING)
                if(hotel.getRoomPriceKing() < value) results.add(hotel);
        }

        return results;
    }

    public List<Hotel> FindHotelsByPriceGT(double value, Hotel.RoomType roomType) {
        List<Hotel> results = new ArrayList<>();

        for (Hotel hotel : FindAllHotels()) {
            if(roomType == Hotel.RoomType.STANDARD)
                if(hotel.getRoomPriceStandard() > value) results.add(hotel);
            if(roomType == Hotel.RoomType.QUEEN)
                if(hotel.getRoomPriceQueen() > value) results.add(hotel);
            if(roomType == Hotel.RoomType.KING)
                if(hotel.getRoomPriceKing() > value) results.add(hotel);
        }

        return results;
    }

}