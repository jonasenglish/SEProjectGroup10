package com.main;

import java.io.IOException;
import java.util.Hashtable;

import com.main.database.DatabaseManager;
import com.main.objects.Account;
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
    }

    // Initializes the pages dictionary with the FXML files of the pages
    private void InitializePages(){
        try{
            pages.put("Login", (Parent)FXMLLoader.load(getClass().getResource("pages/LoginPage.fxml")));
            pages.put("CreateAccount", (Parent)FXMLLoader.load(getClass().getResource("pages/CreateAccountPage.fxml")));
            pages.put("CreateAmenity", (Parent)FXMLLoader.load(getClass().getResource("pages/createAmenityPage.fxml")));
            pages.put("ManagerHotelCreation", (Parent)FXMLLoader.load(getClass().getResource("pages/managerHotelCreation.fxml")));
            pages.put("ManagerView", (Parent)FXMLLoader.load(getClass().getResource("pages/managerView.fxml")));
            // Add new pages above.
        }catch(Exception e){
            System.err.println("Error Loading FXML classes!");
            System.err.println(e);

            return;
        }
    }
}
