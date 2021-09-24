package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.main.database.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    // This is a static reference to the Primary Stage, and should be used to change Stages.
    public static Stage pStage;

    // Contains a list of Parents(Pages)
    // 0. Login Page
    // 1. Create Account Page
    //
    // Add new pages above and see the InitializePages function below.
    public static List<Parent> pages = new ArrayList<Parent>();

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        InitializePages();

        Parent root = pages.get(0);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Welcome! - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();

        pStage = primaryStage;

        DatabaseManager dManager = new DatabaseManager();
        DatabaseManager.SetInstance(dManager);
        DatabaseManager.instance.Connect();
    }

    // Initializes the pages list with the FXML files of the pages
    private void InitializePages(){
        try{
            pages.add((Parent)FXMLLoader.load(getClass().getResource("pages/LoginPage.fxml")));
            pages.add((Parent)FXMLLoader.load(getClass().getResource("pages/CreateAccountPage.fxml")));

            // Add new pages above.
        }catch(IOException e){
            System.err.println("Error Loading FXML classes!");
            System.err.println(e);
            return;
        }
    }
}
