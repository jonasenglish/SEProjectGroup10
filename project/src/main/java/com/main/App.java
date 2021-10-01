package com.main;

import java.io.IOException;
import java.util.Hashtable;

import com.main.database.DatabaseManager;
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

            // Add new pages above.
        }catch(IOException e){
            System.err.println("Error Loading FXML classes!");
            System.err.println(e);
            return;
        }
    }
}
