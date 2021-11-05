package com.main;

//import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import com.main.database.DatabaseManager;
import com.main.objects.Account;
import com.main.objects.Hotel;
import com.main.pages.PageManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application
{
    // This is a static reference to the Primary Stage, and should be used to change Stages.
    public static Stage pStage;

    // The Dictionary of Pages, Add more Pages below in InitializePages()
    // Pages should be retrieved by name. E.G. pages.get("Login") retrieved the Login Page
    public static Hashtable<String, Parent> pages = new Hashtable<String, Parent>();

    // The account that is currently Logged in
    public static Account currentUser = null;
    public static Hotel currentHotel = null;

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pStage = primaryStage;
        InitializePages();

        PageManager.SetPage("Login", "Welcome! - Login");

        DatabaseManager dManager = new DatabaseManager();
        DatabaseManager.SetInstance(dManager);
        DatabaseManager.instance.Connect();
        //TestDatabase(); //Used to check if database is working
    }

    // Initializes the pages dictionary with the FXML files of the pages
    private void InitializePages(){
        try{
            pages.put("Login", (Parent)FXMLLoader.load(getClass().getResource("pages/LoginPage.fxml")));
            pages.put("CreateAccount", (Parent)FXMLLoader.load(getClass().getResource("pages/CreateAccountPage.fxml")));
            pages.put("CreateAmenity", (Parent)FXMLLoader.load(getClass().getResource("pages/createAmenityPage.fxml")));
            pages.put("ManagerHotelCreation", (Parent)FXMLLoader.load(getClass().getResource("pages/managerHotelCreation.fxml")));
            pages.put("ManagerView", (Parent)FXMLLoader.load(getClass().getResource("pages/managerView.fxml")));
            pages.put("ProfileView", (Parent)FXMLLoader.load(getClass().getResource("pages/profileView.fxml"))); // added a page for the profile
            pages.put("CreateReservation", (Parent)FXMLLoader.load(getClass().getResource("pages/CreateReservation.fxml"))); //added the reservation page
            pages.put("CustomerView", (Parent)FXMLLoader.load(getClass().getResource("pages/customerView.fxml")));
            pages.put("HotelView", (Parent)FXMLLoader.load(getClass().getResource("pages/hotelView.fxml")));
            pages.put("ReservationView", (Parent)FXMLLoader.load(getClass().getResource("pages/reservationView.fxml")));
            pages.put("HotelSearchView", (Parent)FXMLLoader.load(getClass().getResource("pages/hotelSearchView.fxml")));

            // Add new pages above.
        }catch(Exception e){
            System.err.println("Error Loading FXML classes!");
            System.err.println(e);

            return;
        }
    }

    private void TestDatabase(){
        DatabaseManager dm = DatabaseManager.instance;

        List<Account> accounts = dm.FindAllAccounts();
        System.out.println("Printing All Accounts in Database:\n");
        for(Account account : accounts)
            System.out.println(account.toString() + "\n");

        List<Hotel> hotels = dm.FindAllHotels();
        System.out.println("Printing All Hotels in Database:\n");
        for(Hotel hotel : hotels)
            System.out.println(hotel.toString() + "\n");

        /*List<Reservation> reservations = dm.FindAllReservations();
        System.out.println("Printing All Reservations in Database:\n");
        for(Reservation reservation : reservations)
            System.out.println(reservation.toString() + "\n");

            Had to comment this out. This will work once I fix the Reservee bug.
        */

        
    }
}
